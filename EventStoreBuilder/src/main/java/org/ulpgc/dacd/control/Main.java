package org.ulpgc.dacd.control;

public class Main {
    public static void main(String[] args) {
        WeatherCollect weatherCollect = new WeatherCollect(args[0]);
        weatherCollect.createDirectory();
        weatherCollect.execute();
    }
}
