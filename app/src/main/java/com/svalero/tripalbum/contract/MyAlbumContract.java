package com.svalero.tripalbum.contract;

import com.svalero.tripalbum.domain.Place;

import java.util.List;

public interface MyAlbumContract {

    interface Model {
        interface OnLoadVisitedListener {
            void OnLoadVisitedSuccess(List<Place> places);
            void OnLoadVisitedError(String message);
        }
        interface OnLoadInterestingListener {
            void OnLoadInterestingSuccess(List<Place> places);
            void OnLoadInterestingError(String message);
        }

        void loadVisited(MyAlbumContract.Model.OnLoadVisitedListener listener, long userId);
        void loadInteresting(MyAlbumContract.Model.OnLoadInterestingListener listener, long userId);
    }

    interface View {
        void listVisited(List<Place> places);
        void listInteresting(List<Place> places);
        void showErrorMessage(String message);
    }

    interface Presenter {
        void loadVisited(long userId);
        void loadInteresting(long userId);
        void openViewVisits(long userId, Place place);
    }
}
