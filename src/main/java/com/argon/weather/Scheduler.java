package com.argon.weather;

import com.argon.weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@EnableScheduling
@RequiredArgsConstructor
public class Scheduler {

    // weatherService
    private final WeatherService weatherService;


//    @Scheduled(fixedRate = 10000)
    @Scheduled(cron = "0 0 * * * *")
    public void schedulerWeatherInsert() throws IOException, ParseException {
        log.info("스케줄러동작");
        weatherService.saveAuto();
        log.info("스케줄러성공");
    }

}
