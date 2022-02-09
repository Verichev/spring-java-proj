package com.inyoucells.myproj.service.weather.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class WeatherMain {
    private final float temp;
    @JsonProperty("feels_like")
    private final float feelsLike;
    @JsonProperty("temp_min")
    private final float tempMin;
    @JsonProperty("temp_max")
    private final float tempMax;
    private final int pressure;
    private final int humidity;
}
