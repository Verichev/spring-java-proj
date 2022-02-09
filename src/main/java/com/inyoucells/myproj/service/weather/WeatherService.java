package com.inyoucells.myproj.service.weather;

import com.inyoucells.myproj.service.weather.model.WeatherClientConfig;
import com.inyoucells.myproj.service.weather.model.WeatherResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
@RequiredArgsConstructor
public class WeatherService {
    private final WeatherClient weatherClient;
    private final WeatherClientConfig settingConfig;

    @Value("${keystore.weatherKey}")
    private String apiKey;

    @Scheduled(initialDelayString = "${weather.schedulerDelay}", fixedDelayString = "${weather.schedulerInterval}")
    public void weatherFetcher() {
        WeatherResponse weatherResponse = weatherClient.getWeather(settingConfig.getCity(), apiKey);
        log.debug(settingConfig.getCity() + " weather: " + weatherResponse.getWeatherMain());
    }
}
