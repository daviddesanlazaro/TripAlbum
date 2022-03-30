package com.svalero.tripalbum.presenter;

import com.svalero.tripalbum.contract.NewVisitContract;
import com.svalero.tripalbum.domain.Visit;
import com.svalero.tripalbum.model.NewVisitModel;
import com.svalero.tripalbum.view.NewVisitView;

public class NewVisitPresenter implements NewVisitContract.Presenter, NewVisitContract.Model.OnAddVisitListener {

    private NewVisitModel model;
    private NewVisitView view;

    public NewVisitPresenter(NewVisitView view) {
        model = new NewVisitModel();
        this.view = view;
    }

//    @Override
//    public void addVisit(Visit visit, boolean modify) {
//        if (modify)
//            model.modifyVisit(view.getApplicationContext(), visit);
//        else {
//            model.addVisit(view.getApplicationContext(), visit);
//            view.finish();
//        }
//    }

    @Override
    public void addVisit(Visit visit, boolean modify) {
        if (modify) {
            model.modifyVisit(view.getApplicationContext(), visit);
        }
        else {
            model.addVisit(this, visit);
            view.finish();
        }
    }

    @Override
    public void deleteVisit(Visit visit) {
        model.deleteVisit(view.getApplicationContext(), visit);
        view.finish();
    }

    @Override
    public void OnAddVisitSuccess(Visit visit) {
        view.showErrorMessage("Bien");
    }

    @Override
    public void OnAddVisitError(String message) {
        view.showErrorMessage(message);
    }
}
