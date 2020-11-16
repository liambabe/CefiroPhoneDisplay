package com.example.cefirophonedisplay.MainDisplay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.example.cefirophonedisplay.Data.SensorRawDataParser;
import com.example.cefirophonedisplay.Net.BluetoothConnection;
import com.example.cefirophonedisplay.Models.SensorDisplayValue;
import com.example.cefirophonedisplay.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView statusLabel;
    private BluetoothConnection bluetoothCon;
    private SensorValueRecycleViewAdapter sensorValueViewAdapter;
    private SensorRawDataParser sensorValueParser;
    private boolean isChecked = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorValueParser = new SensorRawDataParser();

        Button conBtn = (Button)findViewById(R.id.ConnectBtn);
        Button closeBtn = (Button)findViewById(R.id.CloseBtn);

        this.statusLabel = (TextView)findViewById(R.id.StatusLabel);

        this.bluetoothCon = new BluetoothConnection(this);

        //initialize button controllers
        ButtonControllers buttonController = new ButtonControllers();

        conBtn.setOnClickListener(buttonController.new ConnectButtonController(this));
        closeBtn.setOnClickListener(buttonController.new CloseConnectionButtonController(this));

        // data to populate the RecyclerView with
        ArrayList<SensorDisplayValue> SensorValues = new ArrayList<>();
        SensorValues.add(new SensorDisplayValue(SensorDisplayValue.SensorIDs.CoolantTemp, "Coolant Temperature:", "0"));
        SensorValues.add(new SensorDisplayValue(SensorDisplayValue.SensorIDs.AirIntakeTemp, "Air Intake Temperature:", "0"));
        SensorValues.add(new SensorDisplayValue(SensorDisplayValue.SensorIDs.MapSensorPressure, "Map Sensor Pressure:", "0"));
        SensorValues.add(new SensorDisplayValue(SensorDisplayValue.SensorIDs.FanStatus, "Fan Status:", "off"));

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.SensorValueRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        sensorValueViewAdapter = new SensorValueRecycleViewAdapter(this, SensorValues);
        recyclerView.setAdapter(sensorValueViewAdapter);
    }

    /**
     * adds in options menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_settings, menu);
        return true;
    }

    /**
     * function to prepare and validate options menu
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem checkable = menu.findItem(R.id.MenuSettingsColourFormatButton);
        checkable.setChecked(isChecked);
        return true;
    }

    /**
     * function to process input on our settings menu
     * @param item setting menu option pressed
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.MenuSettingsColourFormatButton:
                isChecked = !item.isChecked();
                item.setChecked(isChecked);
                return true;
            case R.id.MenuSettingsDisplaysSettingButton:
                //popup
                return true;
            default:
                return false;
        }
    }

    public void setStatusLabel(String text) {
        this.statusLabel.setText(text);
    }

    public void setSensorValues(String values) {
        this.sensorValueViewAdapter.setValues(this.sensorValueParser.parseData(values));
    }

    public BluetoothConnection getBluetoothCon() {
        return this.bluetoothCon;
    }

    /**
     * getting for if the user has selected colour formatting
     * within the options menu
     * @return boolean, true if selected
     */
    public boolean getColourFormatMode() {
        return isChecked;
    }

}