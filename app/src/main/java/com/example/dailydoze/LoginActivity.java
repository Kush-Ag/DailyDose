package com.example.dailydoze;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

import com.example.dailydoze.Models.LoginRequest;
import com.example.dailydoze.Models.LoginResponse;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {



    private EditText username,password;
    private TextView bottomText,forgotPassword;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);

        forgotPassword=findViewById(R.id.forgotPassword);
        loginButton=findViewById(R.id.loginButton);
        bottomText=findViewById(R.id.bottomText);

        bottomText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "Contact Developer", Toast.LENGTH_SHORT).show();
            }
        });



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String usernameValue, passwordValue;
                usernameValue=username.getText().toString().trim();
                passwordValue=password.getText().toString().trim();

                if (TextUtils.isEmpty(usernameValue)) {
                    username.setError("Username cannot be empty");
                    username.requestFocus();
                } else if (TextUtils.isEmpty(passwordValue)) {
                    password.setError("Password cannot be empty");
                    password.requestFocus();
                } else{
                    //handle login


                    LoginRequest request = new LoginRequest(usernameValue, passwordValue);
                    System.out.println("uuuuuu   "+usernameValue+"    "+passwordValue);

                    Call<LoginResponse> call = RetrofitClient
                            .getInstance()
                            .getApi()
                            .login(request);

                    call.enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                            if(response.code()==200)
                            {
                                Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                TokenManager.getInstance(LoginActivity.this).saveUsername(usernameValue);
                                Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                                startActivity(intent);
                            }
                            else if(response.code()==401){
                                Toast.makeText(getApplicationContext(), "Wrong Password", Toast.LENGTH_SHORT).show();

                            }
                            else if(response.code()==500){
                                Toast.makeText(getApplicationContext(), "Internal Error", Toast.LENGTH_SHORT).show();

                            }
                            else if(response.code()==404){
                                Toast.makeText(getApplicationContext(), "User not registered", Toast.LENGTH_SHORT).show();

                            }


                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {

                            Toast.makeText(getApplicationContext(), "Login failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });


                }
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