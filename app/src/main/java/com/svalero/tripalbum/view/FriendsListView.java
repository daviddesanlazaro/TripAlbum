package com.svalero.tripalbum.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.svalero.tripalbum.R;
import com.svalero.tripalbum.contract.FriendsListContract;
import com.svalero.tripalbum.domain.Friend;
import com.svalero.tripalbum.domain.User;
import com.svalero.tripalbum.presenter.FriendsListPresenter;

import java.util.ArrayList;
import java.util.List;

public class FriendsListView extends AppCompatActivity implements FriendsListContract.View, AdapterView.OnItemClickListener {

    private FriendsListPresenter presenter;

    public List<User> friendsList;
    public List<User> searchList;
    private ArrayAdapter<User> friendsAdapter;
    private ArrayAdapter<User> searchAdapter;

    private View customLayout;
    private LinearLayout layout;
    private EditText phone;

    private User friend;
    private User user = new User("624c4ba4e6a95b2e80b77bed", null, null, null, null);    // Solución hasta hacer control de sesión

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);
        presenter = new FriendsListPresenter(this);

        initializeFriendsList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.loadFriends();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_friends, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        layout = findViewById(R.id.search_friends_layout);
        if (layout.getVisibility() == View.GONE) {
            layout.setVisibility(View.VISIBLE);
            phone = findViewById(R.id.search_friend_phone);
            phone.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(phone, InputMethodManager.SHOW_IMPLICIT);
        }
        else
            layout.setVisibility(View.GONE);
        return true;
    }

    private void initializeFriendsList() {
        friendsList = new ArrayList<>();
        friendsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, friendsList);
        ListView lvFriends = findViewById(R.id.friends_list);
        lvFriends.setAdapter(friendsAdapter);
        lvFriends.setOnItemClickListener(this);
        registerForContextMenu(lvFriends);
    }

    private void initializeSearchList() {
        searchList = new ArrayList<>();
        searchAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, searchList);
        ListView lvSearch = customLayout.findViewById(R.id.search_friend_list);
        lvSearch.setAdapter(searchAdapter);
        lvSearch.setOnItemClickListener(this);
    }

    public void searchFriends(View view) {
        if (phone.getText().toString().equals(""))
            Toast.makeText(this, R.string.empty_phone, Toast.LENGTH_SHORT).show();
        else {
            customLayout = getLayoutInflater().inflate(R.layout.search_friend, null, false);
            initializeSearchList();
            presenter.loadUsers(phone.getText().toString());
        }
    }

    @Override
    public void listFriends(List<User> friends) {
        friendsList.clear();
        friendsList.addAll(friends);
        friendsAdapter.notifyDataSetChanged();
    }

    @Override
    public void listSearchFriends(List<User> searchUsers) {
        searchList.clear();
        searchList.addAll(searchUsers);
        searchAdapter.notifyDataSetChanged();

        if (searchList.size() == 0) {
                Toast.makeText(this, R.string.user_not_found, Toast.LENGTH_SHORT).show();
        } else {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(customLayout);

            builder.setPositiveButton(R.string.select, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Friend newFriend = new Friend(friend.getId(), friend.getUsername(), friend.getEmail(), friend.getPhone());
                    layout.setVisibility(View.GONE);
                    presenter.addFriend(newFriend, user.getId());
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
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
        } else if (parent.getId() == R.id.search_friend_list) {
            friend = searchList.get(position);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contextual_friends, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if (item.getItemId() == R.id.delete_friend) {
            User friend = friendsList.get(info.position);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.delete_friend_dialog)
                    .setPositiveButton(R.string.confirm_yes,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                presenter.deleteFriend(friend.getId());
                                }})
                    .setNegativeButton(R.string.confirm_no,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }});
            builder.create().show();
        }
        return true;
    }
}