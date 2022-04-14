package com.svalero.tripalbum.presenter;

import android.content.Intent;
import android.widget.Toast;

import com.svalero.tripalbum.R;
import com.svalero.tripalbum.contract.FriendsListContract;
import com.svalero.tripalbum.domain.Friend;
import com.svalero.tripalbum.domain.User;
import com.svalero.tripalbum.model.FriendsListModel;
import com.svalero.tripalbum.view.FriendsListView;
import com.svalero.tripalbum.view.MyAlbumView;

import java.util.ArrayList;
import java.util.List;

public class FriendsListPresenter implements FriendsListContract.Presenter, FriendsListContract.Model.OnLoadUsersListener {

    private final FriendsListView view;
    private final FriendsListModel model;

    public FriendsListPresenter(FriendsListView view) {
        model = new FriendsListModel();
        this.view = view;
    }

    @Override
    public void loadFriends() {
        List<Friend> friends = model.loadFriends(view);
        if (friends.size() > 0) {
            List<User> users = new ArrayList<>();
            for (Friend f : friends) {
                User user = new User(f.getId(), f.getUsername(), null, f.getEmail(), f.getPhone());
                users.add(user);
            }
            view.listFriends(users);
        }
    }

    @Override
    public void loadUsers(String phone) {
        model.loadUsers(this, phone);
    }

    @Override
    public void addFriend(Friend friend, String userId) {
        List<Friend> friends = model.loadFriends(view);
        if (friends.contains(friend))
            Toast.makeText(view, view.getString(R.string.friend_already_exists), Toast.LENGTH_SHORT).show();
        else if (userId.equals(friend.getId()))
            Toast.makeText(view, view.getString(R.string.this_is_you), Toast.LENGTH_SHORT).show();
        else
            model.addFriend(view, friend);
    }

    @Override
    public void deleteFriend(String friendId) {
        Friend friend = new Friend(friendId, null, null, null);
        model.deleteFriend(view, friend);
    }

    @Override
    public void openMyAlbum(User user, String action) {
        Intent intent = new Intent(view, MyAlbumView.class);
        intent.putExtra("user", user);
        intent.putExtra("ACTION", action);
        view.startActivity(intent);
    }

    @Override
    public void OnLoadUsersSuccess(List<User> users) {
        view.listSearchFriends(users);
    }

    @Override
    public void OnLoadUsersError(String message) {
        view.showErrorMessage(message);
    }
}
