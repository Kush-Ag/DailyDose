package com.example.dailydoze;

import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dailydoze.Models.SignUpResponse;
import com.example.dailydoze.Models.UserModel;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {



    private Button regButton;
    private TextView bottomText;
    private EditText name, username, pincode, confirmPassword, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        regButton=findViewById(R.id.aButton);
        bottomText=findViewById(R.id.bottomText);
        name=findViewById(R.id.name);
        username=findViewById(R.id.username);
        pincode=findViewById(R.id.pincode);
        password=findViewById(R.id.password);
        confirmPassword=findViewById(R.id.confirmPassword);

        bottomText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent (SignUpActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateName()&&validateUsername()&&validatePincode()&&validatePasswords())
                {
                    //handle sign up

                    signup();

                    Intent intent = new Intent (SignUpActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private Boolean validateName(){
        String val=name.getText().toString().trim();

        if(val.isEmpty()){
            name.setError("Name cannot be empty");
            name.requestFocus();
            return false;
        }
        else {
            name.setError(null);
            return true;
        }
    }

    private Boolean validatePincode(){
        String val=pincode.getText().toString().trim();

        if(val.isEmpty()){
            pincode.setError("Name cannot be empty");
            pincode.requestFocus();
            return false;
        }
        else if(val.length()!=6){
            pincode.setError("Invalid pincode format");
            pincode.requestFocus();
            return false;
        }
        else {
            pincode.setError(null);
            return true;
        }
    }

    private Boolean validateUsername(){
        String val=username.getText().toString().toUpperCase().trim();

        if(val.isEmpty()){
            username.setError("Username cannot be empty");
            username.requestFocus();
            return false;
        }
        else if(val.contains(" ")){
            username.setError("Username cannot have spaces");
            username.requestFocus();
            return false;
        }
        else {
            username.setError(null);
            return true;
        }
    }

    private Boolean validatePasswords(){
        String val=password.getText().toString().trim();
        String val2=confirmPassword.getText().toString().trim();

        if(val.isEmpty()){
            password.setError("Enter Password");
            password.requestFocus();
            return false;
        }
        else if(val.contains(" ")){
            password.setError("Password cannot have spaces");
            password.requestFocus();
            return false;
        }
        else if(val.length()>12){
            password.setError("12 characters only");
            password.requestFocus();
            return false;
        }
        else if(!val.equals(val2)) {
            password.setError("Password Mismatch");
            confirmPassword.setError("Password Mismatch");
            return false;
        }
        else {
            password.setError(null);
            confirmPassword.setError(null);
            return true;
        }
    }

    private void signup(){
        List<String> Allergies=new ArrayList<>();
        UserModel userModel=new UserModel(name.getText().toString(),username.getText().toString(),password.getText().toString(),pincode.getText().toString(),Allergies);

        Call<SignUpResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .register(userModel);



        call.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                if (response.isSuccessful()) {
                    SignUpResponse signUpResponse = response.body();
                    if (signUpResponse != null) {
                        Toast.makeText(getApplicationContext(), signUpResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Failed to get a valid response", Toast.LENGTH_SHORT).show();
                    }
                } else {

                    try {
                        String errorBody = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(errorBody);
                        String error = jsonObject.getString("error");
                        Toast.makeText(SignUpActivity.this, error, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        // Handle error in parsing error response
                    }
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                // Handle failure
                Toast.makeText(getApplicationContext(), "Registration failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private static long back_pressed;
    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            finishAffinity();
            System.exit(0);
        } else {
            Toast.makeText(getBaseContext(), "Press once again to exit", Toast.LENGTH_SHORT).show();
            back_pressed = System.currentTimeMillis();
        }
    }
}