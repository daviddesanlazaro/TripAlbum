package com.svalero.tripalbum.model;

import android.content.Context;

import androidx.room.Room;

import com.svalero.tripalbum.api.TripAlbumApi;
import com.svalero.tripalbum.api.TripAlbumApiInterface;
import com.svalero.tripalbum.contract.ViewVisitsContract;
import com.svalero.tripalbum.database.AppDatabase;
import com.svalero.tripalbum.domain.Visit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewVisitsModel implements ViewVisitsContract.Model {

    @Override
    public void loadVisits(OnLoadVisitsListener listener, int userId, int placeId) {
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
    public void deleteVisit(Context context, Visit visit) {
        AppDatabase db = Room.databaseBuilder(context,
                AppDatabase.class, "visits").allowMainThreadQueries().build();
        db.visitDao().delete(visit);
    }
}
