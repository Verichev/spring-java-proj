package com.inyoucells.myproj.service.weather.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class WeatherResponse {
    private final String cod;
    private final String name;
    private final int id;
    private final int timezone;
    @JsonProperty("main")
    private final WeatherMain weatherMain;
}
