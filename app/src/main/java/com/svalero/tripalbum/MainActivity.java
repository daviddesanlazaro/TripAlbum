package com.svalero.tripalbum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

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

import com.svalero.tripalbum.database.AppDatabase;
import com.svalero.tripalbum.domain.Country;
import com.svalero.tripalbum.domain.Place;
import com.svalero.tripalbum.domain.Province;
import com.svalero.tripalbum.domain.Visit;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnCreateContextMenuListener {

    public List<Province> provinces;
    private ArrayAdapter<Province> provincesAdapter;
    public List<Country> countries;
    private ArrayAdapter<Country> countriesAdapter;
    public List<Place> places;
    private ArrayAdapter<Place> placesAdapter;
    public ArrayList<Visit> visits;
    private VisitAdapter visitsAdapter;
    private Place place = new Place (0, null, null, 0, 0, 0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        visits = new ArrayList<>();
        ListView gvVisits = (ListView) findViewById(R.id.visit_list_main);
        visitsAdapter = new VisitAdapter(this, visits);
        gvVisits.setAdapter(visitsAdapter);
        gvVisits.setOnItemClickListener(this);
        registerForContextMenu(gvVisits);

        places = new ArrayList<>();
        ListView lvPlaces = findViewById(R.id.places_list_main);
        placesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, places);
        lvPlaces.setAdapter(placesAdapter);
        lvPlaces.setOnItemClickListener(this);

        provinces = new ArrayList<>();
        ListView lvProvinces = findViewById(R.id.provinces_list_main);
        provincesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, provinces);
        lvProvinces.setAdapter(provincesAdapter);
        lvProvinces.setOnItemClickListener(this);

        countries = new ArrayList<>();
        ListView lvCountries = findViewById(R.id.countries_list_main);
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
        if (parent.getId() == R.id.countries_list_main) {
            Country country = countries.get(position);
            places.clear();
            placesAdapter.notifyDataSetChanged();
            visits.clear();
            visitsAdapter.notifyDataSetChanged();
            place.setId(0);
            loadProvinces(country.getId());
        }
        if (parent.getId() == R.id.provinces_list_main) {
            Province province = provinces.get(position);
            visits.clear();
            visitsAdapter.notifyDataSetChanged();
            place.setId(0);
            loadPlaces(province.getId());
        }
        if (parent.getId() == R.id.places_list_main) {
            place = places.get(position);
            loadVisits(place.getId());
        }
        if (parent.getId() == R.id.visit_list_main) {
            Visit visit = visits.get(position);
            Intent intent = new Intent(this, ModifyVisitActivity.class);
            intent.putExtra("modify", 1);
            intent.putExtra("placeName", place.getName());
            intent.putExtra("Visit", visit);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(this, NewPlaceActivity.class);
        startActivity(intent);
        return true;
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

    public void openViewPlace(View view) {
        if (place.getId() == 0) {
            Toast.makeText(this, getString(R.string.no_place_selected), Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, ViewPlaceActivity.class);
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
            Visit visit = visits.get(itemSeleccionado);
            Intent intent = new Intent(this, ModifyVisitActivity.class);
            intent.putExtra("modify", 1);
            intent.putExtra("placeName", place.getName());
            intent.putExtra("Visit", visit);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.delete_context) {
            deleteVisit(info);
        }
        return true;
    }

    public void deleteVisit(AdapterView.AdapterContextMenuInfo info) {
        Visit visit = visits.get(info.position);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_confirm_dialog)
                .setPositiveButton(R.string.confirm_yes,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                                        AppDatabase.class, "visits").allowMainThreadQueries().build();
                                db.visitDao().delete(visit);
                                loadVisits(place.getId());
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