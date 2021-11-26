package com.svalero.tripalbum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.svalero.tripalbum.domain.Country;
import com.svalero.tripalbum.domain.Province;

import java.util.ArrayList;

public class NewProvinceActivity extends AppCompatActivity {

    public static ArrayList<Country> countries;
    private ArrayAdapter<Country> countriesAdapter;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_province);

        countries = new ArrayList<>();
        ListView lvCountries = findViewById(R.id.countries_list);
        countriesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, countries);
        lvCountries.setAdapter(countriesAdapter);

        button = findViewById(R.id.new_country);
        button.setOnClickListener(v -> openNewCountry());
    }

    @Override
    protected void onResume() {
        super.onResume();
        countriesAdapter.notifyDataSetChanged();
    }

    public void addProvince(View view) {
        EditText etName = findViewById(R.id.province_name);

        String name = etName.getText().toString();

        Province province = new Province(0, name, null);

        NewPlaceActivity.provinces.add(province);
        Toast.makeText(this, getString(R.string.province_added), Toast.LENGTH_SHORT).show();
        etName.setText("");
    }

    public void openNewCountry() {
        Intent intent = new Intent(this, NewCountryActivity.class);
        startActivity(intent);
    }

}