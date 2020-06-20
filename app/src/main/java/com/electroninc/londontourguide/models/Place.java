package com.electroninc.londontourguide.models;

public class Place {
    private String title;
    private String info;

    public Place(String title, String info) {
        this.title = title;
        this.info = info;
    }

    public String getTitle() {
        return title;
    }

    public String getInfo() {
        return info;
    }
}
