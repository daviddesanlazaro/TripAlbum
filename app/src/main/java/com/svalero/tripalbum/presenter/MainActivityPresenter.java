package com.svalero.tripalbum.presenter;

import android.content.Intent;

import com.svalero.tripalbum.contract.MainActivityContract;
import com.svalero.tripalbum.domain.Place;
import com.svalero.tripalbum.domain.Visit;
import com.svalero.tripalbum.model.MainActivityModel;
import com.svalero.tripalbum.view.MainActivityView;
import com.svalero.tripalbum.view.ModifyVisitActivityView;


public class MainActivityPresenter implements MainActivityContract.Presenter {

    private MainActivityModel model;
    private MainActivityView view;

    public MainActivityPresenter(MainActivityView view) {
        model = new MainActivityModel();
        this.view = view;
    }

    @Override
    public void loadCountries() {
        view.listCountries(model.loadCountries(view.getApplicationContext()));
    }

    @Override
    public void loadProvinces(int idCountry) {
        view.listProvinces(model.loadProvinces(view.getApplicationContext(), idCountry));
    }

    @Override
    public void loadPlaces(int idProvince) {
        view.listPlaces(model.loadPlaces(view.getApplicationContext(), idProvince));
    }

    @Override
    public void loadVisits(int idPlace) {
        view.listVisits(model.loadVisits(view.getApplicationContext(), idPlace));
    }

    @Override
    public void deleteVisit(Visit visit) {
        model.deleteVisit(view.getApplicationContext(), visit);
    }

    public void openModifyVisit(Visit visit, Place place) {
        Intent intent = new Intent(view, ModifyVisitActivityView.class);
        intent.putExtra("modify", true);
        intent.putExtra("placeName", place.getName());
        intent.putExtra("Visit", visit);
        view.startActivity(intent);
    }

    public void openNewVisit(Place place) {
        Intent intent = new Intent(view, ModifyVisitActivityView.class);
        intent.putExtra("modify", false);
        intent.putExtra("placeId", place.getId());
        intent.putExtra("placeName", place.getName());
        view.startActivity(intent);
    }
}
