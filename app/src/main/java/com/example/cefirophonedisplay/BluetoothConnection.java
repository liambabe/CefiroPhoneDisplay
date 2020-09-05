package com.example.cefirophonedisplay;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class BluetoothConnection {

    private MainActivity mainActivity;
    private BluetoothAdapter mBluetoothAdapter;
    private OutputStream mmOutputStream;
    private BluetoothSocket mmSocket;
    private BluetoothDevice mmDevice;

    public BluetoothConnection(MainActivity mainactivity) {
        this.mainActivity = mainactivity;
    }

    void findBluetooth() throws IOException
    {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (mBluetoothAdapter == null) {
            throw new IOException("No Bluetooth device found");
        }

        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            this.mainActivity.startActivityForResult(enableBluetooth, 0);
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
    }

    void openBluetooth() throws IOException
    {
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); //Standard SerialPortService ID
        mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
        mmSocket.connect();
        mmOutputStream = mmSocket.getOutputStream();
    }

    void closeBluetooth() throws IOException
    {
        mmOutputStream.close();
        mmSocket.close();
    }

    void sendData(char c) throws IOException
    {
        mmOutputStream.write(c);
    }

}
