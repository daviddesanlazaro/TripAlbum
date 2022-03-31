package com.svalero.tripalbum.model;

import com.svalero.tripalbum.api.TripAlbumApi;
import com.svalero.tripalbum.api.TripAlbumApiInterface;
import com.svalero.tripalbum.contract.FriendsListContract;
import com.svalero.tripalbum.domain.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendsListModel implements FriendsListContract.Model {

    @Override
    public void loadFriends(OnLoadFriendsListener listener, int userId) {
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
}
