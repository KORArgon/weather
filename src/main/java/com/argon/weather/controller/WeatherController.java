package com.argon.weather.controller;

import com.argon.weather.domain.Weather;
import com.argon.weather.service.MessageService;
import com.argon.weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.data.domain.Pageable;
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

    // weatherService
    private final WeatherService weatherService;

    // messageService
    private final MessageService messageService;

    /**
     * 날씨 목록 조회
     * @param model
     * @return
     */
    @GetMapping("/weather/weatherListForm")
    public String weatherListForm(Model model, Pageable pageable){
        List<Weather> weatherList = weatherService.findAll(pageable);
        model.addAttribute("weatherList", weatherList);
        model.addAttribute("pageable", pageable);
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

    @GetMapping("/weather/weatherDelete")
    public String weatherDelete(Model model, Weather weather){
        weatherService.weatherDelete(weather);
        return messageService.redirectMessage(model, "삭제를 완료했습니다.", "/weather/weatherListForm");
    }

    @GetMapping("/weather/weatherRegistAuto")
    public String weatherRegistAuto(Model model) throws IOException, ParseException {
        weatherService.saveAuto();
        return messageService.redirectMessage(model, "등록을 완료했습니다.", "/weather/weatherListForm");
    }

}
