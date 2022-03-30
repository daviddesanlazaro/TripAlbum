package com.svalero.tripalbum.contract;

import android.content.Context;

import com.svalero.tripalbum.domain.Country;
import com.svalero.tripalbum.domain.Visit;

import java.util.List;

public interface ModifyVisitContract {

    interface Model {
        interface OnAddVisitListener {
            void OnAddVisitSuccess(Visit visit);
            void OnAddVisitError(String message);
        }
        void addVisit(Context context, Visit visit);
        void modifyVisit(Context context, Visit visit);
        void deleteVisit(Context context, Visit visit);

        void addVisit(OnAddVisitListener listener, Visit visit);
    }

    interface View {
        void modifyVisit(android.view.View view);
        void showErrorMessage(String message);
    }

    interface Presenter {
        void addVisit(Visit visit, boolean modify);
        void deleteVisit(Visit visit);
    }
}
