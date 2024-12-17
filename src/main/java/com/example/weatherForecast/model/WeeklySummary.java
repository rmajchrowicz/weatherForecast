package com.example.weatherForecast.model;

public class WeeklySummary {
    private double averagePressure;
    private double averageSunshine;
    private double minTemperature;
    private double maxTemperature;
    private String summary;

    public WeeklySummary() {

    };

    public WeeklySummary(double averagePressure, double averageSunshine, double minTemperature, double maxTemperature, String summary) {
        this.averagePressure = averagePressure;
        this.averageSunshine = averageSunshine;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.summary = summary;
    }

    public double getAveragePressure() {
        return averagePressure;
    }

    public void setAveragePressure(double averagePressure) {
        this.averagePressure = averagePressure;
    }

    public double getAverageSunshine() {
        return averageSunshine;
    }

    public void setAverageSunshine(double averageSunshine) {
        this.averageSunshine = averageSunshine;
    }

    public double getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(double minTemperature) {
        this.minTemperature = minTemperature;
    }

    public double getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }


}
