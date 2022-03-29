package com.svalero.tripalbum.presenter;

import com.svalero.tripalbum.contract.ViewPlaceContract;
import com.svalero.tripalbum.view.ViewPlaceActivityView;

public class ViewPlacePresenter implements ViewPlaceContract.Presenter {

    private ViewPlaceActivityView view;

    public ViewPlacePresenter(ViewPlaceActivityView view) {
        this.view = view;
    }

}
