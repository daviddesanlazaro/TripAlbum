package com.svalero.tripalbum.view;

import androidx.appcompat.app.AppCompatActivity;

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
import com.svalero.tripalbum.R;
import com.svalero.tripalbum.contract.NewPlaceContract;
import com.svalero.tripalbum.domain.Country;
import com.svalero.tripalbum.domain.Place;
import com.svalero.tripalbum.domain.Province;
import com.svalero.tripalbum.presenter.MainActivityPresenter;
import com.svalero.tripalbum.presenter.NewPlacePresenter;

import java.util.ArrayList;
import java.util.List;

public class NewPlaceActivityView extends AppCompatActivity implements NewPlaceContract.View, AdapterView.OnItemClickListener,
        OnMapReadyCallback, GoogleMap.OnMapClickListener {

    public List<Province> provincesList;
    private ArrayAdapter<Province> provincesAdapter;
    public List<Country> countriesList;
    private ArrayAdapter<Country> countriesAdapter;
    private Province province = new Province (0, null, 0);
    private float[] position = {0, 0};
    private GoogleMap map;
    private Marker marker;
    EditText etName;
    EditText etDesc;

    private NewPlacePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_place);
        presenter = new NewPlacePresenter(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment!=null) {
            mapFragment.getMapAsync(this);
        }

        etName = findViewById(R.id.place_name);
        etDesc = findViewById(R.id.place_desc);

        initializeCountriesList();
        initializeProvincesList();
        initializeButtons();
    }

    private void initializeProvincesList() {
        provincesList = new ArrayList<>();
        provincesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, provincesList);
        ListView lvProvinces = findViewById(R.id.provinces_list);
        lvProvinces.setAdapter(provincesAdapter);
        lvProvinces.setOnItemClickListener(this);
    }

    private void initializeCountriesList() {
        countriesList = new ArrayList<>();
        ListView lvCountries = findViewById(R.id.countries_list_places);
        countriesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, countriesList);
        lvCountries.setAdapter(countriesAdapter);
        lvCountries.setOnItemClickListener(this);
    }

    private void initializeButtons() {
        Button buttonProvince = findViewById(R.id.new_province);
        buttonProvince.setOnClickListener(v -> openNewProvince());
        Button buttonCountry = findViewById(R.id.new_country_place);
        buttonCountry.setOnClickListener(v -> openNewCountry());
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.loadCountries();
    }

    @Override
    public void listCountries(List<Country> countries) {
        countriesList.clear();
        countriesList.addAll(countries);
        countriesAdapter.notifyDataSetChanged();
    }

    @Override
    public void listProvinces(List<Province> provinces) {
        provincesList.clear();
        provincesList.addAll(provinces);
        provincesAdapter.notifyDataSetChanged();
    }

    private void clearFields() {
        etName.setText("");
        etDesc.setText("");
        position[0] = 0;
        position[1] = 0;
        marker.remove();
    }

    public void addPlace(View view) {
        String name = etName.getText().toString();
        String desc = etDesc.getText().toString();

        if ((province.getId() == 0) || (name.equals("")) || (desc.equals(""))) {
            Toast.makeText(this, getString(R.string.add_missing_data), Toast.LENGTH_SHORT).show();
        } else if (position[0] == 0) {
            Toast.makeText(this, getString(R.string.add_missing_location), Toast.LENGTH_SHORT).show();
        } else {
            Place place = new Place(0, name, desc, position[0], position[1], province.getId());
            presenter.addPlace(place);

            Toast.makeText(this, getString(R.string.place_added), Toast.LENGTH_SHORT).show();
            clearFields();
        }
    }

    public void openNewProvince() {
        Intent intent = new Intent(this, NewProvinceActivityView.class);
        startActivity(intent);
    }

    public void openNewCountry() {
        Intent intent = new Intent(this, NewCountryActivityView.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.countries_list_places) {
            Country country = countriesList.get(position);
            province.setId(0);
            presenter.loadProvinces(country.getId());
        } else {
            province = provincesList.get(position);
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
    }
}