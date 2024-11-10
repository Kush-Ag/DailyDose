package com.example.dailydoze;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailydoze.Adapters.DoseAdapter;
import com.example.dailydoze.Models.CreateMedRequest;
import com.example.dailydoze.Models.CreateMedResponse;
import com.example.dailydoze.Models.DoseItem;
import com.example.dailydoze.Models.LoginResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddMedActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText medNameInput, medQtyInput;
    private ImageView addDoseButton;
    private Button addMedicineButton;
    private List<DoseItem> doseData;
    private DoseAdapter doseAdapter;
    private TextView typeDisplay;
    private int doseEntered=0;
    private String medType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_med);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView=findViewById(R.id.inputRecyclerView);
        medNameInput=findViewById(R.id.medNameInput);
        medQtyInput=findViewById(R.id.medQtyInput);
        addDoseButton=findViewById(R.id.addDoseButton);
        addMedicineButton=findViewById(R.id.addMedicineButton);
        typeDisplay=findViewById(R.id.typeDisplay);

        Spinner medicineTypeSpinner = findViewById(R.id.medicineTypeSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.medicine_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        medicineTypeSpinner.setAdapter(adapter);

        medicineTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedType = parent.getItemAtPosition(position).toString();

                // Implement different functionality based on the selected type
                switch (selectedType) {
                    case "Tablet":
                        typeDisplay.setText("pills");
                        medType="Tablet";
                        break;
                    case "Syrup":
                        typeDisplay.setText("ml");
                        medType="Syrup";
                        break;
                    case "Capsule":

                        typeDisplay.setText("capsules");
                        medType="Capsules";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Optional: Handle case when no item is selected
            }
        });

        doseData = new ArrayList<>();

        doseAdapter = new DoseAdapter(doseData);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(doseAdapter);

        addCard();

        addDoseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addCard();
            }
        });

        addMedicineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(medNameInput.getText().toString().isEmpty())
                {
                    Toast.makeText(AddMedActivity.this, "Enter Medicine Name", Toast.LENGTH_SHORT).show();
                }
                else if(verifyDoses())
                {
                    if(medQtyInput.getText().toString().isEmpty())
                    {
                        Toast.makeText(AddMedActivity.this, "Enter quantity", Toast.LENGTH_SHORT).show();
                    }
                    else if(Double.parseDouble(medQtyInput.getText().toString())<0)
                    {
                        Toast.makeText(AddMedActivity.this, "Quantity cannot be negative", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        //api request
                        String username=TokenManager.getInstance(AddMedActivity.this).getUsername();

                        CreateMedRequest request = new CreateMedRequest(username,calculateDailyReq(),formatName(medNameInput.getText().toString()),Double.parseDouble(medQtyInput.getText().toString()),medType,"med link",sortDoseData(doseData));

                        Call<CreateMedResponse> call = RetrofitClient
                                .getInstance()
                                .getApi()
                                .create(request);

                        call.enqueue(new Callback<CreateMedResponse>() {
                            @Override
                            public void onResponse(Call<CreateMedResponse> call, Response<CreateMedResponse> response) {

                                if(response.code()==200)
                                {
                                    clearAllFields();
                                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

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
                            public void onFailure(Call<CreateMedResponse> call, Throwable t) {

                                Toast.makeText(getApplicationContext(), "Login failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });

                    }
                }

            }
        });
    }

    private void clearAllFields() {
        // Clear the medicine name and quantity fields
        medNameInput.setText("");
        medQtyInput.setText("");

        // Clear the dose data
        doseData.clear();
        doseAdapter.notifyDataSetChanged();

        // Reset the spinner to the first item
        Spinner medicineTypeSpinner = findViewById(R.id.medicineTypeSpinner);
        medicineTypeSpinner.setSelection(0);

        // Reset the type display text
        typeDisplay.setText("");

        // Reset dose entered counter
        doseEntered = 0;
        addCard();

        // Optional: Reset any other UI elements if necessary
    }

    private void addCard() {
        // Check if the last dose input is not empty
        if (!doseData.isEmpty()) {
            DoseItem lastDoseItem = doseData.get(doseData.size() - 1);
            if (lastDoseItem.getDose().isEmpty()) {
                Toast.makeText(this, "Enter dose first", Toast.LENGTH_SHORT).show();
                return;
            }
            if (lastDoseItem.getTime().equals("Select Time")) {
                Toast.makeText(this, "Select Time first", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        doseEntered++;

        // Add a new DoseItem with empty dose and time
        doseData.add(new DoseItem("", "Select Time"));
        doseAdapter.notifyItemInserted(doseData.size() - 1);
        recyclerView.scrollToPosition(doseData.size() - 1); // Scroll to new card
    }



    private double calculateDailyReq() {
        double ret=0;
        for(int i=0;i<doseData.size();i++){
            DoseItem doseItem=doseData.get(i);
            ret=ret+Double.parseDouble(doseItem.getDose());

        }
        return ret;

    }

    private String formatName(String str) {

        return Character.toUpperCase(str.charAt(0))+str.substring(1);

    }

    private List<DoseItem> sortDoseData(List<DoseItem> doseData) {
        // Define the date format for 12-hour time
        SimpleDateFormat format = new SimpleDateFormat("hh:mm a");

        // Sort the doseData list by time in ascending order
        doseData.sort((dose1, dose2) -> {
            try {
                Date time1 = format.parse(dose1.getTime());
                Date time2 = format.parse(dose2.getTime());
                return time1.compareTo(time2);

            } catch (ParseException e) {
                e.printStackTrace();
                return 0; // In case of a parsing error, consider them equal
            }
        });
        return doseData;
    }

    private boolean verifyDoses() {

        DoseItem lastDoseItem=doseData.get(doseData.size()-1);

        if(lastDoseItem.getDose().isEmpty()||lastDoseItem.getTime().equals("Select Time"))
        {
            Toast.makeText(this, "Enter dose and timing properly", Toast.LENGTH_SHORT).show();
            return false;
        }
        else
        {
            for(int i=0;i<doseData.size();i++){
                DoseItem doseItem=doseData.get(i);
                if(Double.parseDouble(doseItem.getDose())<=0)
                {
                    Toast.makeText(this, "All doses should be greater than 0", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        }
        return true;

    }
}