package com.example.cefirophonedisplay;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView statusLabel;
    private BluetoothConnection bluetoothCon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button onBtn = (Button)findViewById(R.id.OnBtn);
        Button offBtn = (Button)findViewById(R.id.OffBtn);
        Button conBtn = (Button)findViewById(R.id.ConnectBtn);
        Button closeBtn = (Button)findViewById(R.id.CloseBtn);
        statusLabel = (TextView)findViewById(R.id.StatusLabel);

        this.bluetoothCon = new BluetoothConnection(this);

        //initialize button controllers
        ButtonControllers buttonController = new ButtonControllers();

        onBtn.setOnClickListener(buttonController.new OnButtonController(this));
        offBtn.setOnClickListener(buttonController.new OffButtonController(this));
        conBtn.setOnClickListener(buttonController.new ConnectButtonController(this));
        closeBtn.setOnClickListener(buttonController.new CloseConnectionButtonController(this));
    }

    public void setStatusLabel(String text) {
        this.statusLabel.setText(text);
    }

    public BluetoothConnection getBluetoothCon() {
        return this.bluetoothCon;
    }

}