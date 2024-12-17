package com.example.weatherForecast.model;

public class DailyForecast {
    private String date;
    private double maxTemperature;
    private double minTemperature;
    private double sunshineDuration;
    private double pressure;
    private int weatherCode;
    private double estimatedEnergy;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public double getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(double minTemperature) {
        this.minTemperature = minTemperature;
    }

    public double getSunshineDuration() {
        return sunshineDuration;
    }

    public void setSunshineDuration(double sunshineDuration) {
        this.sunshineDuration = sunshineDuration;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public int getWeatherCode() {
        return weatherCode;
    }

    public void setWeatherCode(int weatherCode) {
        this.weatherCode = weatherCode;
    }

    public double getEstimatedEnergy() {
        return estimatedEnergy;
    }

    public void setEstimatedEnergy(double estimatedEnergy) {
        this.estimatedEnergy = estimatedEnergy;
    }
}
