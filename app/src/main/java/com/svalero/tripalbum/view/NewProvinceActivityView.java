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

import com.svalero.tripalbum.R;
import com.svalero.tripalbum.contract.NewProvinceContract;
import com.svalero.tripalbum.domain.Country;
import com.svalero.tripalbum.domain.Province;
import com.svalero.tripalbum.presenter.NewProvincePresenter;

import java.util.ArrayList;
import java.util.List;

public class NewProvinceActivityView extends AppCompatActivity implements NewProvinceContract.View, AdapterView.OnItemClickListener {

    public static List<Country> countriesList;
    private ArrayAdapter<Country> countriesAdapter;
    private Country country = new Country (0, null);
    private NewProvincePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_province);
        presenter = new NewProvincePresenter(this);

        initializeCountries();
        Button button = findViewById(R.id.new_country);
        button.setOnClickListener(v -> openNewCountry());
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.loadCountries();
    }

    private void initializeCountries() {
        countriesList = new ArrayList<>();
        ListView lvCountries = findViewById(R.id.countries_list);
        countriesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, countriesList);
        lvCountries.setAdapter(countriesAdapter);
        lvCountries.setOnItemClickListener(this);
    }

    @Override
    public void listCountries(List<Country> countries) {
        countriesList.clear();
        countriesList.addAll(countries);
        countriesAdapter.notifyDataSetChanged();
    }

    public void addProvince(View view) {
        EditText etName = findViewById(R.id.province_name);
        String name = etName.getText().toString();

        if ((country.getId() == 0) || (name.equals(""))) {
            Toast.makeText(this, getString(R.string.add_missing_data), Toast.LENGTH_SHORT).show();
        } else {
            Province province = new Province(0, name, country.getId());
            presenter.addProvince(province);

            Toast.makeText(this, getString(R.string.province_added), Toast.LENGTH_SHORT).show();
            etName.setText("");
        }
    }

    public void openNewCountry() {
        Intent intent = new Intent(this, NewCountryActivityView.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        country = countriesList.get(position);
    }

}