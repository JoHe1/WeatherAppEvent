package org.ulpgc.dacd.control;

import org.ulpgc.dacd.model.Weather;

import java.sql.Connection;

public class JmsWeatherStore implements WeatherStore{
    public final String url = "tcp://localhost:61616";
    public final String subject = "prediction.Weather";


    @Override
    public Connection connection() {
        return null;
    }

    @Override
    public void close(Connection connection) {

    }

    @Override
    public void save(Weather weather) {

    }
}
