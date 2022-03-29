package com.svalero.tripalbum.presenter;

import com.svalero.tripalbum.contract.NewCountryContract;
import com.svalero.tripalbum.domain.Country;
import com.svalero.tripalbum.model.NewCountryModel;
import com.svalero.tripalbum.view.NewCountryActivityView;

public class NewCountryPresenter implements NewCountryContract.Presenter {

    private NewCountryModel model;
    private NewCountryActivityView view;

    public NewCountryPresenter(NewCountryActivityView view) {
        model = new NewCountryModel();
        this.view = view;
    }

    @Override
    public void addCountry(Country country) {
        model.addCountry(view.getApplicationContext(), country);
    }
}
