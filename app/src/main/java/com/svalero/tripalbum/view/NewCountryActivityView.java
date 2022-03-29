package com.svalero.tripalbum.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.svalero.tripalbum.R;
import com.svalero.tripalbum.contract.NewCountryContract;
import com.svalero.tripalbum.domain.Country;
import com.svalero.tripalbum.presenter.NewCountryPresenter;

public class NewCountryActivityView extends AppCompatActivity implements NewCountryContract.View {

    private NewCountryPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_country);
        presenter = new NewCountryPresenter(this);
    }

    public void addCountry(View view) {
        EditText etName = findViewById(R.id.country_name);
        String name = etName.getText().toString();

        if (name.equals("")) {
            Toast.makeText(this, getString(R.string.add_missing_data), Toast.LENGTH_SHORT).show();
        } else {
        Country country = new Country(0, name);

        presenter.addCountry(country);

        Toast.makeText(this, getString(R.string.country_added), Toast.LENGTH_SHORT).show();
        etName.setText("");
        }
    }
}