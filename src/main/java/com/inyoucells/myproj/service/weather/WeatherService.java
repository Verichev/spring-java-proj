package com.inyoucells.myproj.service.weather;

import com.inyoucells.myproj.service.weather.model.SettingConfig;
import com.inyoucells.myproj.service.weather.model.WeatherResponse;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j;

@Log4j
@Service
public class WeatherService {
    private final WeatherClient weatherClient;
    private final SettingConfig settingConfig;

    public WeatherService(WeatherClient weatherClient, SettingConfig settingConfig) {
        this.weatherClient = weatherClient;
        this.settingConfig = settingConfig;
    }

    @Scheduled(initialDelayString = "${weather.schedulerDelay}", fixedDelayString = "${weather.schedulerInterval}")
    public void weatherFetcher() {
        System.out.println("configProperties: " + settingConfig);
        WeatherResponse weatherResponse = weatherClient.getWeather();
        log.debug("london weather: " + weatherResponse.getWeatherMain());
    }
}
