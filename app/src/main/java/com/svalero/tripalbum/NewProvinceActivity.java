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

import com.svalero.tripalbum.database.AppDatabase;
import com.svalero.tripalbum.domain.Country;
import com.svalero.tripalbum.domain.Province;

import java.util.ArrayList;
import java.util.List;

public class NewProvinceActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public static List<Country> countries;
    private ArrayAdapter<Country> countriesAdapter;
    private Country country = new Country (0, null);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_province);

        countries = new ArrayList<>();
        ListView lvCountries = findViewById(R.id.countries_list);
        countriesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, countries);
        lvCountries.setAdapter(countriesAdapter);
        lvCountries.setOnItemClickListener(this);

        Button button = findViewById(R.id.new_country);
        button.setOnClickListener(v -> openNewCountry());
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

    public void addProvince(View view) {
        EditText etName = findViewById(R.id.province_name);
        String name = etName.getText().toString();

        if ((country.getId() == 0) || (name.equals(""))) {
            Toast.makeText(this, getString(R.string.add_missing_data), Toast.LENGTH_SHORT).show();
        } else {
            Province province = new Province(0, name, country.getId());

            AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "provinces").allowMainThreadQueries().build();
            db.provinceDao().insert(province);

            Toast.makeText(this, getString(R.string.province_added), Toast.LENGTH_SHORT).show();
            etName.setText("");
        }
    }

    public void openNewCountry() {
        Intent intent = new Intent(this, NewCountryActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        country = countries.get(position);
    }

}