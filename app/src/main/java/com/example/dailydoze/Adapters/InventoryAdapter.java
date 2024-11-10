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
import com.example.dailydoze.EditInventoryActivity;
import com.example.dailydoze.Models.InventoryModel;
import com.example.dailydoze.R;

import java.util.List;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.viewHolder>{

    List<InventoryModel> list;
    Context context;

    public InventoryAdapter(List<InventoryModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.inventory_card,parent,false);
        return new InventoryAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        InventoryModel model=list.get(position);
        holder.medName.setText(model.getMedName());
        holder.medQty.setText("Quantity left : "+model.getMedQty());
        holder.medDays.setText("Sufficient for : "+model.getMedDays()+" days");

        holder.orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, ComparatorActivity.class);

                // Pass the necessary data (e.g., medicine name) to the next activity
                intent.putExtra("medName", model.getMedName());

                // Start the new activity
                context.startActivity(intent);

            }
        });

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(context, EditInventoryActivity.class);

                // Pass the necessary data (e.g., medicine name) to the next activity
                intent1.putExtra("invModel", model);

                // Start the new activity
                context.startActivity(intent1);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class viewHolder extends RecyclerView.ViewHolder {


        TextView medName,  medQty, medDays;
        Button orderButton;
        ImageView editButton;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            medName=itemView.findViewById(R.id.medName);
            medQty=itemView.findViewById(R.id.medQty);
            medDays=itemView.findViewById(R.id.medDays);
            orderButton=itemView.findViewById(R.id.orderButton);
            editButton=itemView.findViewById(R.id.editButton);
        }
    }

}
