package com.example.cefirophonedisplay.MainDisplay;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cefirophonedisplay.Models.SensorValue;
import com.example.cefirophonedisplay.R;
import com.example.cefirophonedisplay.Models.SensorDisplayValue;

import java.util.ArrayList;
import java.util.List;

public class SensorValueRecycleViewAdapter extends RecyclerView.Adapter<SensorValueRecycleViewAdapter.ViewHolder> {

    private List<SensorDisplayValue> mData;
    private LayoutInflater mInflater;
    private MainActivity mainPage;

    // data is passed into the constructor
    SensorValueRecycleViewAdapter(Context context, List<SensorDisplayValue> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mainPage = (MainActivity) context;
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
        SensorDisplayValue sensor = mData.get(position);
        holder.displayText.setText(sensor.getDisplayString());
        holder.displayValue.setText(sensor.getValue());

        formatDataConditionals(holder, sensor);
    }

    /**
     * Formats sensor values based on rules set in this method
     * @param holder View holder that contains our recycle view text boxes
     * @param sensor sensor value to determine format
     */
    public void formatDataConditionals(ViewHolder holder, SensorDisplayValue sensor) {

        if (!this.mainPage.getColourFormatMode()) {
            holder.displayValue.setTextColor(Color.parseColor("#000000"));
            return;
        }

        if (sensor.getSensorID() == SensorValue.SensorIDs.CoolantTemp) {
            int coolantTemperature = Integer.parseInt(sensor.getValue());

            if (coolantTemperature >= 0 && coolantTemperature < 78) {
                holder.displayValue.setTextColor(Color.parseColor("#0000FF"));
            } else if (coolantTemperature >= 78 && coolantTemperature < 86) {
                holder.displayValue.setTextColor(Color.parseColor("#000000"));
            } else if (coolantTemperature >= 86) {
                holder.displayValue.setTextColor(Color.parseColor("#FF0000"));
            } else {
                holder.displayValue.setTextColor(Color.parseColor("#000000"));
            }
        }
        else if (sensor.getSensorID() == SensorValue.SensorIDs.MapSensorPressure) {
            double mapPressure = Double.parseDouble(sensor.getValue());

            if (mapPressure >= 1.6) {
                holder.displayValue.setTextColor(Color.parseColor("#FF0000"));
            } else {
                holder.displayValue.setTextColor(Color.parseColor("#000000"));
            }
        }


    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setValues(ArrayList<SensorValue> values) {

        for (SensorValue value : values) {
            for (SensorDisplayValue data : mData) {
                if (data.equals(value)) {
                    data.setValue(value.getValue());
                    continue;
                }
            }
        }
        notifyDataSetChanged();
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
