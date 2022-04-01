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

    private final ViewVisitsModel model;
    private final ViewVisitsView view;

    public ViewVisitsPresenter(ViewVisitsView view) {
        model = new ViewVisitsModel();
        this.view = view;
    }

    @Override
    public void loadVisits(long userId, long placeId) {
        model.loadVisits(this, userId, placeId);
    }

    @Override
    public void deleteVisit(long visitId) {

    }

    @Override
    public void openNewVisit(Place place, Visit visit, boolean modify) {
        Intent intent = new Intent(view, NewVisitView.class);
        intent.putExtra("place", place);
        intent.putExtra("visit", visit);
        intent.putExtra("modify", modify);
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
