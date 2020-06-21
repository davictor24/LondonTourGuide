package com.electroninc.londontourguide.lifecycle;

import com.electroninc.londontourguide.models.Place;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.ViewModel;

public class PlacesViewModel extends ViewModel {
    public boolean hasLoaded = false;
    public List<Place> places = new ArrayList<>();
    public int currentFragment = 0;
}
