package com.example.cefirophonedisplay.MainDisplay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.cefirophonedisplay.Data.BluetoothConnection;
import com.example.cefirophonedisplay.Models.SensorValue;
import com.example.cefirophonedisplay.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView statusLabel;
    private BluetoothConnection bluetoothCon;
    private SensorValueRecycleViewAdapter sensorValueViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button conBtn = (Button)findViewById(R.id.ConnectBtn);
        Button closeBtn = (Button)findViewById(R.id.CloseBtn);

        this.statusLabel = (TextView)findViewById(R.id.StatusLabel);

        this.bluetoothCon = new BluetoothConnection(this);

        //initialize button controllers
        ButtonControllers buttonController = new ButtonControllers();

        conBtn.setOnClickListener(buttonController.new ConnectButtonController(this));
        closeBtn.setOnClickListener(buttonController.new CloseConnectionButtonController(this));

        // data to populate the RecyclerView with
        ArrayList<SensorValue> SensorValues = new ArrayList<>();
        SensorValues.add(new SensorValue(SensorValue.SensorIDs.CoolantTemp, "Coolant Temperature:", "0"));

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.SensorValueRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        sensorValueViewAdapter = new SensorValueRecycleViewAdapter(this, SensorValues);
        recyclerView.setAdapter(sensorValueViewAdapter);
    }

    public void setStatusLabel(String text) {
        this.statusLabel.setText(text);
    }

    public BluetoothConnection getBluetoothCon() {
        return this.bluetoothCon;
    }

}