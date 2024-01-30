package com.argon.weather.service;

import com.argon.weather.domain.Weather;
import com.argon.weather.repository.WeatherRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    public void saveAuto() throws IOException, ParseException {

        SimpleDateFormat format_date = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat format_time = new SimpleDateFormat("HHmm");
        Date date = new Date();

        String base_date = format_date.format(date);
        String base_time = format_time.format(date);

        if(Integer.parseInt(base_time) > 0 && Integer.parseInt(base_time) < 200){
            base_time = "2300";
        }
        else if(Integer.parseInt(base_time) > 200 && Integer.parseInt(base_time) < 400){
            base_time = "0100";
        }
        else if(Integer.parseInt(base_time) > 400 && Integer.parseInt(base_time) < 600){
            base_time = "0300";
        }
        else if(Integer.parseInt(base_time) > 600 && Integer.parseInt(base_time) < 800){
            base_time = "0500";
        }
        else if(Integer.parseInt(base_time) > 800 && Integer.parseInt(base_time) < 1000){
            base_time = "0700";
        }
        else if(Integer.parseInt(base_time) > 1000 && Integer.parseInt(base_time) < 1200){
            base_time = "0900";
        }
        else if(Integer.parseInt(base_time) > 1200 && Integer.parseInt(base_time) < 1400){
            base_time = "1100";
        }
        else if(Integer.parseInt(base_time) > 1400 && Integer.parseInt(base_time) < 1600){
            base_time = "1300";
        }
        else if(Integer.parseInt(base_time) > 1600 && Integer.parseInt(base_time) < 1800){
            base_time = "1500";
        }
        else if(Integer.parseInt(base_time) > 1800 && Integer.parseInt(base_time) < 2000){
            base_time = "1700";
        }
        else if(Integer.parseInt(base_time) > 2000 && Integer.parseInt(base_time) < 2200){
            base_time = "1900";
        }
        else if(Integer.parseInt(base_time) > 2200 && Integer.parseInt(base_time) < 2400){
            base_time = "2100";
        }

        String nx = "69";
        String ny = "107";

        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*공공데이터포털에서 받은 인증키*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("12", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식(XML/JSON)Default: XML*/
        urlBuilder.append("&" + URLEncoder.encode("ftype","UTF-8") + "=" + URLEncoder.encode("SHRT", "UTF-8")); /*15년 12월 1일 발표*/
        urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(base_date, "UTF-8")); /*15년 12월 1일 발표*/
        urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode(base_time, "UTF-8")); /*06시 발표(정시단위)*/
        urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode(nx, "UTF-8")); /*예보지점의 X 좌표값*/
        urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode(ny, "UTF-8")); /*예보지점 Y 좌표*/
        URL url = new URL(urlBuilder.toString());

        InputStreamReader isr = new InputStreamReader(url.openConnection().getInputStream(), "UTF-8");
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(isr);

        JSONObject response = (JSONObject) json.get("response");
        JSONObject body = (JSONObject) response.get("body");
        JSONObject items = (JSONObject) body.get("items");
        JSONArray itemArray = (JSONArray) items.get("item");
        Weather weather = new Weather();

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
}
