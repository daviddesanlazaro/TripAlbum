package com.svalero.tripalbum.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.svalero.tripalbum.R;
import com.svalero.tripalbum.domain.User;

public class MainActivityView extends AppCompatActivity {

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Es posible cambiar de usuario fijando su ID, pero los lugares favoritos y amigos serán comunes a todos los usuarios (room)
        user = new User("624c4ba4e6a95b2e80b77bed", null, null, null, null);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void openMyAlbum(View view) {
        Intent intent = new Intent(this, MyAlbumView.class);
        intent.putExtra("user", user);
        intent.putExtra("ACTION", "USER");
        startActivity(intent);
    }

    public void openFriendsList(View view) {
        Intent intent = new Intent(this, FriendsListView.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    public void openSearchPlaces(View view) {
        Intent intent = new Intent(this, SearchPlacesView.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.main_preferences) {
            Intent intent = new Intent(this, PreferencesView.class);
            startActivity(intent);
            return true;
        }
        return true;
    }
}