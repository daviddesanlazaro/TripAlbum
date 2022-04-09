package com.svalero.tripalbum.model;

import com.svalero.tripalbum.api.TripAlbumApi;
import com.svalero.tripalbum.api.TripAlbumApiInterface;
import com.svalero.tripalbum.contract.NewVisitContract;
import com.svalero.tripalbum.domain.Visit;
import com.svalero.tripalbum.domain.VisitDTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewVisitModel implements NewVisitContract.Model {

    @Override
    public void addVisit(OnAddVisitListener listener, VisitDTO visitDto) {
        TripAlbumApiInterface api = TripAlbumApi.buildInstance();
        Call<Visit> callVisits = api.addVisit(visitDto);
        callVisits.enqueue(new Callback<Visit>() {
            @Override
            public void onResponse(Call<Visit> call, Response<Visit> response) {
                listener.OnAddVisitSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Visit> call, Throwable t) {
                listener.OnAddVisitError("Se ha producido un error");
            }
        });
    }

    @Override
    public void modifyVisit(OnModifyVisitListener listener, String visitId, VisitDTO visitDto) {
        TripAlbumApiInterface api = TripAlbumApi.buildInstance();
        Call<Visit> callVisits = api.modifyVisit(visitId, visitDto);
        callVisits.enqueue(new Callback<Visit>() {
            @Override
            public void onResponse(Call<Visit> call, Response<Visit> response) {
                listener.OnModifyVisitSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Visit> call, Throwable t) {
                listener.OnModifyVisitError("Se ha producido un error");
            }
        });
    }
}
