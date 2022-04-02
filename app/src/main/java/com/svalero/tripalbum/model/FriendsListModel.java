package com.svalero.tripalbum.model;

import com.svalero.tripalbum.api.TripAlbumApi;
import com.svalero.tripalbum.api.TripAlbumApiInterface;
import com.svalero.tripalbum.contract.FriendsListContract;
import com.svalero.tripalbum.domain.Friendship;
import com.svalero.tripalbum.domain.FriendshipDTO;
import com.svalero.tripalbum.domain.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendsListModel implements FriendsListContract.Model {

    @Override
    public void loadFriends(OnLoadFriendsListener listener, long userId) {
        TripAlbumApiInterface api = TripAlbumApi.buildInstance();
        Call<List<User>> callFriends = api.getFriends(userId);
        callFriends.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                listener.OnLoadFriendsSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                listener.OnLoadFriendsError("Se ha producido un error");
            }
        });
    }

    @Override
    public void searchFriends(OnLoadSearchListener listener, long userId, String phone) {
        TripAlbumApiInterface api = TripAlbumApi.buildInstance();
        Call<List<User>> callUsers = api.getUsers(userId, phone);
        callUsers.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                listener.OnLoadSearchSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                listener.OnLoadSearchError("Se ha producido un error");
            }
        });
    }

    @Override
    public void addFriend(OnAddFriendListener listener, FriendshipDTO friendshipDTO) {
        TripAlbumApiInterface api = TripAlbumApi.buildInstance();
        Call<Friendship> callUsers = api.addFriend(friendshipDTO);
        callUsers.enqueue(new Callback<Friendship>() {
            @Override
            public void onResponse(Call<Friendship> call, Response<Friendship> response) {
                listener.OnAddFriendSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Friendship> call, Throwable t) {
                listener.OnAddFriendError("Se ha producido un error");
            }
        });
    }

    @Override
    public void loadFriendship(OnLoadFriendshipListener listener, long userId, long friendId) {
        TripAlbumApiInterface api = TripAlbumApi.buildInstance();
        Call<Friendship> callFriendship = api.getFriendship(userId, friendId);
        callFriendship.enqueue(new Callback<Friendship>() {
            @Override
            public void onResponse(Call<Friendship> call, Response<Friendship> response) {
                listener.OnLoadFriendshipSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Friendship> call, Throwable t) {
                listener.OnLoadFriendshipError("Se ha producido un error");
            }
        });
    }

    @Override
    public void deleteFriendship(OnDeleteFriendshipListener listener, long friendshipId) {
        TripAlbumApiInterface api = TripAlbumApi.buildInstance();
        Call<Void> callFriendship = api.deleteFriendship(friendshipId);
        callFriendship.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                listener.OnDeleteFriendshipSuccess();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                listener.OnDeleteFriendshipError("Se ha producido un error");
            }
        });
    }
}
