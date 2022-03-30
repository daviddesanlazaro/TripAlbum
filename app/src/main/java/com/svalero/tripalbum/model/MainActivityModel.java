package com.svalero.tripalbum.model;

import android.content.Context;

import androidx.room.Room;

import com.svalero.tripalbum.api.TripAlbumApi;
import com.svalero.tripalbum.api.TripAlbumApiInterface;
import com.svalero.tripalbum.contract.MainActivityContract;
import com.svalero.tripalbum.database.AppDatabase;
import com.svalero.tripalbum.domain.Country;
import com.svalero.tripalbum.domain.Place;
import com.svalero.tripalbum.domain.Province;
import com.svalero.tripalbum.domain.Visit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityModel implements MainActivityContract.Model {

    @Override
    public void loadAllCountries(OnLoadCountriesListener listener) {
        TripAlbumApiInterface api = TripAlbumApi.buildInstance();
        Call<List<Country>> callCountries = api.getCountries();
        callCountries.enqueue(new Callback<List<Country>>() {
            @Override
            public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                listener.OnLoadCountriesSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<Country>> call, Throwable t) {
                listener.OnLoadCountriesError("Se ha producido un error");
            }
        });
    }

    @Override
    public void loadProvinces(OnLoadProvincesListener listener, int countryId) {
        TripAlbumApiInterface api = TripAlbumApi.buildInstance();
        Call<List<Province>> callProvinces = api.getProvinces(countryId);
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
    public void loadPlaces(OnLoadPlacesListener listener, int provinceId) {
        TripAlbumApiInterface api = TripAlbumApi.buildInstance();
        Call<List<Place>> callPlaces = api.getPlaces(provinceId);
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
    public void loadVisits(OnLoadVisitsListener listener, int placeId) {
        TripAlbumApiInterface api = TripAlbumApi.buildInstance();
        Call<List<Visit>> callVisits = api.getVisits(65, placeId);
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

    @Override
    public List<Visit> loadVisits(Context context, int placeId) {
        AppDatabase db = Room.databaseBuilder(context,
                AppDatabase.class, "visits").allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();
        return db.visitDao().getVisitsByPlace(placeId);
    }
}
