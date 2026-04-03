package com.nishioffduty.journalApp.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class WeatherResponse {

    @JsonProperty("current")
    private Current current;

    // -------- CURRENT --------
    @Getter
    @Setter
    public class Current {

        @JsonProperty("observation_time")
        private String observationTime;

        @JsonProperty("temperature")
        private int temperature;

        @JsonProperty("weather_descriptions")
        private List<String> weatherDescriptions;

        @JsonProperty("feelslike")
        private int feelsLike;

    }


}