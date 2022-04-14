package com.svalero.tripalbum.view;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.svalero.tripalbum.R;
import com.svalero.tripalbum.contract.ViewPlaceContract;
import com.svalero.tripalbum.domain.Place;
import com.svalero.tripalbum.domain.User;
import com.svalero.tripalbum.presenter.ViewPlacePresenter;

public class ViewPlaceView extends AppCompatActivity implements ViewPlaceContract.View {

    private ViewPlacePresenter presenter;

    private Place place;
    private User user;

    private Button add, remove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_place);
        presenter = new ViewPlacePresenter(this);

        Intent intent = getIntent();
        place = (Place) intent.getSerializableExtra("place");
        user = (User) intent.getSerializableExtra("user");

        initializeView();
        presenter.checkFavorite(place);
    }

    private void initializeView() {
        TextView name = findViewById(R.id.view_place_name);
        TextView description = findViewById(R.id.view_place_description);
        name.setText(place.getName());
        description.setText(place.getDescription());
        add = findViewById(R.id.view_place_interested_add);
        remove = findViewById(R.id.view_place_interested_remove);
    }

    public void openNewVisit(View view) {
        presenter.openNewVisit(place, user);
    }

    public void viewPlaceMap(View view) {
        presenter.openViewMap(place);
    }

    public void addFavorite(View view) {
        presenter.addFavorite(place);
        Toast.makeText(this, getString(R.string.added_to_favorites), Toast.LENGTH_SHORT).show();
        presenter.checkFavorite(place);
    }

    public void deleteFavorite(View view) {
        presenter.deleteFavorite(place);
        Toast.makeText(this, getString(R.string.deleted_from_favorites), Toast.LENGTH_SHORT).show();
        presenter.checkFavorite(place);
    }

    public void showAdd() {
        add.setVisibility(VISIBLE);
        remove.setVisibility(GONE);
    }

    public void showDelete() {
        add.setVisibility(GONE);
        remove.setVisibility(VISIBLE);
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_view_place, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.view_place_preferences) {
            Intent intent = new Intent(this, PreferencesView.class);
            startActivity(intent);
            return true;
        }
        return true;
    }
}