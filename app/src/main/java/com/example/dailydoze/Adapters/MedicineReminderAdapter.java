package com.example.dailydoze.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailydoze.Models.MedicineReminderModel;
import com.example.dailydoze.R;

import java.util.List;

public class MedicineReminderAdapter extends RecyclerView.Adapter<MedicineReminderAdapter.viewHolder>{
    List<MedicineReminderModel> list;
    Context context;

    public MedicineReminderAdapter(List<MedicineReminderModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.medicine_reminder_card,parent,false);
        return new MedicineReminderAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        MedicineReminderModel model=list.get(position);
        holder.medName.setText(model.getMedName());
        holder.medDose.setText("Dose : "+model.getMedDose());
        holder.medTime.setText("Time : "+model.getMedTime());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {


        TextView medName,  medDose, medTime;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            medName=itemView.findViewById(R.id.medName);
            medDose=itemView.findViewById(R.id.medDose);
            medTime=itemView.findViewById(R.id.medTime);
        }
    }
}
