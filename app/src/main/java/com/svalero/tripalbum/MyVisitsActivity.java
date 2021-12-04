package com.svalero.tripalbum;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.svalero.tripalbum.database.AppDatabase;
import com.svalero.tripalbum.domain.Country;
import com.svalero.tripalbum.domain.Place;
import com.svalero.tripalbum.domain.Province;
import com.svalero.tripalbum.domain.Visit;

import java.util.ArrayList;
import java.util.List;

public class MyVisitsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public List<Province> provinces;
    private ArrayAdapter<Province> provincesAdapter;
    public List<Country> countries;
    private ArrayAdapter<Country> countriesAdapter;
    public List<Place> places;
    private ArrayAdapter<Place> placesAdapter;
    public ArrayList<Visit> visits;
    private VisitAdapter visitsAdapter;
    private Place place = new Place(0, null, null, 0, 0, 0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_visits);

        visits = new ArrayList<>();
        ListView gvVisits = (ListView) findViewById(R.id.visit_list);
        visitsAdapter = new VisitAdapter(this, visits);
        gvVisits.setAdapter(visitsAdapter);
        gvVisits.setOnItemClickListener(this);

        places = new ArrayList<>();
        ListView lvPlaces = findViewById(R.id.places_list_visits);
        placesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, places);
        lvPlaces.setAdapter(placesAdapter);
        lvPlaces.setOnItemClickListener(this);

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
        loadVisits(place.getId());
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

    private void loadVisits(int idPlace) {
        visits.clear();
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "visits").allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();
        visits.addAll(db.visitDao().getVisitsByPlace(idPlace));
        visitsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.countries_list_visits) {
            Country country = countries.get(position);
            places.clear();
            placesAdapter.notifyDataSetChanged();
            visits.clear();
            visitsAdapter.notifyDataSetChanged();
            place.setId(0);
            loadProvinces(country.getId());
        } else  if (parent.getId() == R.id.provinces_list_visits) {
            Province province = provinces.get(position);
            visits.clear();
            visitsAdapter.notifyDataSetChanged();
            place.setId(0);
            loadPlaces(province.getId());
        } else if (parent.getId() == R.id.places_list_visits) {
            place = places.get(position);
            loadVisits(place.getId());
        } else {
            Visit visit = visits.get(position);
            Intent intent = new Intent(this, ModifyVisitActivity.class);
            intent.putExtra("modify", 1);
            intent.putExtra("placeName", place.getName());
            intent.putExtra("Visit", visit);
            startActivity(intent);
        }
    }

    public void openNewVisit(View view) {
        if (place.getId() == 0) {
            Toast.makeText(this, getString(R.string.no_place_selected), Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, ModifyVisitActivity.class);
            intent.putExtra("modify", 0);
            intent.putExtra("placeId", place.getId());
            intent.putExtra("placeName", place.getName());
            startActivity(intent);
        }
    }
}