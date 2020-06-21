package com.electroninc.londontourguide.loaders;

import android.content.Context;

import com.electroninc.londontourguide.models.Place;
import com.electroninc.londontourguide.utils.Utils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.loader.content.AsyncTaskLoader;

public class PlacesLoader extends AsyncTaskLoader<List<Place>> {
    public PlacesLoader(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Place> loadInBackground() {
        // Later, this could be implemented to load data from the Internet
        return Utils.loadPlaces(getContext());
    }
}
