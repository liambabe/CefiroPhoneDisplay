package com.example.cefirophonedisplay.Models;

public class SensorDisplayValue extends SensorValue {

    private final String DisplayString;

    public SensorDisplayValue(SensorIDs ID, String displaystring, String value) {
        super(ID, value);
        this.DisplayString = displaystring;
    }

    public String getDisplayString() {
        return this.DisplayString;
    }

}
