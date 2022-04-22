package com.svalero.tripalbum.model;

import android.content.Context;

import androidx.room.Room;

import com.svalero.tripalbum.api.TripAlbumApi;
import com.svalero.tripalbum.api.TripAlbumApiInterface;
import com.svalero.tripalbum.contract.SearchPlacesContract;
import com.svalero.tripalbum.database.AppDatabase;
import com.svalero.tripalbum.domain.Place;
import com.svalero.tripalbum.domain.Province;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchPlacesModel implements SearchPlacesContract.Model {

    @Override
    public void loadProvinces(SearchPlacesContract.Model.OnLoadProvincesListener listener) {
        TripAlbumApiInterface api = TripAlbumApi.buildInstance();
        Call<List<Province>> callProvinces = api.getProvinces();
        callProvinces.enqueue(new Callback<List<Province>>() {
            @Override
            public void onResponse(Call<List<Province>> call, Response<List<Province>> response) {
                listener.OnLoadProvincesSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<Province>> call, Throwable t) {
                listener.OnLoadProvincesError("Se ha producido un error");
            }
        });
    }

    @Override
    public void loadPlaces(SearchPlacesContract.Model.OnLoadPlacesListener listener, String provinceId, String name) {
        TripAlbumApiInterface api = TripAlbumApi.buildInstance();
        Call<List<Place>> callPlaces = api.getPlaces(provinceId, name);
        callPlaces.enqueue(new Callback<List<Place>>() {
            @Override
            public void onResponse(Call<List<Place>> call, Response<List<Place>> response) {
                listener.OnLoadPlacesSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<Place>> call, Throwable t) {
                listener.OnLoadPlacesError("Se ha producido un error");
            }
        });
    }

    @Override
    public void loadAllPlaces(OnLoadAllPlacesListener listener, String name) {
        TripAlbumApiInterface api = TripAlbumApi.buildInstance();
        Call<List<Place>> callPlaces = api.getAllPlaces(name);
        callPlaces.enqueue(new Callback<List<Place>>() {
            @Override
            public void onResponse(Call<List<Place>> call, Response<List<Place>> response) {
                listener.OnLoadAllPlacesSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<Place>> call, Throwable t) {
                listener.OnLoadAllPlacesError("Se ha producido un error");
            }
        });
    }

    @Override
    public List<Place> loadAllFavorites(Context context) {
        AppDatabase db = Room.databaseBuilder(context,
                AppDatabase.class, "places").allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();
        return db.placeDao().getAll();
    }

    @Override
    public List<Place> loadFavoritesByName(Context context, String name) {
        AppDatabase db = Room.databaseBuilder(context,
                AppDatabase.class, "places").allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();
        return db.placeDao().getByName(name);
    }

    @Override
    public List<Place> loadFavoritesByProvince(Context context, String provinceId) {
        AppDatabase db = Room.databaseBuilder(context,
                AppDatabase.class, "places").allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();
        return db.placeDao().getByProvince(provinceId);
    }

    @Override
    public List<Place> loadFavoritesByProvinceAndName(Context context, String provinceId, String name) {
        AppDatabase db = Room.databaseBuilder(context,
                AppDatabase.class, "places").allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();
        return db.placeDao().getByProvinceAndName(provinceId, name);
    }
}
