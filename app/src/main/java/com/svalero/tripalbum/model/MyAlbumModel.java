package com.svalero.tripalbum.model;

import android.content.Context;

import androidx.room.Room;

import com.svalero.tripalbum.api.TripAlbumApi;
import com.svalero.tripalbum.api.TripAlbumApiInterface;
import com.svalero.tripalbum.contract.MyAlbumContract;
import com.svalero.tripalbum.database.AppDatabase;
import com.svalero.tripalbum.domain.Place;
import com.svalero.tripalbum.domain.Visit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAlbumModel implements MyAlbumContract.Model {

    @Override
    public void loadPlace(OnLoadPlaceListener listener, String placeId) {
        TripAlbumApiInterface api = TripAlbumApi.buildInstance();
        Call<Place> callPlace = api.getPlace(placeId);
        callPlace.enqueue(new Callback<Place>() {
            @Override
            public void onResponse(Call<Place> call, Response<Place> response) {
                listener.OnLoadPlaceSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Place> call, Throwable t) {
                listener.OnLoadPlaceError("Se ha producido un error");
            }
        });
    }

    @Override
    public void loadVisited(OnLoadVisitedListener listener, String userId) {
        TripAlbumApiInterface api = TripAlbumApi.buildInstance();
        Call<List<Visit>> callVisited = api.getVisited(userId);
        callVisited.enqueue(new Callback<List<Visit>>() {
            @Override
            public void onResponse(Call<List<Visit>> call, Response<List<Visit>> response) {
                listener.OnLoadVisitedSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<Visit>> call, Throwable t) {
                listener.OnLoadVisitedError("Se ha producido un error");
            }
        });
    }

    @Override
    public List<Place> loadFavorites(Context context) {
        AppDatabase db = Room.databaseBuilder(context,
                AppDatabase.class, "places").allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();
        return db.placeDao().getAll();
    }
}
