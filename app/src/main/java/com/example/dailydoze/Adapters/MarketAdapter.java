package com.example.dailydoze.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailydoze.Models.MarketModel;
import com.example.dailydoze.R;

import java.util.List;

public class MarketAdapter extends RecyclerView.Adapter<MarketAdapter.viewHolder>{

    List<MarketModel> list;
    Context context;

    public MarketAdapter(List<MarketModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.market_card,parent,false);
        return new MarketAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        MarketModel model=list.get(position);
        holder.medItem.setText(model.getName());
        holder.medPrice.setText("Price : ₹ "+model.getPrice());
        holder.medPlatform.setText("Platform : "+model.getVendor());

        holder.buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String uriS=model.getLink();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uriS));
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {


        TextView medItem,  medPrice, medPlatform;
        Button buyButton;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            medItem=itemView.findViewById(R.id.medItem);
            medPrice=itemView.findViewById(R.id.medPrice);
            medPlatform=itemView.findViewById(R.id.medPlatform);
            buyButton=itemView.findViewById(R.id.buyButton);
        }
    }
}
