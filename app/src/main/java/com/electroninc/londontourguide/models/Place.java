package com.electroninc.londontourguide.models;

import java.util.List;

public class Place {
    private String tag;
    private String name;
    private String info;
    private String maps;
    private String phone;
    private String website;
    private List<String> photos;

    public Place(String tag, String name, String info, String maps, String phone, String website, List<String> photos) {
        this.tag = tag;
        this.name = name;
        this.info = info;
        this.maps = maps;
        this.phone = phone;
        this.website = website;
        this.photos = photos;
    }

    public String getTag() {
        return tag;
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }

    public String getMaps() {
        return maps;
    }

    public String getPhone() {
        return phone;
    }

    public String getWebsite() {
        return website;
    }

    public List<String> getPhotos() {
        return photos;
    }
}
