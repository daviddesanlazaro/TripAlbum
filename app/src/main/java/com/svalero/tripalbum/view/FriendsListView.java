package com.svalero.tripalbum.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.svalero.tripalbum.R;
import com.svalero.tripalbum.contract.FriendsListContract;
import com.svalero.tripalbum.domain.User;
import com.svalero.tripalbum.presenter.FriendsListPresenter;

import java.util.ArrayList;
import java.util.List;

public class FriendsListView extends AppCompatActivity implements FriendsListContract.View, AdapterView.OnItemClickListener {

    private FriendsListPresenter presenter;

    public List<User> friendsList;
    private ArrayAdapter<User> friendsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);
        presenter = new FriendsListPresenter(this);

        initializeFriendshipsList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.loadFriends(65);
    }

    private void initializeFriendshipsList() {
        friendsList = new ArrayList<>();
        friendsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, friendsList);
        ListView lvFriends = findViewById(R.id.friends_list);
        lvFriends.setAdapter(friendsAdapter);
        lvFriends.setOnItemClickListener(this);
    }

    @Override
    public void listFriends(List<User> friends) {
        friendsList.clear();
        friendsList.addAll(friends);
        friendsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.friends_list) {
            User user = friendsList.get(position);
            presenter.openMyAlbum(user);
        }
    }
}