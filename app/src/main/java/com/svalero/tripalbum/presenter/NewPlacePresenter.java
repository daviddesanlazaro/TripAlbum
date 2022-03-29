package com.svalero.tripalbum.presenter;

import com.svalero.tripalbum.contract.NewPlaceContract;
import com.svalero.tripalbum.domain.Place;
import com.svalero.tripalbum.model.NewPlaceModel;
import com.svalero.tripalbum.view.NewPlaceActivityView;

public class NewPlacePresenter implements NewPlaceContract.Presenter {

    private NewPlaceModel model;
    private NewPlaceActivityView view;

    public NewPlacePresenter(NewPlaceActivityView view) {
        model = new NewPlaceModel();
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
    public void addPlace(Place place) {
        model.addPlace(view.getApplicationContext(), place);
    }
}
