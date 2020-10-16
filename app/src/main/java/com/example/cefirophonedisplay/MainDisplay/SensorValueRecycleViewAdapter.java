package com.example.cefirophonedisplay.MainDisplay;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cefirophonedisplay.R;
import com.example.cefirophonedisplay.Models.SensorValue;

import java.util.List;

public class SensorValueRecycleViewAdapter extends RecyclerView.Adapter<SensorValueRecycleViewAdapter.ViewHolder> {

    private List<SensorValue> mData;
    private LayoutInflater mInflater;

    // data is passed into the constructor
    SensorValueRecycleViewAdapter(Context context, List<SensorValue> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SensorValue sensor = mData.get(position);
        holder.displayText.setText(sensor.getDisplayString());
        holder.displayValue.setText(sensor.getValue());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView displayText;
        TextView displayValue;

        ViewHolder(View itemView) {
            super(itemView);
            displayText = itemView.findViewById(R.id.SensorDisplayText);
            displayValue = itemView.findViewById(R.id.SensorValue);
        }

    }

}