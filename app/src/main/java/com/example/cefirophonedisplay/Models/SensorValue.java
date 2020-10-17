package com.example.cefirophonedisplay.Models;

public class SensorValue {

    private final SensorIDs SensorID;
    private String Value;

    public SensorValue(SensorIDs ID, String value) {
        this.SensorID = ID;
        this.Value = value;
    }

    public String getSensorID() {
        return this.SensorID.toString();
    }

    public String getValue() {
        return this.Value;
    }

    public void setValue(String value) {
        this.Value = value;
    }

    public boolean equals(SensorValue value) {
        if (this.getSensorID().equals(value.getSensorID())) {
            return true;
        } else {
            return false;
        }
    }

    public enum SensorIDs {
        CoolantTemp,
        AirIntakeTemp,
        MapSensorPressure,
        FanStatus
    }

}
