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

import com.svalero.tripalbum.domain.Place;
import com.svalero.tripalbum.domain.Province;

import java.util.ArrayList;

public class NewPlaceActivity extends AppCompatActivity {

    public static ArrayList<Province> provinces;
    private ArrayAdapter<Province> provincesAdapter;
    private Button buttonProvince;
    private Button buttonCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_place);

        provinces = new ArrayList<>();
        ListView lvProvinces = findViewById(R.id.provinces_list);
        provincesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, provinces);
        lvProvinces.setAdapter(provincesAdapter);

        buttonProvince = findViewById(R.id.new_province);
        buttonProvince.setOnClickListener(v -> openNewProvince());

        buttonCountry = findViewById(R.id.new_country_place);
        buttonCountry.setOnClickListener(v -> openNewCountry());
    }

    @Override
    protected void onResume() {
        super.onResume();
        provincesAdapter.notifyDataSetChanged();
    }

//    public void addPlace(View view) {
//        EditText etName = findViewById(R.id.place_name);
//        EditText etDesc = findViewById(R.id.place_desc);
//        EditText etLat = findViewById(R.id.place_lat);
//        EditText etLong = findViewById(R.id.place_long);
//
//        String name = etName.getText().toString();
//        String desc = etDesc.getText().toString();
//        String latitude = etLat.getText().toString();
//        String longitude = etLong.getText().toString();
//
//        Place place = new Place(1, name, desc, Float.parseFloat(latitude), Float.parseFloat(longitude), null);
//
//        Toast.makeText(this, getString(R.string.province_added), Toast.LENGTH_SHORT).show();
//        etName.setText("");
//        etDesc.setText("");
//        etLat.setText("");
//        etLong.setText("");
//    }


    public void openNewProvince() {
        Intent intent = new Intent(this, NewProvinceActivity.class);
        startActivity(intent);
    }

    public void openNewCountry() {
        Intent intent = new Intent(this, NewCountryActivity.class);
        startActivity(intent);
    }
}