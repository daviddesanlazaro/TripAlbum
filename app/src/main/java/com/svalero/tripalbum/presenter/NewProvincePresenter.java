package com.svalero.tripalbum.presenter;

import com.svalero.tripalbum.contract.NewProvinceContract;
import com.svalero.tripalbum.domain.Province;
import com.svalero.tripalbum.model.NewProvinceModel;
import com.svalero.tripalbum.view.NewProvinceActivityView;

public class NewProvincePresenter implements NewProvinceContract.Presenter {

    private NewProvinceModel model;
    private NewProvinceActivityView view;

    public NewProvincePresenter(NewProvinceActivityView view) {
        model = new NewProvinceModel();
        this.view = view;
    }

    @Override
    public void loadCountries() {
        view.listCountries(model.loadCountries(view.getApplicationContext()));
    }

    @Override
    public void addProvince(Province province) {
        model.addProvince(view.getApplicationContext(), province);
    }
}
