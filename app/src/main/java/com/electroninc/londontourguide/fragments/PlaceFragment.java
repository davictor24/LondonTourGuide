package com.electroninc.londontourguide.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.electroninc.londontourguide.R;
import com.electroninc.londontourguide.lifecycle.PlacesViewModel;
import com.electroninc.londontourguide.models.Place;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class PlaceFragment extends Fragment {
    private static final String PLACE_INDEX = "place_index";

    public PlaceFragment() {
        // Required empty public constructor
    }

    public static PlaceFragment newInstance(int placeIndex) {
        PlaceFragment fragment = new PlaceFragment();
        Bundle args = new Bundle();
        args.putInt(PLACE_INDEX, placeIndex);
        fragment.setArguments(args);
        return fragment;
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

        int placeIndex = getArguments().getInt(PLACE_INDEX);
        Place place = new ViewModelProvider(getActivity())
                .get(PlacesViewModel.class)
                .places.getValue()
                .get(placeIndex);

        placeTextView.setText(place.getInfo());
    }

    public int getPlaceIndex() {
        return getArguments().getInt(PLACE_INDEX);
    }
}
