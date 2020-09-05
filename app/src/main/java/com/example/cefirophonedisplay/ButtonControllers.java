package com.example.cefirophonedisplay;

import android.view.View;

import java.io.IOException;

public class ButtonControllers {

    public class OnButtonController implements View.OnClickListener {

        private MainActivity mainPage;

        public OnButtonController(MainActivity mainpage) {
            this.mainPage = mainpage;
        }

        @Override
        public void onClick(View view) {
            try {
                mainPage.getBluetoothCon().sendData('O');
            } catch (IOException ex) {
                mainPage.setStatusLabel("Error sending data");
            }
        }
    }

    public class OffButtonController implements View.OnClickListener {

        private MainActivity mainPage;

        public OffButtonController(MainActivity mainpage) {
            this.mainPage = mainpage;
        }

        @Override
        public void onClick(View view) {
            try {
                mainPage.getBluetoothCon().sendData('F');
            } catch (IOException ex) {
                mainPage.setStatusLabel("Error sending data");
            }
        }
    }

    public class ConnectButtonController implements View.OnClickListener {

        private MainActivity mainPage;

        public ConnectButtonController(MainActivity mainpage) {
            this.mainPage = mainpage;
        }

        @Override
        public void onClick(View view) {
            try {
                mainPage.getBluetoothCon().findBluetooth();
                mainPage.getBluetoothCon().openBluetooth();
                mainPage.setStatusLabel("Bluetooth Connected");
            } catch (IOException ex) {
                mainPage.setStatusLabel("Error opening bluetooth");
            }
        }
    }

    public class CloseConnectionButtonController implements View.OnClickListener {

        private MainActivity mainPage;

        public CloseConnectionButtonController(MainActivity mainpage) {
            this.mainPage = mainpage;
        }

        @Override
        public void onClick(View view) {
            try {
                mainPage.getBluetoothCon().closeBluetooth();
                mainPage.setStatusLabel("Bluetooth Closed");
            } catch (IOException ex) {
                mainPage.setStatusLabel("Error closing bluetooth");
            }
        }
    }

}
