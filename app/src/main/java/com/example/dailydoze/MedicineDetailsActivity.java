package com.example.dailydoze;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.dailydoze.Adapters.DoseAdapter;
import com.example.dailydoze.Adapters.DoseCardAdapter;
import com.example.dailydoze.Adapters.MarketAdapter;
import com.example.dailydoze.Models.DoseCardModel;
import com.example.dailydoze.Models.MarketModel;
import com.example.dailydoze.Models.MedicationSchema;

import java.util.ArrayList;
import java.util.List;

public class MedicineDetailsActivity extends AppCompatActivity {

    private TextView medNameDisplay;
    private RecyclerView recyclerView;
    private DoseCardAdapter adapter;
    private List<DoseCardModel> myDoseModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_medicine_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        MedicationSchema model = (MedicationSchema) getIntent().getSerializableExtra("medModel");

        for(int i=0;i<model.getDose().size();i++){
            myDoseModels.add(new DoseCardModel(model.getDose().get(i).getDose(),model.getDose().get(i).getTime()));
        }

        medNameDisplay=findViewById(R.id.medNameDisplay);

        medNameDisplay.setText(model.getMedName());

        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DoseCardAdapter(myDoseModels, this);
        recyclerView.setAdapter(adapter);

    }
}