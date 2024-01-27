package com.argon.weather.service;

import com.argon.weather.domain.Weather;
import com.argon.weather.repository.WeatherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WeatherService {

    private final WeatherRepository weatherRepository;

    public WeatherService(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    public void save(Weather weather) {
        weatherRepository.save(weather);
    }

    public List<Weather> findAll() {
        return weatherRepository.findAll();
    }

    public Optional<Weather> findById(Long id){
        Optional<Weather> result = weatherRepository.findById(id);
        return result;
    }

    public void saveAuto() {
        Weather weather = new Weather();
        weatherRepository.save(weather);
    }
}
