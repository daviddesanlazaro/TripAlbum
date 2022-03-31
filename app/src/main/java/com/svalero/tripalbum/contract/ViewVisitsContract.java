package com.svalero.tripalbum.contract;

import android.content.Context;

import com.svalero.tripalbum.domain.Visit;

import java.util.List;

public interface ViewVisitsContract {

    interface Model {
        interface OnLoadVisitsListener {
            void OnLoadVisitsSuccess(List<Visit> visits);
            void OnLoadVisitsError(String message);
        }
        void loadVisits(OnLoadVisitsListener listener, int userId, int placeId);

        void deleteVisit(Context context, Visit visit);
    }

    interface View {
        void listVisits(List<Visit> visits);

        void showErrorMessage(String message);
    }

    interface Presenter {
        void loadVisits(int userId, int placeId);
        void deleteVisit(Visit visit);
    }
}
