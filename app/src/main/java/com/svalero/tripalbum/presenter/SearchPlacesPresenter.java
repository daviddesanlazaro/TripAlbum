package com.svalero.tripalbum.presenter;

import android.content.Intent;

import com.svalero.tripalbum.contract.SearchPlacesContract;
import com.svalero.tripalbum.domain.Place;
import com.svalero.tripalbum.domain.Province;
import com.svalero.tripalbum.model.SearchPlacesModel;
import com.svalero.tripalbum.view.SearchPlacesView;
import com.svalero.tripalbum.view.ViewPlaceView;

import java.util.List;

public class SearchPlacesPresenter implements SearchPlacesContract.Presenter, SearchPlacesContract.Model.OnLoadProvincesListener, SearchPlacesContract.Model.OnLoadPlacesListener,
        SearchPlacesContract.Model.OnLoadAllPlacesListener {

    private final SearchPlacesView view;
    private final SearchPlacesModel model;

    public SearchPlacesPresenter(SearchPlacesView view) {
        model = new SearchPlacesModel();
        this.view = view;
    }

    @Override
    public void loadProvinces(long countryId) {
        model.loadProvinces(this, countryId);
    }

    @Override
    public void loadPlaces(long provinceId, String name) {
        if (provinceId == 0)
            model.loadAllPlaces(this, name);
        else
            model.loadPlaces(this, provinceId, name);
    }

    @Override
    public void OnLoadProvincesSuccess(List<Province> provinces) {
        view.listProvinces(provinces);
    }

    @Override
    public void OnLoadProvincesError(String message) {
        view.showErrorMessage(message);
    }

    @Override
    public void OnLoadPlacesSuccess(List<Place> places) {
        view.listPlaces(places);
    }

    @Override
    public void OnLoadPlacesError(String message) {
        view.showErrorMessage(message);
    }

    @Override
    public void OnLoadAllPlacesSuccess(List<Place> places) {
        view.listPlaces(places);
    }

    @Override
    public void OnLoadAllPlacesError(String message) {
        view.showErrorMessage(message);
    }

    @Override
    public void openViewPlace(Place place) {
        Intent intent = new Intent(view, ViewPlaceView.class);
        intent.putExtra("place", place);
        view.startActivity(intent);
    }
}
