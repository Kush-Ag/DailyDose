package com.example.dailydoze.Fragments;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dailydoze.Adapters.MedicineReminderAdapter;
import com.example.dailydoze.ApplicationClass.ReminderBroadcast;
import com.example.dailydoze.Models.DoseItem;
import com.example.dailydoze.Models.FetchedMedicationsResponse;
import com.example.dailydoze.Models.MedicationSchema;
import com.example.dailydoze.Models.MedicineReminderModel;
import com.example.dailydoze.R;
import com.example.dailydoze.RetrofitClient;
import com.example.dailydoze.TokenManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {



    private RecyclerView recyclerView;
    private MedicineReminderAdapter adapter;
    private List<MedicineReminderModel> myMedRemModels = new ArrayList<>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_home, container, false);



        recyclerView=view.findViewById(R.id.recyclerView);
        String username= TokenManager.getInstance(getContext()).getUsername();

        retrofit2.Call<FetchedMedicationsResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .getMedications(username);

        call.enqueue(new Callback<FetchedMedicationsResponse>() {
            @Override
            public void onResponse(Call<FetchedMedicationsResponse> call, Response<FetchedMedicationsResponse> response) {
                if (response.isSuccessful()) {
                    List<MedicationSchema> medicationDetails = response.body().getMedicineDetails();

                    if (medicationDetails != null && !medicationDetails.isEmpty()) {
                        myMedRemModels.clear();
                        for (MedicationSchema medication : medicationDetails) {
                            List<DoseItem> doses = medication.getDose();
                            if (doses != null && !doses.isEmpty()) {
                                for (int j = 0; j < doses.size(); j++) {
                                    DoseItem doseItem = doses.get(j);
                                    long notificationTime = covertTime(doseItem.getTime());

                                    myMedRemModels.add(new MedicineReminderModel(medication.getMedName(),doseItem.getDose(),doseItem.getTime()));

                                    if (notificationTime > System.currentTimeMillis()%86400000) { // Schedule only future times
                                        int uniqueNotificationId = (medication.get_id().hashCode() + j); // Unique ID per dose
                                        scheduleNotification(notificationTime, uniqueNotificationId, medication.getMedName(), doseItem.getDose(), medication.get_id());
                                    }
                                }
                            }
                        }

                        adapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(getContext(), "No data found", Toast.LENGTH_SHORT).show();
                    }
                } else {
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
        adapter = new MedicineReminderAdapter(myMedRemModels, getContext());
        recyclerView.setAdapter(adapter);


        return view;
    }



    private long covertTime(String time) {
        // Define the expected format of the input string (e.g., "hh:mm AM/PM")
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());

        try {
            // Parse the string into a Date object
            Date date = dateFormat.parse(time);

            // Use Calendar to convert the Date into a long (milliseconds from epoch)
            Calendar calendar = Calendar.getInstance();
            if (date != null) {
                calendar.setTime(date);

                // If we are parsing time without the date, we should adjust only the time part
                // Keep today's date and just set the time accordingly
                Calendar now = Calendar.getInstance();
                calendar.set(Calendar.YEAR, now.get(Calendar.YEAR));
                calendar.set(Calendar.MONTH, now.get(Calendar.MONTH));
                calendar.set(Calendar.DAY_OF_MONTH, now.get(Calendar.DAY_OF_MONTH));

                // Return time in milliseconds
                return calendar.getTimeInMillis();
            }

        } catch (ParseException e) {
            e.printStackTrace(); // Handle the parsing exception
        }

        return -1; // Return -1 in case of failure
    }

    private void scheduleNotification(long timeInMillis, int notificationId, String medName, String doseQty, String medId) {
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getContext(), ReminderBroadcast.class);
        intent.putExtra("medName", medName);
        intent.putExtra("doseQty", doseQty);
        intent.putExtra("medId", medId);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getContext(),
                notificationId,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        if (alarmManager != null) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent);
        }
    }

}