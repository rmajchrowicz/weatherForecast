package com.example.weatherForecast.util;

public class EnergyCalculator {

    public static double calculateEnergy(double sunshineHours, double power, double efficiency) {
        return power * sunshineHours * efficiency;
    }
}
