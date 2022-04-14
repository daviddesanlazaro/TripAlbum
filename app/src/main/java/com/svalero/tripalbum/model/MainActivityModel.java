package com.svalero.tripalbum.model;

import com.svalero.tripalbum.api.TripAlbumApi;
import com.svalero.tripalbum.api.TripAlbumApiInterface;
import com.svalero.tripalbum.contract.MainActivityContract;
import com.svalero.tripalbum.domain.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityModel implements MainActivityContract.Model {

//    @Override
//    public void loadUsers(OnLoadUsersListener listener) {
//        TripAlbumApiInterface api = TripAlbumApi.buildInstance();
//        Call<List<User>> callUsers = api.getUsers();
//        callUsers.enqueue(new Callback<List<User>>() {
//            @Override
//            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
//                listener.OnLoadUsersSuccess(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<List<User>> call, Throwable t) {
//                listener.OnLoadUsersError("Se ha producido un error");
//            }
//        });
//    }
}
