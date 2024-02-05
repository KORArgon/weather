package com.argon.weather.controller;

import com.argon.weather.domain.Weather;
import com.argon.weather.service.MessageService;
import com.argon.weather.service.WeatherService;
import com.argon.weather.util.PagingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
    public String weatherListForm(Model model, @PageableDefault(page=0, size=10, sort="weatherId", direction = Sort.Direction.DESC) Pageable pageable){
        Page<Weather> weatherList = weatherService.findAll(pageable);
        model.addAttribute("weatherList", weatherList);
        PagingUtil.getPaginationInfo(model, weatherList);

        return "weather/weatherList";
    }

    /**
     * 날씨 상세 조회
     * @param weather
     * @param model
     * @return
     */
    @GetMapping("/weather/weatherViewForm")
    public String weatherViewForm(Weather weather, Model model){
        Weather result = weatherService.findById(weather.getWeatherId());
        model.addAttribute("weather", result);
        return "weather/weatherView";
    }

    /**
     * 날씨 등록 페이지
     * @return
     */
    @GetMapping("/weather/weatherRegistForm")
    public String weatherRegistForm(){
        return "weather/weatherRegist";
    }

    /**
     * 날씨 등록 처리
     * @param weather
     * @param model
     * @return
     */
    @PostMapping("/weather/weatherRegist")
    public String weatherRegist(Weather weather, Model model){
        weatherService.save(weather);
        return messageService.redirectMessage(model, "등록을 완료했습니다.", "/weather/weatherListForm");
    }

    /**
     * 날씨 수정 페이지
     * @param weather
     * @param model
     * @return
     */
    @GetMapping("/weather/weatherUpdateForm")
    public String weatherUpdateForm(Weather weather, Model model){
        Weather result = weatherService.findById(weather.getWeatherId());
        model.addAttribute("weather", result);
        return "weather/weatherUpdate";
    }

    /**
     * 날씨 수정 처리
     * @param weather
     * @param model
     * @return
     */
    @PostMapping("/weather/weatherUpdate")
    public String weatherUpdate(Weather weather, Model model){
        weatherService.save(weather);
        return messageService.redirectMessage(model, "수정을 완료했습니다.", "/weather/weatherListForm");
    }

    /**
     * 날씨 삭제 처리
     * @param weather
     * @param model
     * @return
     */
    @GetMapping("/weather/weatherDelete")
    public String weatherDelete(Weather weather, Model model){
        weatherService.weatherDelete(weather);
        return messageService.redirectMessage(model, "삭제를 완료했습니다.", "/weather/weatherListForm");
    }

    /**
     * 날씨 Api 등록 처리
     * @param model
     * @return
     */
    @GetMapping("/weather/weatherRegistAuto")
    public String weatherRegistAuto(Model model) {
        weatherService.saveAuto();
        return messageService.redirectMessage(model, "등록을 완료했습니다.", "/weather/weatherListForm");
    }

}
