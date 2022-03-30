package com.svalero.tripalbum.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.svalero.tripalbum.R;
import com.svalero.tripalbum.contract.ViewVisitsContract;
import com.svalero.tripalbum.domain.Country;
import com.svalero.tripalbum.domain.Place;
import com.svalero.tripalbum.domain.Province;
import com.svalero.tripalbum.domain.Visit;
import com.svalero.tripalbum.presenter.ViewVisitsPresenter;

import java.util.ArrayList;
import java.util.List;

public class ViewVisitsView extends AppCompatActivity implements ViewVisitsContract.View, AdapterView.OnItemClickListener, View.OnCreateContextMenuListener {

    private ViewVisitsPresenter presenter;

    public List<Province> provincesList;
    private ArrayAdapter<Province> provincesAdapter;
    public List<Country> countriesList;
    private ArrayAdapter<Country> countriesAdapter;
    public List<Place> placesList;
    private ArrayAdapter<Place> placesAdapter;
    public List<Visit> visitsList;
//    private VisitAdapter visitsAdapter;
    private ArrayAdapter<Visit> visitsAdapter;
    private Place place = new Place (0, null, null, 0, 0, 0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_visits);
        presenter = new ViewVisitsPresenter(this);

        initializeCountriesList();
        initializeProvincesList();
        initializePlacesList();
        initializeVisitsList();
    }

    private void initializeCountriesList() {
        countriesList = new ArrayList<>();
        countriesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, countriesList);
        ListView lvCountries = findViewById(R.id.countries_list_main);
        lvCountries.setAdapter(countriesAdapter);
        lvCountries.setOnItemClickListener(this);
    }

    private void initializeProvincesList() {
        provincesList = new ArrayList<>();
        provincesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, provincesList);
        ListView lvProvinces = findViewById(R.id.provinces_list_main);
        lvProvinces.setAdapter(provincesAdapter);
        lvProvinces.setOnItemClickListener(this);
    }

    private void initializePlacesList() {
        placesList = new ArrayList<>();
        placesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, placesList);
        ListView lvPlaces = findViewById(R.id.places_list_main);
        lvPlaces.setAdapter(placesAdapter);
        lvPlaces.setOnItemClickListener(this);
    }

    private void initializeVisitsList() {
        visitsList = new ArrayList<>();
//        visitsAdapter = new VisitAdapter(this, visitsList);
        visitsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, visitsList);
        ListView lvVisits = findViewById(R.id.visit_list_main);
        lvVisits.setAdapter(visitsAdapter);
        lvVisits.setOnItemClickListener(this);
        registerForContextMenu(lvVisits);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.loadAllCountries();
//        presenter.loadVisits(place.getId());
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

    @Override
    public void listPlaces(List<Place> places) {
        placesList.clear();
        placesList.addAll(places);
        placesAdapter.notifyDataSetChanged();
    }

    @Override
    public void listVisits(List<Visit> visits) {
        visitsList.clear();
        visitsList.addAll(visits);
        visitsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void clearPlaces() {
        placesList.clear();
        placesAdapter.notifyDataSetChanged();
        clearVisits();
    }

    private void clearVisits() {
        visitsList.clear();
        visitsAdapter.notifyDataSetChanged();
        place.setId(0);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.countries_list_main) {
            Country country = countriesList.get(position);
            clearPlaces();
            presenter.loadProvinces(country.getId());
        }
        if (parent.getId() == R.id.provinces_list_main) {
            Province province = provincesList.get(position);
            clearVisits();
            presenter.loadPlaces(province.getId());
        }
        if (parent.getId() == R.id.places_list_main) {
            place = placesList.get(position);
            presenter.loadVisits(place.getId());
        }
        if (parent.getId() == R.id.visit_list_main) {
            Visit visit = visitsList.get(position);
            presenter.openModifyVisit(visit, place);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(this, NewPlaceActivityView.class);
        startActivity(intent);
        return true;
    }

    public void openNewVisit(View view) {
        if (place.getId() == 0) {
            Toast.makeText(this, getString(R.string.no_place_selected), Toast.LENGTH_SHORT).show();
        } else {
            presenter.openNewVisit(place);
        }
    }

    public void openViewPlace(View view) {
        if (place.getId() == 0) {
            Toast.makeText(this, getString(R.string.no_place_selected), Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, ViewPlaceActivityView.class);
            intent.putExtra("place", place);
            startActivity(intent);
        }
    }

    @Override
    public void onCreateContextMenu (ContextMenu menu, View v,
                                     ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.contextual, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final int itemSeleccionado = info.position;

        if (item.getItemId() == R.id.modify_context) {
            Visit visit = visitsList.get(itemSeleccionado);
            presenter.openModifyVisit(visit, place);
        }
        if (item.getItemId() == R.id.delete_context) {
            deleteVisit(info);
        }
        return true;
    }

    private void deleteVisit(AdapterView.AdapterContextMenuInfo info) {
        Visit visit = visitsList.get(info.position);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_confirm_dialog)
                .setPositiveButton(R.string.confirm_yes,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                presenter.deleteVisit(visit);
                                presenter.loadVisits(place.getId());
                            }})
                .setNegativeButton(R.string.confirm_no,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }});
        builder.create().show();
    }
}
