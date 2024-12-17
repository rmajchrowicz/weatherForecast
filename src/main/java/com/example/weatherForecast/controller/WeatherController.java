package com.example.weatherForecast.controller;

import com.example.weatherForecast.model.WeatherResponse;
import com.example.weatherForecast.model.WeeklySummary;
import com.example.weatherForecast.service.WeatherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/forecast")
    public WeatherResponse getForecast(@RequestParam double latitude,@RequestParam double longitude) {
        return weatherService.get7DayForecast(latitude,longitude);
    }

    @GetMapping("/summary")
    public WeeklySummary getWeeklySummary(@RequestParam double latitude,@RequestParam double longitude) {
        return weatherService.getWeeklySummary(latitude,longitude);
    }
}
