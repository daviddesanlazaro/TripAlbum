package com.svalero.tripalbum.contract;

import android.content.Context;

import com.svalero.tripalbum.domain.Place;

import java.util.List;

public interface ViewPlaceContract {

    interface Model {
        void addFavorite(Place place, Context context);
        void deleteFavorite(Place place, Context context);
        List<Place> loadFavorites(Context context);
    }

    interface View {
        void showErrorMessage(String message);
    }

    interface Presenter {
        void addFavorite(Place place);
        void deleteFavorite(Place place);
        void checkFavorite(Place place);
        void openNewVisit(Place place);
        void openViewMap(Place place);
    }
}
