package com.svalero.tripalbum.presenter;

import android.content.Intent;

import com.svalero.tripalbum.contract.MyAlbumContract;
import com.svalero.tripalbum.domain.Place;
import com.svalero.tripalbum.model.MyAlbumModel;
import com.svalero.tripalbum.view.MyAlbumView;
import com.svalero.tripalbum.view.ViewVisitsView;

import java.util.List;

public class MyAlbumPresenter implements MyAlbumContract.Presenter, MyAlbumContract.Model.OnLoadVisitedListener, MyAlbumContract.Model.OnLoadInterestingListener {

    private MyAlbumView view;
    private MyAlbumModel model;

    public MyAlbumPresenter(MyAlbumView view) {
        model = new MyAlbumModel();
        this.view = view;
    }

    @Override
    public void loadVisited(int userId) {
        model.loadVisited(this, userId);
    }

    @Override
    public void loadInteresting(int userId) {
        model.loadInteresting(this, userId);
    }

    @Override
    public void openViewVisits(int userId, int placeId) {
        Intent intent = new Intent(view, ViewVisitsView.class);
        intent.putExtra("userId",userId);
        intent.putExtra("placeId", placeId);
        view.startActivity(intent);
    }

    @Override
    public void OnLoadVisitedSuccess(List<Place> places) {
        view.listVisited(places);
    }

    @Override
    public void OnLoadVisitedError(String message) {
        view.showErrorMessage(message);
    }

    @Override
    public void OnLoadInterestingSuccess(List<Place> places) {
        view.listInteresting(places);
    }

    @Override
    public void OnLoadInterestingError(String message) {
        view.showErrorMessage(message);
    }
}
