package com.svalero.tripalbum.presenter;

import android.content.Intent;

import com.svalero.tripalbum.contract.ViewVisitsContract;
import com.svalero.tripalbum.domain.Place;
import com.svalero.tripalbum.domain.User;
import com.svalero.tripalbum.domain.Visit;
import com.svalero.tripalbum.model.ViewVisitsModel;
import com.svalero.tripalbum.view.ViewVisitsView;
import com.svalero.tripalbum.view.NewVisitView;

import java.util.List;

public class ViewVisitsPresenter implements ViewVisitsContract.Presenter, ViewVisitsContract.Model.OnLoadVisitsListener, ViewVisitsContract.Model.OnDeleteVisitsListener {

    private final ViewVisitsModel model;
    private final ViewVisitsView view;

    public ViewVisitsPresenter(ViewVisitsView view) {
        model = new ViewVisitsModel();
        this.view = view;
    }

    @Override
    public void loadVisits(String userId, String placeId) {
        model.loadVisits(this, userId, placeId);
    }

    @Override
    public void deleteVisit(String visitId) {
        model.deleteVisit(this, visitId);
    }

    @Override
    public void openNewVisit(Place place, User user, Visit visit, String action) {
        Intent intent = new Intent(view, NewVisitView.class);
        intent.putExtra("place", place);
        intent.putExtra("user", user);
        intent.putExtra("visit", visit);
        intent.putExtra("ACTION", action);
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

    @Override
    public void OnDeleteVisitsSuccess() {
        view.showErrorMessage("Bien");
    }

    @Override
    public void OnDeleteVisitsError(String message) {
        view.showErrorMessage(message);
    }
}
