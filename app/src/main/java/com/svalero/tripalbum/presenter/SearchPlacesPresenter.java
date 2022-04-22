package com.svalero.tripalbum.presenter;

import android.content.Intent;

import com.svalero.tripalbum.contract.SearchPlacesContract;
import com.svalero.tripalbum.domain.Place;
import com.svalero.tripalbum.domain.Province;
import com.svalero.tripalbum.domain.User;
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
    public void loadProvinces() {
        model.loadProvinces(this);
    }

    @Override
    public void loadPlaces(String provinceId, String name) {
        if (provinceId == null)
            model.loadAllPlaces(this, name);
        else
            model.loadPlaces(this, provinceId, name);
    }

    @Override
    public void loadFavorites(String provinceId, String name) {
        if ((provinceId == null) && (name == null))
            view.listPlaces(model.loadAllFavorites(view));
        if ((provinceId == null) && (name != null))
            view.listPlaces(model.loadFavoritesByName(view, name));
        if ((provinceId != null) && (name == null))
            view.listPlaces(model.loadFavoritesByProvince(view, provinceId));
        if ((provinceId != null) && (name != null))
            view.listPlaces(model.loadFavoritesByProvinceAndName(view, provinceId, name));

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
    public void openViewPlace(Place place, User user) {
        Intent intent = new Intent(view, ViewPlaceView.class);
        intent.putExtra("place", place);
        intent.putExtra("user", user);
        view.startActivity(intent);
    }
}
