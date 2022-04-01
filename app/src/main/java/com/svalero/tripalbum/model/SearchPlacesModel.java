package com.svalero.tripalbum.model;

import com.svalero.tripalbum.api.TripAlbumApi;
import com.svalero.tripalbum.api.TripAlbumApiInterface;
import com.svalero.tripalbum.contract.SearchPlacesContract;
import com.svalero.tripalbum.domain.Place;
import com.svalero.tripalbum.domain.Province;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchPlacesModel implements SearchPlacesContract.Model {

    @Override
    public void loadProvinces(SearchPlacesContract.Model.OnLoadProvincesListener listener, long countryId) {
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
    public void loadPlaces(SearchPlacesContract.Model.OnLoadPlacesListener listener, long provinceId, String name) {
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
}
