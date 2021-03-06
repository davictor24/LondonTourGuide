package com.electroninc.londontourguide.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.electroninc.londontourguide.R;
import com.electroninc.londontourguide.activities.PhotoActivity;
import com.electroninc.londontourguide.adapters.PhotosAdapter;
import com.electroninc.londontourguide.lifecycle.PlacesViewModel;
import com.electroninc.londontourguide.models.Place;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class PlaceFragment extends Fragment implements PhotosAdapter.ItemClickListener {
    private static final String PLACE_INDEX = "place_index";
    private static final int CALL_PERMISSION_REQUEST_ID = 1;
    private static final String CALL_PERMISSION_NUMBER = "call_permission_number";
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
        final String placeMaps = place.getMaps();
        final String placePhone = place.getPhone();
        final String placeWebsite = place.getWebsite();
        List<String> photos = place.getPhotos();

        if (shouldShow(placeInfoTextView, placeInfo)) {
            placeInfoTextView.setText(placeInfo);
        }

        if (shouldShow(placeMapsTextView, placeMaps)) {
            placeMapsTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openUrl(placeMaps);
                }
            });
        }

        if (shouldShow(placePhoneTextView, placePhone)) {
            placePhoneTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialNumber(placePhone);
                }
            });
        }

        if (shouldShow(placeWebsiteTextView, placeWebsite)) {
            placeWebsiteTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openUrl(placeWebsite);
                }
            });
        }

        int spanCount;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            spanCount = 2;
        else spanCount = 3;

        photosRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(spanCount,
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
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == CALL_PERMISSION_REQUEST_ID) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                String number = getArguments().getString(CALL_PERMISSION_NUMBER);
                Uri uri = Uri.parse("tel:" + number);
                intent.setData(uri);
                startActivity(intent);
            } else showPermissionDeniedToast();
        }
    }

    @Override
    public void onItemClicked(ImageView imageView, int itemId) {
        Intent viewPhotoIntent = new Intent(getActivity(), PhotoActivity.class);
        viewPhotoIntent.putExtra(PhotoActivity.PLACE_NAME, place.getName());
        viewPhotoIntent.putExtra(PhotoActivity.PHOTO_RESOURCE, place.getPhotos().get(itemId));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageView.setTransitionName(PhotoActivity.PHOTO_TRANSITION_NAME);
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    getActivity(),
                    imageView,
                    imageView.getTransitionName()
            );
            startActivity(viewPhotoIntent, optionsCompat.toBundle());
        } else {
            startActivity(viewPhotoIntent);
        }
    }

    private boolean shouldShow(View view, String text) {
        boolean shouldHide = text == null || text.isEmpty();
        if (shouldHide) view.setVisibility(View.GONE);
        return !shouldHide;
    }

    private void dialNumber(String number) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + number));
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            Bundle args = new Bundle();
            args.putString(CALL_PERMISSION_NUMBER, number);
            this.setArguments(args);
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE},
                    CALL_PERMISSION_REQUEST_ID);
        } else {
            try {
                startActivity(intent);
            } catch (SecurityException e) {
                showPermissionDeniedToast();
                e.printStackTrace();
            }
        }
    }

    private void openUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    private void showPermissionDeniedToast() {
        Toast.makeText(getContext(), "Permission denied!", Toast.LENGTH_SHORT).show();
    }

    public int getPlaceIndex() {
        return getArguments().getInt(PLACE_INDEX);
    }
}
