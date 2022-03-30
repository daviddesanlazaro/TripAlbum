package com.svalero.tripalbum.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.svalero.tripalbum.R;
import com.svalero.tripalbum.domain.Country;
import com.svalero.tripalbum.domain.Place;
import com.svalero.tripalbum.domain.Province;
import com.svalero.tripalbum.domain.Visit;

import java.util.ArrayList;
import java.util.List;

public class MyAlbumView extends AppCompatActivity {

    public List<Place> visitedPlaces;
    private ArrayAdapter<Place> visitedAdapter;
    public List<Place> interestingPlaces;
    private ArrayAdapter<Place> interestingAdapter;

    private ListView lvVisited;
    private ListView lvInteresting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_album);

        initializeVisitedPlaces();
        initializeInterestingPlaces();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initializeVisitedPlaces() {
        visitedPlaces = new ArrayList<>();
        visitedAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, visitedPlaces);
        lvVisited = findViewById(R.id.visited_places);
        lvVisited.setAdapter(visitedAdapter);
    }

    private void initializeInterestingPlaces() {
        interestingPlaces = new ArrayList<>();
        interestingAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, interestingPlaces);
        lvInteresting = findViewById(R.id.interresting_places);
        lvInteresting.setAdapter(interestingAdapter);
    }

    public void toggleVisited(View view) {
        if (lvVisited.getVisibility() == View.GONE)
            lvVisited.setVisibility(View.VISIBLE);
        else
            lvVisited.setVisibility(View.GONE);
    }

    public void toggleInteresting(View view) {
        if (lvInteresting.getVisibility() == View.GONE)
            lvInteresting.setVisibility(View.VISIBLE);
        else
            lvInteresting.setVisibility(View.GONE);
    }
}