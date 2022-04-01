package com.svalero.tripalbum.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.svalero.tripalbum.R;
import com.svalero.tripalbum.contract.ViewPlaceContract;
import com.svalero.tripalbum.domain.Favorite;
import com.svalero.tripalbum.domain.Place;
import com.svalero.tripalbum.presenter.ViewPlacePresenter;

public class ViewPlaceView extends AppCompatActivity implements ViewPlaceContract.View, OnMapReadyCallback,
        GoogleMap.OnMapClickListener {

    private ViewPlacePresenter presenter;

    private Place place;

    private final int USER_ID = 65; // Solución hasta hacer control de sesión

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_place);
        presenter = new ViewPlacePresenter(this);

        Intent intent = getIntent();
        place = (Place) intent.getSerializableExtra("place");

        initializeView();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.view_place_map);
        if (mapFragment!=null) {
            mapFragment.getMapAsync(this);
        }
    }

    private void initializeView() {
        TextView name = findViewById(R.id.view_place_name);
        TextView description = findViewById(R.id.view_place_description);
        name.setText(place.getName());
        description.setText(place.getDescription());
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

    public void openNewVisit(View view) {
        presenter.openNewVisit(place);
    }

    public void addFavorite(View view) {
        Favorite favorite = new Favorite(0, USER_ID, place.getId());
        presenter.addFavorite(favorite);
    }

    public void removeInteresting(View view) {

    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}