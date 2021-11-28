package com.svalero.tripalbum;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.svalero.tripalbum.database.AppDatabase;
import com.svalero.tripalbum.domain.Country;

public class NewCountryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_country);
    }

    public void addCountry(View view) {
        EditText etName = findViewById(R.id.country_name);
        String name = etName.getText().toString();

        if (name.equals("")) {
            Toast.makeText(this, getString(R.string.add_missing_data), Toast.LENGTH_SHORT).show();
        } else {
        Country country = new Country(0, name);

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "countries").allowMainThreadQueries().build();
        db.countryDao().insert(country);

        Toast.makeText(this, getString(R.string.country_added), Toast.LENGTH_SHORT).show();

        etName.setText("");
        }
    }
}