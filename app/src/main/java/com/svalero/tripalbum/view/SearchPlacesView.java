package com.svalero.tripalbum.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.svalero.tripalbum.R;
import com.svalero.tripalbum.contract.SearchPlacesContract;
import com.svalero.tripalbum.domain.Place;
import com.svalero.tripalbum.domain.Province;
import com.svalero.tripalbum.presenter.SearchPlacesPresenter;

import java.util.ArrayList;
import java.util.List;

public class SearchPlacesView extends AppCompatActivity implements SearchPlacesContract.View, AdapterView.OnItemSelectedListener {

    private SearchPlacesPresenter presenter;

    public List<Province> provincesList;
    private ArrayAdapter<Province> provincesAdapter;
    public List<Place> placesList;
    private ArrayAdapter<Place> placesAdapter;

    private Spinner spinner;
    private EditText search;
    private ListView lvPlaces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_places);
        presenter = new SearchPlacesPresenter(this);

        initializeProvincesList();
        initializePlacesList();
        search = findViewById(R.id.search_places_search);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.loadProvinces(97);
        presenter.loadPlaces(67, null);
    }

    private void initializeProvincesList() {
        provincesList = new ArrayList<>();
        provincesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, provincesList);
        spinner = findViewById(R.id.search_places_provinces);
        spinner.setAdapter(provincesAdapter);
        spinner.setOnItemSelectedListener(this);
    }

    private void initializePlacesList() {
        placesList = new ArrayList<>();
        placesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, placesList);
        lvPlaces = findViewById(R.id.search_places_list);
        lvPlaces.setAdapter(placesAdapter);
    }

    @Override
    public void listProvinces(List<Province> provinces) {
        provincesList.clear();
        provincesList.addAll(provinces);
        provincesAdapter.notifyDataSetChanged();
    }

    @Override
    public void listPlaces(List<Place> places) {
        placesList.clear();
        placesList.addAll(places);
        placesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent,
                               View view, int pos, long id) {
        Province province = (Province) spinner.getSelectedItem();
        presenter.loadPlaces(province.getId(), null);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void searchPlaces(View view) {
        presenter.loadPlaces(67, search.getText().toString());
    }
}