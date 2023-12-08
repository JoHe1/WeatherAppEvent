package org.ulpgc.dacd.control;

import org.ulpgc.dacd.model.Location;
import org.ulpgc.dacd.model.Weather;

import java.util.List;

public class OpenWeatherMapProvider implements WeatherProvider{
    public final String apikey;
    public OpenWeatherMapProvider(String apikey) {
        this.apikey = apikey;
    }


    @Override
    public List<Weather> get(Location location) {
        return null;
    }
}
