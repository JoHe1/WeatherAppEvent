package org.ulpgc.dacd.model;

public class Location {
    private final String island;
    private final String latitude;
    private final String longitude;

    public Location(String island, String latitude, String longitude) {
        this.island = island;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getIsland() {
        return island;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

}
