package com.electroninc.londontourguide.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.electroninc.londontourguide.R;
import com.electroninc.londontourguide.fragments.PlaceFragment;
import com.electroninc.londontourguide.lifecycle.PlacesViewModel;
import com.electroninc.londontourguide.loaders.PlacesLoader;
import com.electroninc.londontourguide.models.Place;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        LoaderManager.LoaderCallbacks<List<Place>> {

    private static final int LOADER_ID = 1;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private PlacesViewModel placesViewModel;
    private FragmentManager placesFragmentManager;
    private List<PlaceFragment> placeFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        placesViewModel = new ViewModelProvider(this).get(PlacesViewModel.class);
        placesViewModel.places.observe(this, new Observer<List<Place>>() {
            @Override
            public void onChanged(List<Place> places) {
                if (placesViewModel.hasLoaded) updateView(places);
            }
        });

        placesFragmentManager = getSupportFragmentManager();

        if (!placesViewModel.hasLoaded)
            LoaderManager.getInstance(this).initLoader(LOADER_ID, null, this);
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
        switchFragment(id);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @NonNull
    @Override
    public Loader<List<Place>> onCreateLoader(int id, @Nullable Bundle args) {
        return new PlacesLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Place>> loader, List<Place> data) {
        Toast.makeText(this, "Loaded " + data.size() + " items!", Toast.LENGTH_SHORT).show();
        placesViewModel.places.postValue(data);
        placesViewModel.hasLoaded = true;
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Place>> loader) {
    }

    private void updateView(List<Place> places) {
        placeFragments = new ArrayList<>();
        Menu drawerMenu = navigationView.getMenu();
        for (int i = 0; i < places.size(); i++) {
            Place place = places.get(i);
            drawerMenu.add(0, i, 0, place.getName());
            placeFragments.add(PlaceFragment.newInstance(i));
        }

        drawerMenu.setGroupCheckable(0, true, true);
        drawerMenu.getItem(0).setChecked(true);
        navigationView.invalidate();
        switchFragment(0);
    }

    private void switchFragment(int i) {
        PlaceFragment placeFragment = placeFragments.get(i);
        Place place = placesViewModel.places.getValue().get(placeFragment.getPlaceIndex());
        placesFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment,
                        placeFragment,
                        place.getTag())
                .commit();
        toolbar.setTitle(place.getName());
    }

}
