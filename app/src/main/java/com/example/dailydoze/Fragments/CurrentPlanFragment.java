package com.example.dailydoze.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dailydoze.Adapters.InventoryAdapter;
import com.example.dailydoze.Adapters.PlanCardAdapter;
import com.example.dailydoze.Models.FetchedMedicationsResponse;
import com.example.dailydoze.Models.InventoryModel;
import com.example.dailydoze.Models.MedicationSchema;
import com.example.dailydoze.R;
import com.example.dailydoze.RetrofitClient;
import com.example.dailydoze.TokenManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CurrentPlanFragment extends Fragment {



    private RecyclerView recyclerView;
    private PlanCardAdapter adapter;
    private List<MedicationSchema> myModels = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_current_plan, container, false);

        recyclerView=view.findViewById(R.id.recyclerView);
        String username= TokenManager.getInstance(getContext()).getUsername();

        retrofit2.Call<FetchedMedicationsResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .getMedications(username);

        call.enqueue(new Callback<FetchedMedicationsResponse>() {
            @Override
            public void onResponse(retrofit2.Call<FetchedMedicationsResponse> call, Response<FetchedMedicationsResponse> response) {
                if (response.isSuccessful()) {
                    List<MedicationSchema> medicationDetails = response.body().getMedicineDetails();
                    myModels.clear();
                    if (medicationDetails != null && !medicationDetails.isEmpty()) {
                        for (MedicationSchema medication : medicationDetails) {


                            myModels.add(medication);

                        }


                        adapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(getContext(), "No data found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    System.out.println("Response code: " + response.code()); // Log response code
                    Toast.makeText(getContext(), "Response code: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FetchedMedicationsResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error fetching data: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                System.out.println("Error fetching data: " + t.getMessage());
            }
        });




        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PlanCardAdapter(myModels, getContext());
        recyclerView.setAdapter(adapter);


        return view;
    }
}