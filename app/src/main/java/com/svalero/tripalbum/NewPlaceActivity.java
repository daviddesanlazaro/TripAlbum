package com.svalero.tripalbum;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.svalero.tripalbum.database.AppDatabase;
import com.svalero.tripalbum.domain.Country;
import com.svalero.tripalbum.domain.Place;
import com.svalero.tripalbum.domain.Province;

import java.util.ArrayList;
import java.util.List;

public class NewPlaceActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,
        OnMapReadyCallback, GoogleMap.OnMapClickListener {

    public List<Province> provinces;
    private ArrayAdapter<Province> provincesAdapter;
    public List<Country> countries;
    private ArrayAdapter<Country> countriesAdapter;
    private Button buttonProvince;
    private Button buttonCountry;
    Province province = new Province(0, null, 0);
    private float[] position = {0, 0};
    private GoogleMap map;
    private Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_place);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment!=null) {
            mapFragment.getMapAsync(this);
        }

        provinces = new ArrayList<>();
        ListView lvProvinces = findViewById(R.id.provinces_list);
        provincesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, provinces);
        lvProvinces.setAdapter(provincesAdapter);
        lvProvinces.setOnItemClickListener(this);

        countries = new ArrayList<>();
        ListView lvCountries = findViewById(R.id.countries_list_places);
        countriesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, countries);
        lvCountries.setAdapter(countriesAdapter);
        lvCountries.setOnItemClickListener(this);

        buttonProvince = findViewById(R.id.new_province);
        buttonProvince.setOnClickListener(v -> openNewProvince());

        buttonCountry = findViewById(R.id.new_country_place);
        buttonCountry.setOnClickListener(v -> openNewCountry());
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCountries();
    }

    private void loadCountries() {
        countries.clear();
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "countries").allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();
        countries.addAll(db.countryDao().getAll());
        countriesAdapter.notifyDataSetChanged();
    }

    private void loadProvinces(int idCountry) {
        provinces.clear();
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "provinces").allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();
        provinces.addAll(db.provinceDao().getProvincesByCountry(idCountry));
        provincesAdapter.notifyDataSetChanged();
    }

    public void addPlace(View view) {
        EditText etName = findViewById(R.id.place_name);
        EditText etDesc = findViewById(R.id.place_desc);

        String name = etName.getText().toString();
        String desc = etDesc.getText().toString();

        if ((province.getId() == 0) || (name.equals("")) || (desc.equals(""))) {
            Toast.makeText(this, getString(R.string.add_missing_data), Toast.LENGTH_SHORT).show();
        } else {
            Place place = new Place(0, name, desc, position[0], position[1], province.getId());

            AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "places").allowMainThreadQueries().build();
            db.placeDao().insert(place);

            Toast.makeText(this, getString(R.string.place_added), Toast.LENGTH_SHORT).show();
            etName.setText("");
            etDesc.setText("");
        }
    }

    public void openNewProvince() {
        Intent intent = new Intent(this, NewProvinceActivity.class);
        startActivity(intent);
    }

    public void openNewCountry() {
        Intent intent = new Intent(this, NewCountryActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.countries_list_places) {
            Country country = countries.get(position);
            province.setId(0);
            loadProvinces(country.getId());
        } else {
            province = provinces.get(position);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        googleMap.setOnMapClickListener(this);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        if (marker != null) {
            marker.remove();
        }
        marker = map.addMarker(new MarkerOptions().position(latLng));
        position[0] = (float) latLng.latitude;
        position[1] = (float) latLng.longitude;
        Toast.makeText(this, latLng.latitude + ", " + latLng.longitude, Toast.LENGTH_SHORT).show();
    }
}