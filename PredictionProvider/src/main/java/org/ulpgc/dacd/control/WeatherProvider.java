package org.ulpgc.dacd.control;

import org.ulpgc.dacd.model.Location;
import org.ulpgc.dacd.model.Weather;

import java.util.List;

public interface WeatherProvider {
    List<Weather> get(Location location);
}
