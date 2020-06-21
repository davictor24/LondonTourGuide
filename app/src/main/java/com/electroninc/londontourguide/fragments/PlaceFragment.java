package com.electroninc.londontourguide.fragments;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.electroninc.londontourguide.R;
import com.electroninc.londontourguide.activities.PhotoActivity;
import com.electroninc.londontourguide.adapters.PhotosAdapter;
import com.electroninc.londontourguide.lifecycle.PlacesViewModel;
import com.electroninc.londontourguide.models.Place;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class PlaceFragment extends Fragment implements PhotosAdapter.ItemClickListener {
    private static final String PLACE_INDEX = "place_index";
    private Place place;

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
        TextView placeInfoTextView = view.findViewById(R.id.place_info);
        TextView placeMapsTextView = view.findViewById(R.id.place_maps);
        TextView placePhoneTextView = view.findViewById(R.id.place_phone);
        TextView placeWebsiteTextView = view.findViewById(R.id.place_website);
        RecyclerView photosRecyclerView = view.findViewById(R.id.place_photos);

        place = new ViewModelProvider(getActivity())
                .get(PlacesViewModel.class)
                .places.get(getPlaceIndex());
        String placeInfo = place.getInfo();
        String placeMaps = place.getMaps();
        String placePhone = place.getPhone();
        String placeWebsite = place.getWebsite();
        List<String> photos = place.getPhotos();

        populateTextView(placeInfoTextView, placeInfo);
        populateTextView(placeMapsTextView, placeMaps);
        populateTextView(placePhoneTextView, placePhone);
        populateTextView(placeWebsiteTextView, placeWebsite);

        photosRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        PhotosAdapter adapter = new PhotosAdapter(getActivity(), photos, this);
        photosRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClicked(int itemId) {
        Intent viewPhotoIntent = new Intent(getActivity(), PhotoActivity.class);
        viewPhotoIntent.putExtra(PhotoActivity.PLACE_NAME, place.getName());
        viewPhotoIntent.putExtra(PhotoActivity.PHOTO_RESOURCE, place.getPhotos().get(itemId));
        startActivity(viewPhotoIntent);
    }

    private void populateTextView(TextView textView, String text) {
        if (text == null || text.isEmpty()) textView.setVisibility(View.GONE);
        else textView.setText(text);
    }

    public int getPlaceIndex() {
        return getArguments().getInt(PLACE_INDEX);
    }
}
