package com.svalero.tripalbum.model;

import com.svalero.tripalbum.api.TripAlbumApi;
import com.svalero.tripalbum.api.TripAlbumApiInterface;
import com.svalero.tripalbum.contract.ViewPlaceContract;
import com.svalero.tripalbum.domain.Favorite;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewPlaceModel implements ViewPlaceContract.Model {

    @Override
    public void addFavorite(OnAddFavoriteListener listener, Favorite favorite) {
        TripAlbumApiInterface api = TripAlbumApi.buildInstance();
        Call<Favorite> callFavorites = api.addFavorite(favorite);
        callFavorites.enqueue(new Callback<Favorite>() {
            @Override
            public void onResponse(Call<Favorite> call, Response<Favorite> response) {
                listener.OnAddFavoriteSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Favorite> call, Throwable t) {
                listener.OnAddFavoriteError("Se ha producido un error");
            }
        });
    }
}
