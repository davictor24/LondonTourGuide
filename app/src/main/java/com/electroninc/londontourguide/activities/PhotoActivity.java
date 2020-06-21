package com.electroninc.londontourguide.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.electroninc.londontourguide.R;
import com.electroninc.londontourguide.utils.Utils;

public class PhotoActivity extends AppCompatActivity {

    public static final String PLACE_NAME = "place_name";
    public static final String PHOTO_RESOURCE = "photo_resource";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        ImageView photoImageView = findViewById(R.id.photo_image_view);
        Intent intent = getIntent();
        String placeName = intent.getStringExtra(PLACE_NAME);
        String photoResource = intent.getStringExtra(PHOTO_RESOURCE);
        photoImageView.setImageResource(Utils.getResourceFromDrawable(this, photoResource));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setTitle(placeName);
    }
}
