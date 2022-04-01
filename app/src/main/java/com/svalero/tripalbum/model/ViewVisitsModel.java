package com.svalero.tripalbum.model;

import com.svalero.tripalbum.api.TripAlbumApi;
import com.svalero.tripalbum.api.TripAlbumApiInterface;
import com.svalero.tripalbum.contract.ViewVisitsContract;
import com.svalero.tripalbum.domain.Visit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewVisitsModel implements ViewVisitsContract.Model {

    @Override
    public void loadVisits(OnLoadVisitsListener listener, long userId, long placeId) {
        TripAlbumApiInterface api = TripAlbumApi.buildInstance();
        Call<List<Visit>> callVisits = api.getVisits(userId, placeId);
        callVisits.enqueue(new Callback<List<Visit>>() {
            @Override
            public void onResponse(Call<List<Visit>> call, Response<List<Visit>> response) {
                listener.OnLoadVisitsSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<Visit>> call, Throwable t) {
                listener.OnLoadVisitsError("Se ha producido un error");
            }
        });
    }

    @Override
    public void deleteVisit(OnDeleteVisitsListener listener, long visitId) {
        TripAlbumApiInterface api = TripAlbumApi.buildInstance();
        Call<Void> callVisits = api.deleteVisit(visitId);
        callVisits.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                listener.OnDeleteVisitsSuccess();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                listener.OnDeleteVisitsError("Se ha producido un error");
            }
        });
    }
}
