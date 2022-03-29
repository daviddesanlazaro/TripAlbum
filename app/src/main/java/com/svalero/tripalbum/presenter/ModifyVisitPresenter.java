package com.svalero.tripalbum.presenter;

import com.svalero.tripalbum.contract.ModifyVisitContract;
import com.svalero.tripalbum.domain.Visit;
import com.svalero.tripalbum.model.ModifyVisitModel;
import com.svalero.tripalbum.view.ModifyVisitActivityView;

public class ModifyVisitPresenter implements ModifyVisitContract.Presenter {

    private ModifyVisitModel model;
    private ModifyVisitActivityView view;

    public ModifyVisitPresenter(ModifyVisitActivityView view) {
        model = new ModifyVisitModel();
        this.view = view;
    }

    @Override
    public void addVisit(Visit visit, boolean modify) {
        if (modify)
            model.modifyVisit(view.getApplicationContext(),  visit);
        else {
            model.addVisit(view.getApplicationContext(), visit);
            view.finish();
        }
    }

    @Override
    public void deleteVisit(Visit visit) {
        model.deleteVisit(view.getApplicationContext(), visit);
        view.finish();
    }
}
