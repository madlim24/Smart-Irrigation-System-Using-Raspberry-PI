package com.example.authenticatorapp;

public class Data {
    private String soilMoisture;
    private String temperature;
    private String humidity;

    public Data(String soilMoisture, String temperature, String humidity) {
        this.soilMoisture = soilMoisture;
        this.temperature = temperature;
        this.humidity = humidity;
    }

    public String getSoilMoisture() {
        return soilMoisture;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getHumidity() {
        return humidity;
    }
}
