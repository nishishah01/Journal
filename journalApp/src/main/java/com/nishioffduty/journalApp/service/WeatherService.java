package com.nishioffduty.journalApp.service;


import com.nishioffduty.journalApp.api.response.WeatherResponse;
import com.nishioffduty.journalApp.cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {
    @Value("${weather.api.key}")
    private String apiKey;

    private static final String API = "http://api.weatherstack.com/current?access_key=API_KEY&query=CITY";


    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private AppCache appCache;
    @Autowired
    private RedisService redisService;

    public WeatherResponse getWeather(String city){
        WeatherResponse weatherResponse= redisService.get("weather_of"+city,WeatherResponse.class);
        if(weatherResponse!=null){
            return weatherResponse;
        }
        else{
            String finalAPI=appCache.APP_CACHE.get("weather_api").replace("<city>",city).replace("<apiKey>",apiKey);
            ResponseEntity<WeatherResponse>response=restTemplate.exchange(finalAPI, HttpMethod.GET,null, WeatherResponse.class);
            WeatherResponse body= response.getBody();
            if(body!=null){
                redisService.set("weather_of"+city,body,300l);
            }
            return body;
        }


    }
}
