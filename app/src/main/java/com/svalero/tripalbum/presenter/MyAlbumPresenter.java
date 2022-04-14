package com.svalero.tripalbum.presenter;

import android.content.Intent;

import com.svalero.tripalbum.contract.MyAlbumContract;
import com.svalero.tripalbum.domain.Place;
import com.svalero.tripalbum.domain.Visit;
import com.svalero.tripalbum.model.MyAlbumModel;
import com.svalero.tripalbum.view.MyAlbumView;
import com.svalero.tripalbum.view.ViewPlaceView;
import com.svalero.tripalbum.view.ViewVisitsView;

import java.util.List;

public class MyAlbumPresenter implements MyAlbumContract.Presenter, MyAlbumContract.Model.OnLoadVisitedListener, MyAlbumContract.Model.OnLoadPlaceListener {

    private final MyAlbumView view;
    private final MyAlbumModel model;

    public MyAlbumPresenter(MyAlbumView view) {
        model = new MyAlbumModel();
        this.view = view;
    }

    @Override
    public void loadVisited(String userId) {
        model.loadVisited(this, userId);
    }

    @Override
    public void loadFavorites() {
        view.listFavorites(model.loadFavorites(view));
    }

    @Override
    public void openViewVisits(String userId, Place place, String action) {
        Intent intent = new Intent(view, ViewVisitsView.class);
        intent.putExtra("userId",userId);
        intent.putExtra("place", place);
        intent.putExtra("ACTION", action);
        view.startActivity(intent);
    }

    @Override
    public void openViewPlace(Place place) {
        Intent intent = new Intent(view, ViewPlaceView.class);
        intent.putExtra("place", place);
        view.startActivity(intent);
    }

    @Override
    public void OnLoadVisitedSuccess(List<Visit> visits) {
        for (int i = 0; i<visits.size(); i++) {
            model.loadPlace(this, visits.get(i).getPlace().getId());
        }
    }

    @Override
    public void OnLoadVisitedError(String message) {
        view.showErrorMessage(message);
    }

    @Override
    public void OnLoadPlaceSuccess(Place place) {
        if (!(view.visitedList.contains(place)))
            view.visitedList.add(place);
        view.refreshVisited();
    }

    @Override
    public void OnLoadPlaceError(String message) {
        view.showErrorMessage(message);
    }
}
