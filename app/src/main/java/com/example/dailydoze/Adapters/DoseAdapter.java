package com.example.dailydoze.Adapters;

import android.app.TimePickerDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailydoze.Models.DoseItem;
import com.example.dailydoze.R;

import java.util.Calendar;
import java.util.List;

public class DoseAdapter  extends RecyclerView.Adapter<DoseAdapter.viewHolder> {
    private List<DoseItem> doseData;

    public DoseAdapter(List<DoseItem> doseData) {
        this.doseData = doseData;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_med_card, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        DoseItem currentItem = doseData.get(position);
        holder.doseQtyInput.setText(currentItem.getDose());
        holder.doseTimeInput.setText(currentItem.getTime());


        holder.doseQtyInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                currentItem.setDose(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        holder.doseTimeInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(holder, currentItem);
            }
        });

        holder.reduceButton.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION && doseData.size() > 1) { // Ensure at least one item remains
                doseData.remove(pos);
                notifyItemRemoved(pos);
                notifyItemRangeChanged(pos, doseData.size()); // Update the positions of remaining items
            } else if (doseData.size() == 1) {
                Toast.makeText(holder.itemView.getContext(), "At least one dose is required", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return doseData.size();
    }

    private void showTimePickerDialog(viewHolder holder, DoseItem currentItem) {
        // Get the current time
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Create a TimePickerDialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(holder.itemView.getContext(),
                (view, selectedHour, selectedMinute) -> {
                    // Format the time to 12-hour format with AM/PM
                    String time = String.format("%02d:%02d %s",
                            selectedHour % 12 == 0 ? 12 : selectedHour % 12,
                            selectedMinute,
                            selectedHour < 12 ? "AM" : "PM");

                    // Set formatted time to button and update currentItem
                    holder.doseTimeInput.setText(time);
                    currentItem.setTime(time); // Update the current item with the selected time
                }, hour, minute, false); // false for 12-hour format
        timePickerDialog.show();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        EditText doseQtyInput;
        TextView doseTimeInput;
        ImageView reduceButton;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            doseQtyInput = itemView.findViewById(R.id.doseQtyInput);
            doseTimeInput = itemView.findViewById(R.id.doseTimeInput);
            reduceButton = itemView.findViewById(R.id.reduceButton);
        }
    }


}
