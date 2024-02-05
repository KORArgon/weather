package com.argon.weather.repository;

import com.argon.weather.domain.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WeatherRepository extends JpaRepository<Weather, Long> {

    @Query("SELECT m FROM Weather m ORDER BY m.weatherId DESC LIMIT 1")
    Weather weatherLast();

}
