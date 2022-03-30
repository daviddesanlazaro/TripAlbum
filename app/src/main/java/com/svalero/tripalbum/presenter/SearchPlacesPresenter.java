package com.svalero.tripalbum.presenter;

import com.svalero.tripalbum.contract.SearchPlacesContract;
import com.svalero.tripalbum.domain.Place;
import com.svalero.tripalbum.domain.Province;
import com.svalero.tripalbum.model.SearchPlacesModel;
import com.svalero.tripalbum.view.SearchPlacesView;

import java.util.List;

public class SearchPlacesPresenter implements SearchPlacesContract.Presenter, SearchPlacesContract.Model.OnLoadProvincesListener, SearchPlacesContract.Model.OnLoadPlacesListener {

    private SearchPlacesView view;
    private SearchPlacesModel model;

    public SearchPlacesPresenter(SearchPlacesView view) {
        model = new SearchPlacesModel();
        this.view = view;
    }

    @Override
    public void loadProvinces(int countryId) {
        model.loadProvinces(this, countryId);
    }

    @Override
    public void loadPlaces(int provinceId, String name) {
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
}
