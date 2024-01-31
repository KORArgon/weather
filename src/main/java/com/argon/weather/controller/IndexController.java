package com.argon.weather.controller;

import org.springframework.ui.Model;
import com.argon.weather.domain.Weather;
import com.argon.weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final WeatherService weatherService;

    @GetMapping("/")
    public String index(Model model){

        Weather weather = weatherService.selectWeatherLast();
        model.addAttribute("weather", weather);

        return "index";
    }

}
