package com.svalero.tripalbum.contract;

import android.content.Context;

import com.svalero.tripalbum.domain.Place;
import com.svalero.tripalbum.domain.Visit;

import java.util.List;

public interface MyAlbumContract {

    interface Model {
        interface OnLoadPlaceListener {
            void OnLoadPlaceSuccess(Place place);
            void OnLoadPlaceError(String message);
        }
        interface OnLoadVisitedListener {
            void OnLoadVisitedSuccess(List<Visit> visits);
            void OnLoadVisitedError(String message);
        }

        void loadPlace(MyAlbumContract.Model.OnLoadPlaceListener listener, String placeId);
        void loadVisited(MyAlbumContract.Model.OnLoadVisitedListener listener, String userId);
        List<Place> loadFavorites(Context context);
    }

    interface View {
        void refreshVisited();
        void listFavorites(List<Place> places);
        void showErrorMessage(String message);
    }

    interface Presenter {
        void loadVisited(String userId);
        void loadFavorites();

        void openViewVisits(String userId, Place place);
        void openViewPlace(Place place);
    }
}
