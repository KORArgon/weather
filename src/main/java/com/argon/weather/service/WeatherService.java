package com.argon.weather.service;

import com.argon.weather.domain.Weather;
import com.argon.weather.repository.WeatherRepository;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {

    private final WeatherRepository weatherRepository;

    public WeatherService(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    public void save(Weather weather) {
        weatherRepository.save(weather);
    }
}
