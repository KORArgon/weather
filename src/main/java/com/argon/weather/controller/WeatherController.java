package com.argon.weather.controller;

import com.argon.weather.domain.Weather;
import com.argon.weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class WeatherController {


    private final WeatherService weatherService;

    @GetMapping("/weather/weatherListForm")
    public String weatherListForm(Model model){
        List<Weather> weatherList = weatherService.findAll();
        model.addAttribute("weatherList", weatherList);
        return "weather/weatherList";
    }

    @GetMapping("/weather/weatherViewForm")
    public String weatherViewForm(Weather weather, Model model){
        Optional<Weather> result = weatherService.findById(weather.getWeatherId());
        model.addAttribute("result", result);
        return "weather/weatherView";
    }

    @GetMapping("/weather/weatherRegistForm")
    public String weatherRegistForm(){
        return "weather/weatherRegist";
    }

    @PostMapping("/weather/weatherRegist")
    public String weatherRegist(Weather weather){
        weatherService.save(weather);
        return "weather/weatherRegist";
    }

    @GetMapping("/weather/weatherUpdateForm")
    public String weatherUpdateForm(Weather weather, Model model){
        Optional<Weather> result = weatherService.findById(weather.getWeatherId());
        model.addAttribute("result", result);
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

    @GetMapping("/weather/weatherRegistAuto")
    public String weatherRegistAuto() throws IOException, ParseException {
        weatherService.saveAuto();
        return "weather/weatherRegist";
    }

}
