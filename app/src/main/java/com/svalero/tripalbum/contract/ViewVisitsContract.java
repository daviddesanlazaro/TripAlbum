package com.svalero.tripalbum.contract;

import com.svalero.tripalbum.domain.Place;
import com.svalero.tripalbum.domain.User;
import com.svalero.tripalbum.domain.Visit;

import java.util.List;

public interface ViewVisitsContract {

    interface Model {
        interface OnLoadVisitsListener {
            void OnLoadVisitsSuccess(List<Visit> visits);
            void OnLoadVisitsError(String message);
        }
        interface OnDeleteVisitsListener {
            void OnDeleteVisitsSuccess();
            void OnDeleteVisitsError(String message);
        }
        void loadVisits(OnLoadVisitsListener listener, String userId, String placeId);
        void deleteVisit(OnDeleteVisitsListener listener, String visitId);
    }

    interface View {
        void listVisits(List<Visit> visits);

        void showErrorMessage(String message);
    }

    interface Presenter {
        void loadVisits(String userId, String placeId);
        void deleteVisit(String visitId);

        void openNewVisit(Place place, User user, Visit visit, String action);
    }
}
