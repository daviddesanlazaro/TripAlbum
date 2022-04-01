package com.svalero.tripalbum.presenter;

import android.content.Intent;

import com.svalero.tripalbum.contract.FriendsListContract;
import com.svalero.tripalbum.domain.User;
import com.svalero.tripalbum.model.FriendsListModel;
import com.svalero.tripalbum.view.FriendsListView;
import com.svalero.tripalbum.view.MyAlbumView;

import java.util.List;

public class FriendsListPresenter implements FriendsListContract.Presenter, FriendsListContract.Model.OnLoadFriendsListener {

    private final FriendsListView view;
    private final FriendsListModel model;

    public FriendsListPresenter(FriendsListView view) {
        model = new FriendsListModel();
        this.view = view;
    }

    @Override
    public void loadFriends(long userId) {
        model.loadFriends(this, userId);
    }

    @Override
    public void OnLoadFriendsSuccess(List<User> friends) {
        view.listFriends(friends);
    }

    @Override
    public void OnLoadFriendsError(String message) {
        view.showErrorMessage(message);
    }

    @Override
    public void openMyAlbum(User user) {
        Intent intent = new Intent(view, MyAlbumView.class);
        intent.putExtra("user", user);
        view.startActivity(intent);
    }
}
