package com.svalero.tripalbum.view;

import static android.view.View.GONE;
import static com.svalero.tripalbum.api.Constants.Action.USER;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.svalero.tripalbum.R;
import com.svalero.tripalbum.api.Constants.Action;
import com.svalero.tripalbum.contract.MyAlbumContract;
import com.svalero.tripalbum.domain.Place;
import com.svalero.tripalbum.domain.User;
import com.svalero.tripalbum.presenter.MyAlbumPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MyAlbumView extends AppCompatActivity implements MyAlbumContract.View, AdapterView.OnItemClickListener {

    private MyAlbumContract.Presenter presenter;

    public List<Place> visitedList;
    private ArrayAdapter<Place> visitedAdapter;
    public List<Place> favoritesList;
    private ArrayAdapter<Place> favoritesAdapter;
    private User user;
    private Action action;

    private ListView lvVisited;
    private ListView lvFavorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_album);
        presenter = new MyAlbumPresenter(this);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        action = Action.valueOf(getIntent().getStringExtra("ACTION"));

        checkUser();
        initializeVisitedPlaces();
        if (action == USER)
            initializeFavoritePlaces();
    }

    @Override
    protected void onResume() {
        super.onResume();
        visitedList.clear();
        presenter.loadVisited(user.getId());
        if (action == USER)
            presenter.loadFavorites();
        else {
            Button visited = findViewById(R.id.visited_button);
            visited.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            Button favorites = findViewById(R.id.favorite_button);
            lvFavorites = findViewById(R.id.my_album_favorites);
            favorites.setVisibility(GONE);
            lvFavorites.setVisibility(GONE);
        }

    }

    private void initializeVisitedPlaces() {
        visitedList = new ArrayList<>();
        visitedAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, visitedList);
        lvVisited = findViewById(R.id.my_album_visited);
        lvVisited.setAdapter(visitedAdapter);
        lvVisited.setOnItemClickListener(this);
    }

    private void initializeFavoritePlaces() {
        favoritesList = new ArrayList<>();
        favoritesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, favoritesList);
        lvFavorites = findViewById(R.id.my_album_favorites);
        lvFavorites.setAdapter(favoritesAdapter);
        lvFavorites.setOnItemClickListener(this);
    }

    private void checkUser() {
        TextView title = findViewById(R.id.my_album_title);
        if (action == USER) {
            title.setText(getString(R.string.main_my_album));
        }
        else {
            title.setText(getString(R.string.friend_album_title, user.getUsername().toUpperCase(Locale.ROOT)));
        }
    }

    @Override
    public void refreshVisited() {
        visitedAdapter.notifyDataSetChanged();
    }

    @Override
    public void listFavorites(List<Place> places) {
        favoritesList.clear();
        favoritesList.addAll(places);
        favoritesAdapter.notifyDataSetChanged();
    }

    public void toggleVisited(View view) {
        if (lvVisited.getVisibility() == GONE)
            lvVisited.setVisibility(View.VISIBLE);
        else
            lvVisited.setVisibility(GONE);
    }

    public void toggleFavorites(View view) {
        if (lvFavorites.getVisibility() == GONE)
            lvFavorites.setVisibility(View.VISIBLE);
        else
            lvFavorites.setVisibility(GONE);
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.my_album_visited) {
            Place place = visitedList.get(position);
            if (action == USER)
                presenter.openViewVisits(user, place, "USER");
            else
                presenter.openViewVisits(user, place, "FRIEND");
        } else if (parent.getId() == R.id.my_album_favorites) {
            Place place = favoritesList.get(position);
            presenter.openViewPlace(user, place);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_my_album, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(this, PreferencesView.class);
        startActivity(intent);
        return true;
    }
}