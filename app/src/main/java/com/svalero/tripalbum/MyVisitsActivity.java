package com.svalero.tripalbum;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.svalero.tripalbum.database.AppDatabase;
import com.svalero.tripalbum.domain.Country;
import com.svalero.tripalbum.domain.Place;
import com.svalero.tripalbum.domain.Province;

import java.util.ArrayList;
import java.util.List;

public class MyVisitsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public List<Province> provinces;
    private ArrayAdapter<Province> provincesAdapter;
    public List<Country> countries;
    private ArrayAdapter<Country> countriesAdapter;
    public List<Place> places;
    private ArrayAdapter<Place> placesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_visits);

        places = new ArrayList<>();
        ListView lvPlaces = findViewById(R.id.places_list_visits);
        placesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, places);
        lvPlaces.setAdapter(placesAdapter);

        provinces = new ArrayList<>();
        ListView lvProvinces = findViewById(R.id.provinces_list_visits);
        provincesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, provinces);
        lvProvinces.setAdapter(provincesAdapter);
        lvProvinces.setOnItemClickListener(this);

        countries = new ArrayList<>();
        ListView lvCountries = findViewById(R.id.countries_list_visits);
        countriesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, countries);
        lvCountries.setAdapter(countriesAdapter);
        lvCountries.setOnItemClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCountries();
        provincesAdapter.notifyDataSetChanged();
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

    private void loadPlaces(int idProvince) {
        places.clear();
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "places").allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();
        places.addAll(db.placeDao().getPlacesByProvince(idProvince));
        placesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.countries_list_visits) {
            Country country = countries.get(position);
            places.clear();
            placesAdapter.notifyDataSetChanged();
            loadProvinces(country.getId());
        } else {
            Province province = provinces.get(position);
            loadPlaces(province.getId());
        }
    }
}