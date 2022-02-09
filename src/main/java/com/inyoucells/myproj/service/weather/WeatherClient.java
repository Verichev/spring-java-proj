package com.inyoucells.myproj.service.weather;

import com.inyoucells.myproj.service.weather.model.WeatherResponse;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "WeatherForecast", url = "http://api.openweathermap.org")
public interface WeatherClient {

    @GetMapping("/data/2.5/weather")
    WeatherResponse getWeather(@RequestParam("q") String city, @RequestParam String apiKey);
}
