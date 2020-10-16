package com.example.cefirophonedisplay.Models;

public class SensorValue {

    private final SensorIDs SensorID;
    private final String DisplayString;
    private String Value;

    public SensorValue(SensorIDs ID, String displaystring, String value) {
        this.SensorID = ID;
        this.DisplayString = displaystring;
        this.Value = value;
    }

    public String getSensorID() {
        return this.SensorID.toString();
    }

    public String getDisplayString() {
        return this.DisplayString;
    }

    public String getValue() {
        return this.Value;
    }

    public void setValue(String value) {
        this.Value = value;
    }

    public enum SensorIDs {
        CoolantTemp,
        AirIntakeTemp,
        MapSensorPressure,
        FanStatus
    }

}
