package com.argon.weather.controller;

import com.argon.weather.domain.Weather;
import com.argon.weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WeatherApiController {

    // weatherService
    private final WeatherService weatherService;

    @GetMapping("/weather/weatherListApi")
    public Page<Weather> weatherListApi(Pageable pageable){
        return weatherService.findAll(pageable);
    }

    @GetMapping("/weather/weatherViewApi")
    public Weather weatherViewApi(){
        return weatherService.weatherLast();
    }

}
