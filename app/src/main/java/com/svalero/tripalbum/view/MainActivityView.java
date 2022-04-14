package com.svalero.tripalbum.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.svalero.tripalbum.R;
import com.svalero.tripalbum.contract.MainActivityContract;
import com.svalero.tripalbum.domain.User;
import com.svalero.tripalbum.presenter.MainActivityPresenter;

import java.util.ArrayList;
import java.util.List;

public class MainActivityView extends AppCompatActivity implements MainActivityContract.View {

    private MainActivityPresenter presenter;

//    public List<User> usersList;
//    private ArrayAdapter<User> usersAdapter;
//    private View customLayout;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainActivityPresenter(this);

        // Fijar el ID del usuario que se quiera usar
        user = new User("624c4ba4e6a95b2e80b77bed", null, null, null, null);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

//    private void initializeUsersList() {
//        usersList = new ArrayList<>();
//        usersAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, usersList);
//        ListView lvUsers = customLayout.findViewById(R.id.search_friend_list);
//        lvUsers.setAdapter(usersAdapter);
//        lvUsers.setOnItemClickListener(this);
//    }

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
        startActivity(intent);
    }

//    public void changeUser(View view) {
//        customLayout = getLayoutInflater().inflate(R.layout.search_friend, null, false);
//        initializeUsersList();
//        presenter.loadUsers();
//    }

//    @Override
//    public void listUsers(List<User> users) {
//        usersList.clear();
//        usersList.addAll(users);
//        usersAdapter.notifyDataSetChanged();
//
//        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setView(customLayout);
//
//        builder.setPositiveButton(R.string.select, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
//        AlertDialog dialog = builder.create();
//        dialog.show();
//    }

//    public void showErrorMessage(String message) {
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
//    }

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

//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        if (parent.getId() == R.id.search_friend_list) {
//            user = usersList.get(position);
//        }
//    }
}