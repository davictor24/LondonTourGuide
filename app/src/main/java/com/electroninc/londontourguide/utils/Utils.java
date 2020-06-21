package com.electroninc.londontourguide.utils;

import android.content.Context;

import com.electroninc.londontourguide.models.Place;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class Utils {
    private Utils() {
        // Private constructor, to prevent instantiation
    }

    public static List<Place> loadPlaces(Context context) {
        try {
            // Load JSON from assets folder
            // https://stackoverflow.com/a/19945484
            String json = null;
            InputStream is = context.getAssets().open("data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            return loadPlacesFromJson(json);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private static List<Place> loadPlacesFromJson(String json) throws JSONException {
        List<Place> places = new ArrayList<>();
        JSONArray placesJson = new JSONObject(json).getJSONArray("places");
        for (int i = 0; i < placesJson.length(); i++) {
            JSONObject placeJson = placesJson.getJSONObject(i);
            String tag = UUID.randomUUID().toString().replace("-", "");
            String name = placeJson.getString("name");
            String info = placeJson.getString("info");
            String maps = placeJson.getString("maps");
            String phone = placeJson.getString("phone");
            String website = placeJson.getString("website");
            List<String> photos = new ArrayList<>();
            JSONArray photosJson = placeJson.getJSONArray("photos");
            for (int j = 0; j < photosJson.length(); j++)
                photos.add(photosJson.getString(j));
            places.add(new Place(
                    tag,
                    name,
                    info,
                    maps,
                    phone,
                    website,
                    photos
            ));
        }
        return places;
    }
}