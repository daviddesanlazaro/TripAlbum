package com.svalero.tripalbum.presenter;

import static com.svalero.tripalbum.api.Constants.Action.PUT;

import com.svalero.tripalbum.api.Constants.Action;
import com.svalero.tripalbum.contract.NewVisitContract;
import com.svalero.tripalbum.domain.Visit;
import com.svalero.tripalbum.domain.VisitDTO;
import com.svalero.tripalbum.model.NewVisitModel;
import com.svalero.tripalbum.view.NewVisitView;

public class NewVisitPresenter implements NewVisitContract.Presenter, NewVisitContract.Model.OnAddVisitListener, NewVisitContract.Model.OnModifyVisitListener {

    private final NewVisitModel model;
    private final NewVisitView view;

    public NewVisitPresenter(NewVisitView view) {
        model = new NewVisitModel();
        this.view = view;
    }

    @Override
    public void addVisit(Visit visit, Action action) {
        VisitDTO visitDto = new VisitDTO();
        visitDto.setUser(visit.getUser().getId());
        visitDto.setPlace(visit.getPlace().getId());
        visitDto.setDate(visit.getDate());
        visitDto.setRating(visit.getRating());
        visitDto.setCommentary(visit.getCommentary());
//        String image = new String(visit.getImage(), StandardCharsets.UTF_8);
//        visitDto.setImage(visit.getImage());

        if (action == PUT) {
            model.modifyVisit(this, visit.getId(), visitDto);
        }
        else {
            model.addVisit(this, visitDto);
        }
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

    @Override
    public void OnModifyVisitSuccess(Visit visit) {
        view.showErrorMessage("Bien");
    }

    @Override
    public void OnModifyVisitError(String message) {
        view.showErrorMessage(message);
    }
}
