package com.example.dailydoze.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailydoze.Models.DoseCardModel;
import com.example.dailydoze.Models.MarketModel;
import com.example.dailydoze.R;

import java.util.List;

public class DoseCardAdapter extends RecyclerView.Adapter<DoseCardAdapter.viewHolder> {

    List<DoseCardModel> list;
    Context context;

    public DoseCardAdapter(List<DoseCardModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.all_dose_card,parent,false);
        return new DoseCardAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        DoseCardModel model=list.get(position);
        holder.doseQty.setText("Dose : "+model.getDoseQty());
        holder.doseTime.setText("@ Time : "+model.getDoseTime());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {

        TextView doseQty, doseTime;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            doseQty = itemView.findViewById(R.id.doseQty);
            doseTime = itemView.findViewById(R.id.doseTime);
        }
    }
}
