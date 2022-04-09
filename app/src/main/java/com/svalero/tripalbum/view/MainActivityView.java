package com.svalero.tripalbum.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.svalero.tripalbum.R;
import com.svalero.tripalbum.domain.User;

public class MainActivityView extends AppCompatActivity {

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = new User("624c4ba4e6a95b2e80b77bed", null, null, null, null);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void openMyAlbum(View view) {
        Intent intent = new Intent(this, MyAlbumView.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    public void openFriendsList(View view) {
        Intent intent = new Intent(this, FriendsListView.class);
        startActivity(intent);
    }

    public void openSearchPlaces(View view) {
        Intent intent = new Intent(this, SearchPlacesView.class);
        startActivity(intent);
    }
}