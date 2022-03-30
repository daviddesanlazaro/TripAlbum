package com.svalero.tripalbum.presenter;

import android.content.Intent;

import com.svalero.tripalbum.contract.ViewVisitsContract;
import com.svalero.tripalbum.domain.Country;
import com.svalero.tripalbum.domain.Place;
import com.svalero.tripalbum.domain.Province;
import com.svalero.tripalbum.domain.Visit;
import com.svalero.tripalbum.model.ViewVisitsModel;
import com.svalero.tripalbum.view.ViewVisitsView;
import com.svalero.tripalbum.view.NewVisitView;

import java.util.List;

public class ViewVisitsPresenter implements ViewVisitsContract.Presenter, ViewVisitsContract.Model.OnLoadCountriesListener,
        ViewVisitsContract.Model.OnLoadProvincesListener, ViewVisitsContract.Model.OnLoadPlacesListener, ViewVisitsContract.Model.OnLoadVisitsListener {

    private ViewVisitsModel model;
    private ViewVisitsView view;

    public ViewVisitsPresenter(ViewVisitsView view) {
        model = new ViewVisitsModel();
        this.view = view;
    }

    @Override
    public void loadAllCountries() {
        model.loadAllCountries(this);
    }

    @Override
    public void loadProvinces(int countryId) {
        model.loadProvinces(this, countryId);
    }

    @Override
    public void loadPlaces(int provinceId) {
        model.loadPlaces(this, provinceId);
    }

    @Override
    public void loadVisits(int placeId) {
        model.loadVisits(this, placeId);
    }

    @Override
    public void loadVisitsRoom(int placeId) {
        view.listVisits(model.loadVisits(view.getApplicationContext(), placeId));
    }

    @Override
    public void deleteVisit(Visit visit) {
        model.deleteVisit(view.getApplicationContext(), visit);
    }

    public void openModifyVisit(Visit visit, Place place) {
        Intent intent = new Intent(view, NewVisitView.class);
        intent.putExtra("modify", true);
        intent.putExtra("placeName", place.getName());
        intent.putExtra("Visit", visit);
        view.startActivity(intent);
    }

    public void openNewVisit(Place place) {
        Intent intent = new Intent(view, NewVisitView.class);
        intent.putExtra("modify", false);
        intent.putExtra("placeId", place.getId());
        intent.putExtra("placeName", place.getName());
        view.startActivity(intent);
    }

    @Override
    public void OnLoadCountriesSuccess(List<Country> countries) {
        view.listCountries(countries);
    }

    @Override
    public void OnLoadCountriesError(String message) {
        view.showErrorMessage(message);
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
    public void OnLoadVisitsSuccess(List<Visit> visits) {
        view.listVisits(visits);
    }

    @Override
    public void OnLoadVisitsError(String message) {
        view.showErrorMessage(message);
    }
}
