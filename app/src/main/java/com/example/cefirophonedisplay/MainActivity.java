package com.example.cefirophonedisplay;

import androidx.appcompat.app.AppCompatActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    TextView statusLabel;
    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;
    OutputStream mmOutputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button onBtn = (Button)findViewById(R.id.OnBtn);
        Button offBtn = (Button)findViewById(R.id.OffBtn);
        Button conBtn = (Button)findViewById(R.id.ConnectBtn);
        Button closeBtn = (Button)findViewById(R.id.CloseBtn);
        statusLabel = (TextView)findViewById(R.id.StatusLabel);

        //On Button
        onBtn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                try {
                    sendData('o');
                } catch (IOException ex) {
                    statusLabel.setText("Error sending data");
                }
            }
        });

        //Off Button
        offBtn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                try {
                    sendData('f');
                } catch (IOException ex) {
                    statusLabel.setText("Error sending data");
                }
            }
        });

        //Connect Button
        conBtn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                try {
                    findBluetooth();
                    openBluetooth();
                } catch (IOException ex) {
                    statusLabel.setText("Error opening bluetooth");
                }
            }
        });

        closeBtn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                try {
                    closeBluetooth();
                } catch (IOException ex) {
                    statusLabel.setText("Error closing bluetooth");
                }
            }
        });
    }

    void findBluetooth()
    {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (mBluetoothAdapter == null) {
            statusLabel.setText("No bluetooth adapter available");
        }

        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetooth, 0);
        }

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if(pairedDevices.size() > 0) {
            for(BluetoothDevice device : pairedDevices) {
                if(device.getName().equals("HC-06")) {
                    mmDevice = device;
                    break;
                }
            }
        }

        statusLabel.setText("Bluetooth Device Found");
    }

    void openBluetooth() throws IOException
    {
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); //Standard SerialPortService ID
        mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
        mmSocket.connect();
        mmOutputStream = mmSocket.getOutputStream();

        statusLabel.setText("Bluetooth Opened");
    }

    void closeBluetooth() throws IOException
    {
        mmOutputStream.close();
        mmSocket.close();
        statusLabel.setText("Bluetooth Closed");
    }

    void sendData(char c) throws IOException
    {
        mmOutputStream.write(c);
        statusLabel.setText("Data Sent");
    }
}