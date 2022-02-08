package com.inyoucells.myproj.service.weather;

import com.inyoucells.myproj.service.weather.model.WeatherResponse;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "WeatherForecast", url = "http://api.openweathermap.org")
public interface WeatherClient {

    @GetMapping("/data/2.5/weather?q=London&apiKey=${weather.apiKey}")
    WeatherResponse getWeather();
}
