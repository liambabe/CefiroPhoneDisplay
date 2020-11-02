package com.example.cefirophonedisplay.Data;

import com.example.cefirophonedisplay.Models.SensorValue;
import java.util.ArrayList;

public class SensorRawDataParser {

    public ArrayList<SensorValue> parseData(String Data) {
        ArrayList<SensorValue> sensorValueList = new ArrayList<SensorValue>();

        String[] values = Data.split(",");

        sensorValueList.add(new SensorValue(SensorValue.SensorIDs.CoolantTemp, values[0]));
        sensorValueList.add(new SensorValue(SensorValue.SensorIDs.MapSensorPressure, values[1]));
        sensorValueList.add(new SensorValue(SensorValue.SensorIDs.AirIntakeTemp, values[2]));
        sensorValueList.add(new SensorValue(SensorValue.SensorIDs.FanStatus, values[3]));

        return sensorValueList;
    }

}
