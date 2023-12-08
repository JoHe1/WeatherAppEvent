package org.ulpgc.dacd.control;

public class WeatherController {
    public final WeatherProvider weatherProvider;
    public final WeatherStore weatherStore;

    public WeatherController(WeatherProvider weatherProvider, WeatherStore weatherStore) {
        this.weatherProvider = weatherProvider;
        this.weatherStore = weatherStore;
    }
}
