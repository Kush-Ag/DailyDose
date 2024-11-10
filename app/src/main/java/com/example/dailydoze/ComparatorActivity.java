package com.example.dailydoze;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailydoze.Adapters.MarketAdapter;
import com.example.dailydoze.Models.MarketModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComparatorActivity extends AppCompatActivity {



    private RecyclerView recyclerView;
    private MarketAdapter adapter;
    private List<MarketModel> myMarketModels = new ArrayList<>();
    private TextView medNameDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_comparator);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent = getIntent();
        String medName = intent.getStringExtra("medName");

        recyclerView=findViewById(R.id.recyclerView);
        medNameDisplay=findViewById(R.id.medNameDisplay);
        medNameDisplay.setText(medName);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MarketAdapter(myMarketModels, this);
        recyclerView.setAdapter(adapter);

        // Make the API call
        fetchMarket(medName);


    }



    private void fetchMarket(String medName) {


        retrofit2.Call<List<MarketModel>> call = RetrofitClientKrishna
                .getInstance()
                .getApiKrishna()
                .getDetails(medName,"282005");

        call.enqueue(new Callback<List<MarketModel>>() {
            @Override
            public void onResponse(retrofit2.Call<List<MarketModel>> call, Response<List<MarketModel>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        myMarketModels.clear();
                        myMarketModels.addAll(response.body()); // Add all items from the response
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(ComparatorActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    System.out.println("Response code: " + response.code()); // Log response code
                    Toast.makeText(ComparatorActivity.this, "Response code: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<MarketModel>> call, Throwable t) {
                Toast.makeText(ComparatorActivity.this, "Error fetching data: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                System.out.println("Error fetching data: " + t.getMessage());
            }
        });
    }
}