package com.svalero.tripalbum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.svalero.tripalbum.domain.Place;

public class ViewPlaceActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    GoogleMap map;
    Place place = new Place(0, null, null, 0, 0, 0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_place);
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
        map = googleMap;
        googleMap.setOnMapClickListener(this);
        String title = place.getName();
        String description = place.getDescription();
        double lat = (double) place.getLatitude();
        double longitude = (double) place.getLongitude();
        map.addMarker(new MarkerOptions()
                .position(new LatLng(lat, longitude))
                .snippet(description)
                .title(title));
    }
}