package com.argon.weather.service;

import com.argon.weather.domain.Weather;
import com.argon.weather.repository.WeatherRepository;
import com.argon.weather.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.springframework.data.domain.Page;

@Service
@Slf4j
public class WeatherService {

    // weatherService
    private final WeatherRepository weatherRepository;

    // weatherService
    public WeatherService(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }
    
    // API KEY 값, application-local.properties 에서 설정
    @Value("${weather.api.key}")
    private String API_KEY;

    /**
     * 전체 조회
     * @param pageable
     * @return
     */
    public Page<Weather> findAll(Pageable pageable) {
        return weatherRepository.findAll(pageable);
    }

    /**
     * 상세 조회
     * @param id
     * @return
     */
    public Weather findById(Long id){
        return weatherRepository.findById(id).orElseThrow();
    }

    /**
     * 등록 및 수정 처리
     * @param weather
     */
    public void save(Weather weather) {
        weatherRepository.save(weather);
    }

    /**
     * 삭제 처리
     * @param weather
     */
    public void weatherDelete(Weather weather) {
        weatherRepository.delete(weather);
    }

    /**
     * 마지막 데이터 조회
     * @return
     */
    public Weather weatherLast() {
        return weatherRepository.weatherLast();
    }

    /**
     * API 등록
     */
    public void saveAuto() {

        String base_date = DateUtil.getSimpleDateUtil("yyyyMMdd");
        String base_time = getWeatherTimeFormat(DateUtil.getSimpleDateUtil("HHmm"));

        String nx = "69";
        String ny = "107";

        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst?pageNo=1&numOfRows=12&dataType=JSON&ftype=SHRT"); /*URL*/
        URL url = null;
        try {
            urlBuilder.append("&" + URLEncoder.encode("serviceKey","UTF-8") + "=" + URLEncoder.encode(API_KEY, "UTF-8")); /*공공데이터포털에서 받은 인증키*/
            urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(base_date, "UTF-8")); /*15년 12월 1일 발표*/
            urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode(base_time, "UTF-8")); /*06시 발표(정시단위)*/
            urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode(nx, "UTF-8")); /*예보지점의 X 좌표값*/
            urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode(ny, "UTF-8")); /*예보지점 Y 좌표*/
        } catch (UnsupportedEncodingException e) {
            log.info("ERROR : " + e);
        }

        try {
            url = new URL(urlBuilder.toString());
        } catch (MalformedURLException e) {
            log.info("ERROR : " + e);
        }

        InputStreamReader isr = null;
        JSONParser parser = new JSONParser();
        JSONObject json = null;

        try {
            isr = new InputStreamReader(url.openConnection().getInputStream(), "UTF-8");
            json = (JSONObject) parser.parse(isr);
        } catch (IOException | ParseException e) {
            log.info("ERROR : " + e);
        }

        JSONObject response = (JSONObject) json.get("response");
        JSONObject body = (JSONObject) response.get("body");
        JSONObject items = (JSONObject) body.get("items");
        JSONArray itemArray = (JSONArray) items.get("item");

        Weather weather = new Weather();
        weather.setBaseDate(base_date);
        weather.setBaseTime(base_time);
        for (Object item : itemArray) {
            JSONObject jsonItem = (JSONObject) item;
            switch ((String) jsonItem.get("category")){
                case "PTY":
                    weather.setPty((String) jsonItem.get("obsrValue"));
                    break;
                case "REH":
                    weather.setReh((String) jsonItem.get("obsrValue"));
                    break;
                case "RN1":
                    weather.setRn1((String) jsonItem.get("obsrValue"));
                    break;
                case "T1H":
                    weather.setT1h((String) jsonItem.get("obsrValue"));
                    break;
                case "UUU":
                    weather.setUuu((String) jsonItem.get("obsrValue"));
                    break;
                case "VEC":
                    weather.setVec((String) jsonItem.get("obsrValue"));
                    break;
                case "VVV":
                    weather.setVvv((String) jsonItem.get("obsrValue"));
                    break;
                case "WSD":
                    weather.setWsd((String) jsonItem.get("obsrValue"));
                    break;
                default:
                    break;
            }
        }
        weatherRepository.save(weather);
    }
    
    public String getWeatherTimeFormat(String time){
        if(Integer.parseInt(time) > 0 && Integer.parseInt(time) < 200) return "2300";
        else if(Integer.parseInt(time) > 200 && Integer.parseInt(time) < 400) return "0100";
        else if(Integer.parseInt(time) > 400 && Integer.parseInt(time) < 600) return "0300";
        else if(Integer.parseInt(time) > 600 && Integer.parseInt(time) < 800) return "0500";
        else if(Integer.parseInt(time) > 800 && Integer.parseInt(time) < 1000) return "0700";
        else if(Integer.parseInt(time) > 1000 && Integer.parseInt(time) < 1200) return "0900";
        else if(Integer.parseInt(time) > 1200 && Integer.parseInt(time) < 1400) return "1100";
        else if(Integer.parseInt(time) > 1400 && Integer.parseInt(time) < 1600) return "1300";
        else if(Integer.parseInt(time) > 1600 && Integer.parseInt(time) < 1800) return "1500";
        else if(Integer.parseInt(time) > 1800 && Integer.parseInt(time) < 2000) return "1700";
        else if(Integer.parseInt(time) > 2000 && Integer.parseInt(time) < 2200) return "1900";
        else if(Integer.parseInt(time) > 2200 && Integer.parseInt(time) < 2400) return "2100";
        else return "0000";
    }
}
