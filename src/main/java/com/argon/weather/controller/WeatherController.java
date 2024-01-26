package com.argon.weather.controller;

import com.argon.weather.domain.Weather;
import com.argon.weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class WeatherController {


    private final WeatherService memberService;

    @GetMapping("/weather/weatherListForm")
    public String weatherListForm(){
        return "weather/weatherList";
    }

    @GetMapping("/weather/weatherViewForm")
    public String weatherViewForm(Weather weather){
        return "weather/weatherList";
    }

    @GetMapping("/weather/weatherRegistForm")
    public String weatherRegistForm(){
        return "weather/weatherRegist";
    }

    @PostMapping("/weather/weatherRegist")
    public String weatherRegist(Weather weather){
        memberService.save(weather);
        return "weather/weatherRegist";
    }

    @GetMapping("/weather/weatherUpdateForm")
    public String weatherUpdateForm(Weather weather){
        return "weather/weatherUpdate";
    }

    @PostMapping("/weather/weatherUpdate")
    public String weatherUpdate(Weather weather){

        return "weather/weatherUpdate";
    }

    @PostMapping("/weather/weatherDelete")
    public String weatherDelete(Weather weather){

        return "weather/weatherList";
    }

}
