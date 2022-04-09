package com.svalero.tripalbum.presenter;

import android.content.Intent;

import com.svalero.tripalbum.contract.ViewPlaceContract;
import com.svalero.tripalbum.domain.Place;
import com.svalero.tripalbum.model.ViewPlaceModel;
import com.svalero.tripalbum.view.NewVisitView;
import com.svalero.tripalbum.view.PlaceMapView;
import com.svalero.tripalbum.view.ViewPlaceView;

import java.util.List;

public class ViewPlacePresenter implements ViewPlaceContract.Presenter {

    private final ViewPlaceModel model;
    private final ViewPlaceView view;

    public ViewPlacePresenter(ViewPlaceView view) {
        model = new ViewPlaceModel();
        this.view = view;
    }

    @Override
    public void addFavorite(Place place) {
        model.addFavorite(place, view);
    }

    @Override
    public void deleteFavorite(Place place) {
        model.deleteFavorite(place, view);
    }

    @Override
    public void checkFavorite(Place place) {
        List<Place> favorites = model.loadFavorites(view);
        if (favorites.contains(place))
            view.showDelete();
        else
            view.showAdd();
    }

    @Override
    public void openNewVisit(Place place) {
        Intent intent = new Intent(view, NewVisitView.class);
        intent.putExtra("place", place);
        intent.putExtra("ACTION", "POST");
        view.startActivity(intent);
    }

    @Override
    public void openViewMap(Place place) {
        Intent intent = new Intent(view, PlaceMapView.class);
        intent.putExtra("place", place);
        view.startActivity(intent);
    }
}
