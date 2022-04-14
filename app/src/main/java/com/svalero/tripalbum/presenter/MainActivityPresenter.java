package com.svalero.tripalbum.presenter;

import com.svalero.tripalbum.contract.MainActivityContract;
import com.svalero.tripalbum.domain.User;
import com.svalero.tripalbum.model.MainActivityModel;
import com.svalero.tripalbum.view.MainActivityView;

import java.util.List;

public class MainActivityPresenter implements MainActivityContract.Presenter {

    private final MainActivityView view;
    private final MainActivityModel model;

    public MainActivityPresenter(MainActivityView view) {
        model = new MainActivityModel();
        this.view = view;
    }

//    @Override
//    public void loadUsers() {
//        model.loadUsers(this);
//    }
//
//    @Override
//    public void OnLoadUsersSuccess(List<User> users) {
//        view.listUsers(users);
//    }
//
//    @Override
//    public void OnLoadUsersError(String message) {
//        view.showErrorMessage(message);
//    }
}
