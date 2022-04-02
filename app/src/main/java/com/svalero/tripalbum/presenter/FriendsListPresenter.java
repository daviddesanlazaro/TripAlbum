package com.svalero.tripalbum.presenter;

import android.content.Intent;

import com.svalero.tripalbum.contract.FriendsListContract;
import com.svalero.tripalbum.domain.Friendship;
import com.svalero.tripalbum.domain.FriendshipDTO;
import com.svalero.tripalbum.domain.User;
import com.svalero.tripalbum.model.FriendsListModel;
import com.svalero.tripalbum.view.FriendsListView;
import com.svalero.tripalbum.view.MyAlbumView;

import java.util.List;

public class FriendsListPresenter implements FriendsListContract.Presenter, FriendsListContract.Model.OnLoadFriendsListener, FriendsListContract.Model.OnLoadSearchListener,
        FriendsListContract.Model.OnAddFriendListener, FriendsListContract.Model.OnLoadFriendshipListener, FriendsListContract.Model.OnDeleteFriendshipListener {

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
    public void loadSearchFriends(long userId, String phone) {
        model.searchFriends(this, userId, phone);
    }

    @Override
    public void addFriend(long userId, long friendId) {
        FriendshipDTO friendshipDTO = new FriendshipDTO(userId, friendId);
        model.addFriend(this, friendshipDTO);
    }

    @Override
    public void loadFriendshipForDelete(long userId, long friendId) {
        model.loadFriendship(this, userId, friendId);
    }

    @Override
    public void deleteFriendship(long friendshipId) {
        model.deleteFriendship(this, friendshipId);
    }

    @Override
    public void openMyAlbum(User user) {
        Intent intent = new Intent(view, MyAlbumView.class);
        intent.putExtra("user", user);
        view.startActivity(intent);
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
    public void OnLoadSearchSuccess(List<User> searchFriends) {
        view.listSearchFriends(searchFriends);
    }

    @Override
    public void OnLoadSearchError(String message) {
        view.showErrorMessage(message);
    }

    @Override
    public void OnAddFriendSuccess(Friendship friendship) {
        view.showErrorMessage("Bien");
    }

    @Override
    public void OnAddFriendError(String message) {
        view.showErrorMessage(message);
    }

    @Override
    public void OnLoadFriendshipSuccess(Friendship friendship) {
        deleteFriendship(friendship.getId());
    }

    @Override
    public void OnLoadFriendshipError(String message) {
        view.showErrorMessage(message);
    }

    @Override
    public void OnDeleteFriendshipSuccess() {
        view.showErrorMessage("Bien");
    }

    @Override
    public void OnDeleteFriendshipError(String message) {
        view.showErrorMessage(message);
    }
}
