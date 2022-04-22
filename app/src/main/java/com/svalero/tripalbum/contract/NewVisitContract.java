package com.svalero.tripalbum.contract;

import com.svalero.tripalbum.api.Constants.Action;
import com.svalero.tripalbum.domain.Visit;
import com.svalero.tripalbum.domain.VisitDTO;

public interface NewVisitContract {

    interface Model {
        interface OnAddVisitListener {
            void OnAddVisitSuccess(Visit visit);
            void OnAddVisitError(String message);
        }
        interface OnModifyVisitListener {
            void OnModifyVisitSuccess(Visit visit);
            void OnModifyVisitError(String message);
        }
        void modifyVisit(OnModifyVisitListener listener, String visitId, VisitDTO visitDto);

        void addVisit(OnAddVisitListener listener, VisitDTO visitDto);
    }

    interface View {
        void modifyVisit(android.view.View view);
        void showErrorMessage(String message);
    }

    interface Presenter {
        void addVisit(Visit visit, Action action);
    }
}
