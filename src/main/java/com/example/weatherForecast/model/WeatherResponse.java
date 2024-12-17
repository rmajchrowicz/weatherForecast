package com.example.weatherForecast.model;

import com.example.weatherForecast.util.EnergyCalculator;


import java.util.ArrayList;
import java.util.List;

public class WeatherResponse {

    private List<String> time;
    private List<Double> temperature_2m_max;
    private List<Double> temperature_2m_min;
    private List<Double> sunshineDuration;
    private List<Integer> weatherCode;
    private List<Double> generatedEnergy;
    private List<Integer> precipitationPropability;

    public List<String> getTime() {
        return time;
    }

    public void setTime(List<String> time) {
        this.time = time;
    }

    public List<Double> getTemperature_2m_max() {
        return temperature_2m_max;
    }

    public void setTemperature_2m_max(List<Double> temperature_2m_max) {
        this.temperature_2m_max = temperature_2m_max;
    }

    public List<Double> getTemperature_2m_min() {
        return temperature_2m_min;
    }

    public void setTemperature_2m_min(List<Double> temperature_2m_min) {
        this.temperature_2m_min = temperature_2m_min;
    }

    public List<Double> getSunshineDuration() {
        return sunshineDuration;
    }

    public void setSunshineDuration(List<Double> sunshineDuration) {
        this.sunshineDuration = sunshineDuration;
    }

    public List<Integer> getWeatherCode() {
        return weatherCode;
    }

    public void setWeatherCode(List<Integer> weatherCode) {
        this.weatherCode = weatherCode;
    }


    // Metoda do mapowania danych z API do listy DailyForecast
    public List<DailyForecast> toDailyForecasts() {
        if (time == null || time.size() == 0) {
            return null;
        }

        // Tworzymy listę DailyForecast na podstawie odpowiedzi z API
        List<DailyForecast> dailyForecasts = new ArrayList<>();
        for (int i = 0; i < time.size(); i++) {
            DailyForecast forecast = new DailyForecast();
            forecast.setDate(time.get(i));
            forecast.setMaxTemperature(temperature_2m_max.get(i));
            forecast.setMinTemperature(temperature_2m_min.get(i));
            forecast.setSunshineDuration(sunshineDuration.get(i));
            forecast.setWeatherCode(weatherCode.get(i));
            forecast.setPressure(0);  // Brak danych o ciśnieniu bo jest tylko current
            forecast.setEstimatedEnergy(EnergyCalculator.calculateEnergy(sunshineDuration.get(i), 2.5, 0.2));

            dailyForecasts.add(forecast);
        }
        return dailyForecasts;
    }

    public List<Double> getGeneratedEnergy() {
        return generatedEnergy;
    }

    public void setGeneratedEnergy(List<Double> generatedEnergy) {
        this.generatedEnergy = generatedEnergy;
    }

    public List<Integer> getPrecipitationPropability() {
        return precipitationPropability;
    }

    public void setPrecipitationPropability(List<Integer> precipitationPropability) {
        this.precipitationPropability = precipitationPropability;
    }
}
