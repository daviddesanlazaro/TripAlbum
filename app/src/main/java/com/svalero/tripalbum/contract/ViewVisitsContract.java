package com.svalero.tripalbum.contract;

import com.svalero.tripalbum.domain.Place;
import com.svalero.tripalbum.domain.Visit;

import java.util.List;

public interface ViewVisitsContract {

    interface Model {
        interface OnLoadVisitsListener {
            void OnLoadVisitsSuccess(List<Visit> visits);
            void OnLoadVisitsError(String message);
        }
        void loadVisits(OnLoadVisitsListener listener, long userId, long placeId);
    }

    interface View {
        void listVisits(List<Visit> visits);

        void showErrorMessage(String message);
    }

    interface Presenter {
        void loadVisits(long userId, long placeId);
        void deleteVisit(long visitId);

        void openNewVisit(Place place, Visit visit, boolean modify);
    }
}
