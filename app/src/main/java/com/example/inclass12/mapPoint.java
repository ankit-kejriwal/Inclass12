package com.example.inclass12;

public class mapPoint {
    String lat,longi;

    public mapPoint() {
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLongi() {
        return longi;
    }

    public void setLongi(String longi) {
        this.longi = longi;
    }

    @Override
    public String toString() {
        return "mapPoint{" +
                "lat=" + lat +
                ", longi=" + longi +
                '}';
    }
}
