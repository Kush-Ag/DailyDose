package com.example.dailydoze.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailydoze.ComparatorActivity;
import com.example.dailydoze.MedicineDetailsActivity;
import com.example.dailydoze.Models.InventoryModel;
import com.example.dailydoze.Models.MedicationSchema;
import com.example.dailydoze.R;

import java.util.List;

public class PlanCardAdapter extends RecyclerView.Adapter<PlanCardAdapter.viewHolder>{

    List<MedicationSchema> list;
    Context context;

    public PlanCardAdapter(List<MedicationSchema> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.plan_card,parent,false);
        return new PlanCardAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        MedicationSchema model=list.get(position);
        holder.medName.setText(model.getMedName());
        holder.medDoses.setText("Total Daily Dose : "+model.getMedDaily());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MedicineDetailsActivity.class);

                // Pass the necessary data (e.g., medicine name) to the next activity
                intent.putExtra("medModel", model);

                // Start the new activity
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {


        TextView medName,  medDoses;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            medName=itemView.findViewById(R.id.medName);
            medDoses=itemView.findViewById(R.id.medDoses);
        }
    }
}
