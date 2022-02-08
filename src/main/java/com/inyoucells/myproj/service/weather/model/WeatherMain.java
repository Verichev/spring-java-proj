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
    @JsonProperty("pressure")
    private final int pressure;
    @JsonProperty("humidity")
    private final int humidity;
}
