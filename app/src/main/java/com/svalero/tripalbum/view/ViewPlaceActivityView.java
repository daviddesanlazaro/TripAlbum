package com.svalero.tripalbum.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.svalero.tripalbum.R;
import com.svalero.tripalbum.contract.ViewPlaceContract;
import com.svalero.tripalbum.domain.Place;
import com.svalero.tripalbum.presenter.ViewPlacePresenter;

public class ViewPlaceActivityView extends AppCompatActivity implements ViewPlaceContract.View, OnMapReadyCallback,
        GoogleMap.OnMapClickListener {

    private Place place = new Place (0, null, null, 0, 0, 0);
    private ViewPlacePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_place);
        presenter = new ViewPlacePresenter(this);

        Intent intent = getIntent();
        place = (Place) intent.getSerializableExtra("place");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.view_place_map);
        if (mapFragment!=null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setOnMapClickListener(this);
        String title = place.getName();
        String description = place.getDescription();
        double lat = place.getLatitude();
        double longitude = place.getLongitude();
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(lat, longitude))
                .snippet(description)
                .title(title));

        if (ActivityCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }
        googleMap.setMyLocationEnabled(true);
    }
}