package com.electroninc.londontourguide.lifecycle;

import com.electroninc.londontourguide.models.Place;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PlacesViewModel extends ViewModel {
    public MutableLiveData<List<Place>> places = new MutableLiveData<>((List<Place>) new ArrayList<Place>());
}
