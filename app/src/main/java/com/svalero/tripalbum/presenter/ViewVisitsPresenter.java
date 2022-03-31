package com.svalero.tripalbum.presenter;

import android.content.Intent;

import com.svalero.tripalbum.contract.ViewVisitsContract;
import com.svalero.tripalbum.domain.Place;
import com.svalero.tripalbum.domain.Visit;
import com.svalero.tripalbum.model.ViewVisitsModel;
import com.svalero.tripalbum.view.ViewVisitsView;
import com.svalero.tripalbum.view.NewVisitView;

import java.util.List;

public class ViewVisitsPresenter implements ViewVisitsContract.Presenter, ViewVisitsContract.Model.OnLoadVisitsListener {

    private ViewVisitsModel model;
    private ViewVisitsView view;

    public ViewVisitsPresenter(ViewVisitsView view) {
        model = new ViewVisitsModel();
        this.view = view;
    }

    @Override
    public void loadVisits(int userId, int placeId) {
        model.loadVisits(this, userId, placeId);
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
    public void OnLoadVisitsSuccess(List<Visit> visits) {
        view.listVisits(visits);
    }

    @Override
    public void OnLoadVisitsError(String message) {
        view.showErrorMessage(message);
    }
}
