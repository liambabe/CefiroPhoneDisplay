package com.example.cefirophonedisplay.Data;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.cefirophonedisplay.MainDisplay.MainActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class BluetoothConnection {
    private static final String TAG = "bluetoothCon";

    private MainActivity mainActivity;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothSocket mmSocket;
    private BluetoothDevice mmDevice;
    private ConnectionThread connectionThread;
    private Handler h;
    private StringBuilder stringBuilder;
    private final int RECIEVE_MESSAGE = 1;
    private final Handler mHandler;

    public BluetoothConnection(MainActivity mainactivity) {

        this.mainActivity = mainactivity;
        this.stringBuilder = new StringBuilder();

        mHandler = new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                byte[] writeBuf = (byte[]) msg.obj;
                int begin = (int)msg.arg1;
                int end = (int)msg.arg2;
                switch(msg.what)
                {
                    case 1:
                        String writeMessage = new String(writeBuf);
                        writeMessage = writeMessage.substring(begin, end);
                        //mainActivity.setCoolantTempDataLabel(writeMessage);
                        break;
                }
            }
        };

    }

    public void openBluetooth() throws IOException
    {
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); //Standard SerialPortService ID
        mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
        mmSocket.connect();
        connectionThread = new ConnectionThread(mmSocket);
        connectionThread.start();
    }

    public void closeBluetooth() throws IOException
    {
        mmSocket.close();
    }

    public void sendData(char c) throws IOException
    {
        connectionThread.write(c);
    }

    public void findBluetooth() throws IOException
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

    //class to handle sending data on its own thread
    private class ConnectionThread extends Thread {
        private OutputStream mmOutputStream;
        private InputStream mmInputStream;

        public ConnectionThread(BluetoothSocket socket) {
            InputStream tempIn = null;
            OutputStream tempOut = null;

            try {
                tempIn = socket.getInputStream();
                tempOut = socket.getOutputStream();
            } catch (IOException e) {}

            mmInputStream = tempIn;
            mmOutputStream = tempOut;

        }

        public void run() {
            byte[] buffer = new byte[1024];
            int begin = 0;
            int bytes = 0;
            while (true) {
                try {
                    bytes += mmInputStream.read(buffer, bytes, buffer.length -bytes);
                    for (int i = begin; i < bytes; i++) {
                        if (buffer[i] == "#".getBytes()[0]) {
                            mHandler.obtainMessage(1, begin, i, buffer).sendToTarget();
                            begin = i + 1;
                            if (i == bytes -1) {bytes = 0;begin = 0;}
                        }
                    }
                } catch (IOException e) {
                    Log.d(TAG, "error reading buffer: " + e.getMessage());
                    break;}
            }
        }

        public void write(char c) {
            Log.d(TAG, "Write Data");
            try {
                mmOutputStream.write(c);
            } catch (IOException e) {}
        }
    }
}
