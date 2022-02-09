package com.inyoucells.myproj.service.weather.model;

import lombok.Data;

@Data
public class WeatherClientConfig {
    private Long schedulerDelay;
    private Long schedulerInterval;
    private String city;
}
