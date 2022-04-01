package com.svalero.tripalbum.model;

import com.svalero.tripalbum.api.TripAlbumApi;
import com.svalero.tripalbum.api.TripAlbumApiInterface;
import com.svalero.tripalbum.contract.MyAlbumContract;
import com.svalero.tripalbum.domain.Place;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAlbumModel implements MyAlbumContract.Model {

    @Override
    public void loadVisited(OnLoadVisitedListener listener, long userId) {
        TripAlbumApiInterface api = TripAlbumApi.buildInstance();
        Call<List<Place>> callVisited = api.getVisited(userId);
        callVisited.enqueue(new Callback<List<Place>>() {
            @Override
            public void onResponse(Call<List<Place>> call, Response<List<Place>> response) {
                listener.OnLoadVisitedSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<Place>> call, Throwable t) {
                listener.OnLoadVisitedError("Se ha producido un error");
            }
        });
    }

    @Override
    public void loadInteresting(OnLoadInterestingListener listener, long userId) {
        TripAlbumApiInterface api = TripAlbumApi.buildInstance();
        Call<List<Place>> callInteresting = api.getInteresting(userId);
        callInteresting.enqueue(new Callback<List<Place>>() {
            @Override
            public void onResponse(Call<List<Place>> call, Response<List<Place>> response) {
                listener.OnLoadInterestingSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<Place>> call, Throwable t) {
                listener.OnLoadInterestingError("Se ha producido un error");
            }
        });
    }
}
