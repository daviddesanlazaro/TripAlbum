package com.svalero.tripalbum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.new_place) {
            Intent intent = new Intent(this, NewPlaceActivity.class);
            startActivity(intent);
            return true;
        }
        if (item.getItemId() == R.id.my_visits) {
            Intent intent = new Intent(this, MyVisitsActivity.class);
            startActivity(intent);
            return true;
        }

        return false;
    }
}