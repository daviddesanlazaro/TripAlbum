package com.svalero.tripalbum.presenter;

import android.content.Intent;

import com.svalero.tripalbum.contract.ViewPlaceContract;
import com.svalero.tripalbum.domain.Favorite;
import com.svalero.tripalbum.domain.FavoriteDTO;
import com.svalero.tripalbum.domain.Place;
import com.svalero.tripalbum.model.ViewPlaceModel;
import com.svalero.tripalbum.view.NewVisitView;
import com.svalero.tripalbum.view.PlaceMapView;
import com.svalero.tripalbum.view.ViewPlaceView;

public class ViewPlacePresenter implements ViewPlaceContract.Presenter, ViewPlaceContract.Model.OnAddFavoriteListener {

    private final ViewPlaceModel model;
    private final ViewPlaceView view;

    public ViewPlacePresenter(ViewPlaceView view) {
        model = new ViewPlaceModel();
        this.view = view;
    }

    @Override
    public void addFavorite(FavoriteDTO favoriteDto) {
        model.addFavorite(this, favoriteDto);
    }

    @Override
    public void openNewVisit(Place place) {
        Intent intent = new Intent(view, NewVisitView.class);
        intent.putExtra("place", place);
        view.startActivity(intent);
    }

    @Override
    public void openViewMap(Place place) {
        Intent intent = new Intent(view, PlaceMapView.class);
        intent.putExtra("place", place);
        view.startActivity(intent);
    }

    @Override
    public void OnAddFavoriteSuccess(Favorite favorite) {
        view.showErrorMessage("Registrado con éxito");
    }

    @Override
    public void OnAddFavoriteError(String message) {
        view.showErrorMessage(message);
    }
}
