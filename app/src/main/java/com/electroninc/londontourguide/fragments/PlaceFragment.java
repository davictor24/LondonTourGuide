package com.electroninc.londontourguide.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.electroninc.londontourguide.R;
import com.electroninc.londontourguide.models.Place;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class PlaceFragment extends Fragment {
    private Place place;

    public PlaceFragment() {
        // Required empty public constructor
    }

    public PlaceFragment(Place place) {
        this.place = place;
    }

    public Place getPlace() {
        return place;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_place, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        TextView placeTextView = view.findViewById(R.id.place_text_view);
        placeTextView.setText(place.getInfo());
    }
}
