package com.svalero.tripalbum.view;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.TravelMode;
import com.svalero.tripalbum.R;
import com.svalero.tripalbum.domain.Place;
import com.svalero.tripalbum.util.DirectionUtils;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class PlaceMapView extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, LocationListener {

    private Place place;
    private GoogleMap map;
    private Marker marker;
    private LatLng userLocation;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_map);

        Intent intent = getIntent();
        place = (Place) intent.getSerializableExtra("place");
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment!=null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setOnMapClickListener(this);
        String title = place.getName();
        String description = place.getDescription();
        double lat = place.getLatitude();
        double longitude = place.getLongitude();
        marker = map.addMarker(new MarkerOptions()
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
        Location location = getLastKnownLocation();
        userLocation = new LatLng(location.getLatitude(), location.getLongitude());
        googleMap.setMyLocationEnabled(true);
    }

    @Override
    public void onMapClick(LatLng latLng) {
    }

    public void showRoute(View view) {
        if (userLocation == null)
            Toast.makeText(this, "Espera", Toast.LENGTH_SHORT).show();
        else {
            DirectionsApiRequest request = DirectionsApi.newRequest(getGeoContext())
                    .mode(TravelMode.DRIVING)
                    .origin(DirectionUtils.fromMapsToDirectionsApi(userLocation))
                    .destination(DirectionUtils.fromMapsToDirectionsApi(marker.getPosition()))
                    .departureTime(DateTime.now())
                    .alternatives(true);

            RouteTask routeTask = new RouteTask();
            routeTask.execute(request);
            DirectionsResult result = null;
            try {
                result = routeTask.get();
                if (result.routes.length == 0) {
                    Toast.makeText(this, "No hay rutas entre los 2 puntos", Toast.LENGTH_SHORT).show();
                    return;
                }
                paintRoute(result, 0);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private GeoApiContext getGeoContext() {
        GeoApiContext geoApiContext = new GeoApiContext();
        return geoApiContext.setQueryRateLimit(3)
                .setApiKey(getString(R.string.google_maps_key))
                .setConnectTimeout(1, TimeUnit.SECONDS)
                .setReadTimeout(1, TimeUnit.SECONDS)
                .setWriteTimeout(1, TimeUnit.SECONDS);
    }

    private void paintRoute(DirectionsResult result, int position) {
        List<com.google.maps.model.LatLng> routePath = result.routes[position].overviewPolyline.decodePath();
        map.addPolyline(new PolylineOptions()
                .add(DirectionUtils.fromMapsToDirections(routePath))
                .color(Color.RED));
        Log.d("sanvalero", routePath.toString());
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        userLocation = new LatLng(location.getLatitude(), location.getLongitude());
    }

    private class RouteTask extends AsyncTask<DirectionsApiRequest, Void, DirectionsResult> {

        @Override
        protected DirectionsResult doInBackground(DirectionsApiRequest... requests) {
            try {
                return requests[0].await();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            } catch (ApiException apie) {
                apie.printStackTrace();
            }
            return null;
        }
    }

    private Location getLastKnownLocation() {
        locationManager = (LocationManager)getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        if (ActivityCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        for (String provider : providers) {
            Location l = locationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                bestLocation = l;
            }
        }
        return bestLocation;
    }
}