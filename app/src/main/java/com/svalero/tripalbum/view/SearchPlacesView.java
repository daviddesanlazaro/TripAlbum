package com.svalero.tripalbum.view;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.svalero.tripalbum.R;
import com.svalero.tripalbum.contract.SearchPlacesContract;
import com.svalero.tripalbum.domain.Place;
import com.svalero.tripalbum.domain.Province;
import com.svalero.tripalbum.domain.User;
import com.svalero.tripalbum.presenter.SearchPlacesPresenter;

import java.util.ArrayList;
import java.util.List;

public class SearchPlacesView extends AppCompatActivity implements SearchPlacesContract.View, AdapterView.OnItemClickListener {

    private SearchPlacesPresenter presenter;

    public List<Province> provincesList;
    private ArrayAdapter<Province> provincesAdapter;
    public List<Place> placesList;
    private ArrayAdapter<Place> placesAdapter;
    private Province province;
    private EditText search;
    private AutoCompleteTextView autocomplete;
    private SharedPreferences preferences;
    private boolean favorites;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_places);
        presenter = new SearchPlacesPresenter(this);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        initializeProvincesList();
        initializePlacesList();
        search = findViewById(R.id.search_places_search);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.loadProvinces();
        LinearLayout layout = findViewById(R.id.search_places_layout);
        favorites = preferences.getBoolean("preferences_favorites", false);
        if (favorites) {
            presenter.loadFavorites(null, null);
            layout.setVisibility(GONE);
        }
        else {
            presenter.loadPlaces(null, null);
            layout.setVisibility(VISIBLE);
        }
    }

    private void initializeProvincesList() {
        provincesList = new ArrayList<>();
        provincesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, provincesList);
        autocomplete = findViewById(R.id.search_places_provinces);
        autocomplete.setAdapter(provincesAdapter);
        province = new Province(null, null, null);

        autocomplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {
                province = (Province) parent.getItemAtPosition(position);
                if (favorites)
                    presenter.loadFavorites(province.getId(), null);
                else
                    presenter.loadPlaces(province.getId(), null);
            }
        });
    }

    private void initializePlacesList() {
        placesList = new ArrayList<>();
        placesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, placesList);
        ListView lvPlaces = findViewById(R.id.search_places_list);
        lvPlaces.setAdapter(placesAdapter);
        lvPlaces.setOnItemClickListener(this);
        registerForContextMenu(lvPlaces);
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
    public void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void searchPlaces(View view) {
        if (favorites)
            presenter.loadFavorites(province.getId(), search.getText().toString());
        else
            presenter.loadPlaces(province.getId(), search.getText().toString());
    }

    public void clear(View view) {
        autocomplete.setText("");
        province.setId(null);
        if (favorites)
            presenter.loadFavorites(null, null);
        else
            presenter.loadPlaces(null, null);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.search_places_list) {
            Place place = placesList.get(position);
            presenter.openViewPlace(place, user);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_search_places, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.search_places_preferences) {
            Intent intent = new Intent(this, PreferencesView.class);
            startActivity(intent);
            return true;
        }
        return true;
    }
}