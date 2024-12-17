package com.example.weatherForecast.service;

import com.example.weatherForecast.model.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.json.JSONObject;
import org.json.JSONArray;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
public class WeatherService {

    private static final String API_URL = "https://api.open-meteo.com/v1/forecast";
    private static final double panelPower = 2.5;
    private static final double panelEfficiency = 0.2;

    public WeatherResponse get7DayForecast(double latitude, double longitude) {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format("%s?latitude="+latitude+"&longitude="+longitude+"&current=surface_pressure&daily=temperature_2m_max,temperature_2m_min,sunshine_duration,precipitation_probability_max,weathercode&timezone=auto", API_URL);

        String response = restTemplate.getForObject(url, String.class);

        WeatherResponse weatherResponse = new WeatherResponse();

        JSONObject JSONresponse = new JSONObject(response);

        JSONObject daily = JSONresponse.getJSONObject("daily");

        JSONArray timeArray = daily.getJSONArray("time");
        JSONArray weatherCodeArray = daily.getJSONArray("weathercode");
        JSONArray maxTempArray = daily.getJSONArray("temperature_2m_max");
        JSONArray minTempArray = daily.getJSONArray("temperature_2m_min");
        JSONArray sunshineArray = daily.getJSONArray("sunshine_duration");
        // to dla WeeklySummary
        JSONArray precipitationPropabilityArray = daily.getJSONArray("precipitation_probability_max");

        List<String> timeList = convertJSONArrayToListOfStrings(timeArray);
        List<Integer> weatherCodeList = convertJSONArrayToListOfIntegers(weatherCodeArray);
        List<Double> maxTempList = convertJSONArrayToListOfDoubles(maxTempArray);
        List<Double> minTempList = convertJSONArrayToListOfDoubles(minTempArray);
        List<Double> sunshineList = convertJSONArrayToListOfDoubles(sunshineArray);
        List<Double> generatedEnergyList = calculateGeneratedEnergy(sunshineList);
        List<Integer> precipitationList = convertJSONArrayToListOfIntegers(precipitationPropabilityArray);

        weatherResponse.setTime(timeList);
        weatherResponse.setWeatherCode(weatherCodeList);
        weatherResponse.setTemperature_2m_max(maxTempList);
        weatherResponse.setTemperature_2m_min(minTempList);
        weatherResponse.setSunshineDuration(sunshineList);
        weatherResponse.setGeneratedEnergy(generatedEnergyList);
        weatherResponse.setPrecipitationPropability(precipitationList);

        return weatherResponse;
    }

    private List<Double> calculateGeneratedEnergy(List<Double> sunshineList){
        List<Double> energyList = new ArrayList<>();
        for(Double sunshineDuration: sunshineList){
            double hours = sunshineDuration/ 3600;
            double energy = panelPower * panelEfficiency * hours;
            BigDecimal energyBigDecimal = new BigDecimal(Double.toString(energy));
            energyBigDecimal = energyBigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
            double roundedEnergy = energyBigDecimal.doubleValue();
            energyList.add(roundedEnergy);
        }
        return energyList;
    }

    private List<String> convertJSONArrayToListOfStrings(JSONArray jsonArray) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(jsonArray.getString(i));
        }
        return list;
    }


    private List<Double> convertJSONArrayToListOfDoubles(JSONArray jsonArray) {
        List<Double> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(jsonArray.getDouble(i));
        }
        return list;
    }


    private List<Integer> convertJSONArrayToListOfIntegers(JSONArray jsonArray) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(jsonArray.getInt(i));
        }
        return list;
    }

    public WeeklySummary getWeeklySummary(double latitude, double longitude) {
        WeatherResponse forecast = get7DayForecast(latitude, longitude);

        if (forecast == null || forecast.getSunshineDuration() == null || forecast.getSunshineDuration().isEmpty()) {
            return new WeeklySummary(0, 0, 0, 0, "Brak danych do analizy.");
        }

        WeeklySummary weeklySummary = new WeeklySummary();

        List<Double> sunshineDurationList = forecast.getSunshineDuration();
        double sunshineDuration = 0;
        for(int i =0 ; i < sunshineDurationList.size(); i++){
            sunshineDuration = sunshineDuration + sunshineDurationList.get(i);
        }
        double averageSunshineDuration = sunshineDuration / sunshineDurationList.size();
        BigDecimal sunshineBigDecimal = new BigDecimal(Double.toString(averageSunshineDuration));
        sunshineBigDecimal = sunshineBigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
        double roundedAverageSunshineDuration = sunshineBigDecimal.doubleValue();
        weeklySummary.setAverageSunshine(roundedAverageSunshineDuration);

        List<Double> temperature_2m_maxList = forecast.getTemperature_2m_max();
        List<Double> temperature_2m_minList = forecast.getTemperature_2m_min();
        if (temperature_2m_maxList == null || temperature_2m_maxList.isEmpty() || temperature_2m_minList == null || temperature_2m_minList.isEmpty()) {
            return new WeeklySummary(0, 0, 0, 0, "Brak danych do analizy.");
        }
        weeklySummary.setMaxTemperature(Collections.max(temperature_2m_maxList));
        weeklySummary.setMinTemperature(Collections.min(temperature_2m_minList));

        String summary;
        int daysWithRain = 0;
        for(Integer perticipationPropability : forecast.getPrecipitationPropability()){
            if(perticipationPropability > 50){
                daysWithRain++;
            }
        }

        if (daysWithRain >= 4) {
            summary = "Tydzień deszczowy";
        } else {
            summary = "Tydzień suchy";
        }

        weeklySummary.setSummary(summary);


        return weeklySummary;
    }
}
