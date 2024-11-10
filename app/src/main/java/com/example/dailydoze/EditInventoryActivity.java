    package com.example.dailydoze;

    import android.os.Bundle;
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

    import com.example.dailydoze.Models.FetchedMedicationsResponse;
    import com.example.dailydoze.Models.InventoryModel;
    import com.example.dailydoze.Models.MedicationSchema;
    import com.example.dailydoze.Models.UpdateQtyRequest;
    import com.example.dailydoze.Models.UpdateQtyResponse;

    import java.util.List;

    import retrofit2.Call;
    import retrofit2.Callback;
    import retrofit2.Response;

    public class EditInventoryActivity extends AppCompatActivity {

        private TextView medNameDisplay, typeDisplay, typeDisplay2, oldQty;
        private EditText medQtyInput;
        private Button saveChangesButton;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            EdgeToEdge.enable(this);
            setContentView(R.layout.activity_edit_inventory);
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });

            medNameDisplay=findViewById(R.id.medNameDisplay);
            typeDisplay = findViewById(R.id.typeDisplay);
            typeDisplay2 = findViewById(R.id.typeDisplay2);
            oldQty = findViewById(R.id.oldQty);

            medQtyInput = findViewById(R.id.medQtyInput);
            saveChangesButton = findViewById(R.id.saveChangesButton);

            InventoryModel model = (InventoryModel) getIntent().getSerializableExtra("invModel");

// Now you can use the model to set data in your views
            String medName = model.getMedName();
            String medQty = model.getMedQty();
            String medType = model.getMedType();
            String medId = model.getMedId();

            medNameDisplay.setText(medName);
            oldQty.setText(medQty);

            if(medType.equalsIgnoreCase("tablet")){
                typeDisplay.setText("pills");
                typeDisplay2.setText("pills");

            }else if(medType.equalsIgnoreCase("syrup")){
                typeDisplay.setText("ml");
                typeDisplay2.setText("ml");
            }else{
                typeDisplay.setText("capsules");
                typeDisplay2.setText("capsules");
            }


            saveChangesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String newQtyStr = medQtyInput.getText().toString();
                    if (newQtyStr.isEmpty()) {
                        Toast.makeText(EditInventoryActivity.this, "Please enter a valid quantity", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    double newQty=Double.parseDouble(medQtyInput.getText().toString());


                    UpdateQtyRequest request = new UpdateQtyRequest(medId, newQty);


                    retrofit2.Call<UpdateQtyResponse> call = RetrofitClient
                            .getInstance()
                            .getApi()
                            .updateQty(request);

                    call.enqueue(new Callback<UpdateQtyResponse>() {
                        @Override
                        public void onResponse(retrofit2.Call<UpdateQtyResponse> call, Response<UpdateQtyResponse> response) {
                            if (response.isSuccessful()) {

                                Toast.makeText(EditInventoryActivity.this, "Quantity updated successfully!", Toast.LENGTH_SHORT).show();
                                oldQty.setText(String.valueOf(newQty)); // Update displayed quantity

                            } else { // Handle failed response
                                Toast.makeText(EditInventoryActivity.this, "Failed to update quantity. Please try again.", Toast.LENGTH_SHORT).show();


                            }
                        }

                        @Override
                        public void onFailure(Call<UpdateQtyResponse> call, Throwable t) {
                            // Handle failure (e.g., network error)
                            Toast.makeText(EditInventoryActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });


                }
            });
        }
    }