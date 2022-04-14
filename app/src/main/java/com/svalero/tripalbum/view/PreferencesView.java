package com.svalero.tripalbum.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.svalero.tripalbum.R;

public class PreferencesView extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        getSupportActionBar().setTitle(R.string.preferences);

        if (findViewById(R.id.fragment_preferences) != null) {
            if (savedInstanceState != null) {
                return;
            }
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_preferences, new PreferenceFragment()).commit();
        }
    }
}