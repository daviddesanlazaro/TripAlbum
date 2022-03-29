package com.svalero.tripalbum.contract;

import android.content.Context;

import com.svalero.tripalbum.domain.Visit;

public interface ModifyVisitContract {

    interface Model {
        void addVisit(Context context, Visit visit);
        void modifyVisit(Context context, Visit visit);
        void deleteVisit(Context context, Visit visit);
    }

    interface View {

    }

    interface Presenter {
        void addVisit(Visit visit, boolean modify);
        void deleteVisit(Visit visit);
    }
}
