package com.svalero.tripalbum.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.svalero.tripalbum.R;
import com.svalero.tripalbum.contract.MyAlbumContract;
import com.svalero.tripalbum.domain.Place;
import com.svalero.tripalbum.presenter.MyAlbumPresenter;

import java.util.ArrayList;
import java.util.List;

public class MyAlbumView extends AppCompatActivity implements MyAlbumContract.View, AdapterView.OnItemClickListener {

    private MyAlbumContract.Presenter presenter;

    public List<Place> visitedPlaces;
    private ArrayAdapter<Place> visitedAdapter;
    public List<Place> interestingPlaces;
    private ArrayAdapter<Place> interestingAdapter;
    private int userId;

    private ListView lvVisited;
    private ListView lvInteresting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_album);
        presenter = new MyAlbumPresenter(this);

        Intent intent = getIntent();
        userId = intent.getIntExtra("userId", 65);

        initializeVisitedPlaces();
        initializeInterestingPlaces();

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.loadVisited(userId);
        presenter.loadInteresting(userId);
    }

    private void initializeVisitedPlaces() {
        visitedPlaces = new ArrayList<>();
        visitedAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, visitedPlaces);
        lvVisited = findViewById(R.id.visited_places);
        lvVisited.setAdapter(visitedAdapter);
        lvVisited.setOnItemClickListener(this);
    }

    private void initializeInterestingPlaces() {
        interestingPlaces = new ArrayList<>();
        interestingAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, interestingPlaces);
        lvInteresting = findViewById(R.id.interresting_places);
        lvInteresting.setAdapter(interestingAdapter);
    }

    @Override
    public void listVisited(List<Place> places) {
        visitedPlaces.clear();
        visitedPlaces.addAll(places);
        visitedAdapter.notifyDataSetChanged();
    }

    @Override
    public void listInteresting(List<Place> places) {
        interestingPlaces.clear();
        interestingPlaces.addAll(places);
        interestingAdapter.notifyDataSetChanged();
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

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.visited_places) {
            Place place = visitedPlaces.get(position);
            presenter.openViewVisits(userId, place.getId());
        }
    }
}