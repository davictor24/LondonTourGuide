package com.electroninc.londontourguide.activities;

import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.electroninc.londontourguide.R;
import com.electroninc.londontourguide.fragments.PlaceFragment;
import com.electroninc.londontourguide.models.Place;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String FRAGMENT_PLACE_A = "place_a";
    private static final String FRAGMENT_PLACE_B = "place_b";
    private static final String FRAGMENT_PLACE_C = "place_c";

    private FragmentManager placesFragmentManager;
    private List<PlaceFragment> placeFragments;
    private ArrayList<String> tags;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        setupFragments();
    }

    private void setupFragments() {
        placesFragmentManager = getSupportFragmentManager();

        placeFragments = new ArrayList<>();
        placeFragments.add(new PlaceFragment(new Place("Place A", "This is Place A")));
        placeFragments.add(new PlaceFragment(new Place("Place B", "This is Place B")));
        placeFragments.add(new PlaceFragment(new Place("Place C", "This is Place C")));

        tags = new ArrayList<>();
        tags.add(FRAGMENT_PLACE_A);
        tags.add(FRAGMENT_PLACE_B);
        tags.add(FRAGMENT_PLACE_C);

        switchFragment(0);
        toolbar.setTitle(placeFragments.get(0).getPlace().getTitle());
    }

    private void switchFragment(int i) {
        placesFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, placeFragments.get(i), tags.get(i))
                .commit();
        toolbar.setTitle(placeFragments.get(i).getPlace().getTitle());
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        int fragmentInd = 0;
        if (id == R.id.place_b) fragmentInd = 1;
        else if (id == R.id.place_c) fragmentInd = 2;

        switchFragment(fragmentInd);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}
