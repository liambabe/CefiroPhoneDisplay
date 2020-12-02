package com.example.cefirophonedisplay.Data;

import com.example.cefirophonedisplay.Models.SensorValue;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Sensor Raw Data class simply parses raw data into an
 * JSON Object and returns an Array List of Sensor Values
 *
 * @author liam
 */
public class SensorRawDataParser {

    /**
     * Getter method to Parse Raw data into an array list
     * of engine sensors values from our Arduino
     *
     * @param Data String of raw sensor data
     * @return A list of Sensor Value modelled data
     */
    public ArrayList<SensorValue> parseData(String Data) {
        try {
            JSONObject JSONdata = new JSONObject(Data);
            return this.parse(JSONdata);
        } catch (JSONException e) {
            return null;
        }
    }

    /**
     * Convert our JSON data into an
     * Array List of Sensor Value Data
     *
     * @param Data JSONObject of sensor data
     * @return A list of Sensor Value modelled data
     */
    private ArrayList<SensorValue> parse(JSONObject Data) {
        ArrayList<SensorValue> sensorValueList = new ArrayList<SensorValue>();

        try { sensorValueList.add(new SensorValue(SensorValue.SensorIDs.CoolantTemp,
                                                    Data.getString("CTemp")));
        } catch (JSONException e) {}

        try {
            sensorValueList.add(new SensorValue(SensorValue.SensorIDs.MapSensorPressure,
                                                Data.getString("MSPre")));
        } catch (JSONException e) {}

        try {
            sensorValueList.add(new SensorValue(SensorValue.SensorIDs.AirIntakeTemp,
                                                Data.getString("AITemp")));
        } catch (JSONException e) {}

        try {
            sensorValueList.add(new SensorValue(SensorValue.SensorIDs.FanStatus,
                                                Data.getString("FanStatus")));
        } catch (JSONException e) {}


        return sensorValueList;
    }
}
