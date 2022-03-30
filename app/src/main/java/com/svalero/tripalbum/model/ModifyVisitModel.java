package com.svalero.tripalbum.model;

import android.content.Context;

import androidx.room.Room;

import com.svalero.tripalbum.api.TripAlbumApi;
import com.svalero.tripalbum.api.TripAlbumApiInterface;
import com.svalero.tripalbum.contract.ModifyVisitContract;
import com.svalero.tripalbum.database.AppDatabase;
import com.svalero.tripalbum.domain.Visit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModifyVisitModel implements ModifyVisitContract.Model {

    @Override
    public void addVisit(Context context, Visit visit) {
        AppDatabase db = Room.databaseBuilder(context,
                AppDatabase.class, "visits").allowMainThreadQueries().build();
        db.visitDao().insert(visit);
    }

    @Override
    public void addVisit(OnAddVisitListener listener, Visit visit) {
        TripAlbumApiInterface api = TripAlbumApi.buildInstance();
        Call<Visit> callVisits = api.addVisit(visit);
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
    public void modifyVisit(Context context, Visit visit) {
        AppDatabase db = Room.databaseBuilder(context,
                AppDatabase.class, "visits").allowMainThreadQueries().build();
        db.visitDao().update(visit);
    }

    @Override
    public void deleteVisit(Context context, Visit visit) {
        AppDatabase db = Room.databaseBuilder(context,
                AppDatabase.class, "visits").allowMainThreadQueries().build();
        db.visitDao().delete(visit);
    }
}
