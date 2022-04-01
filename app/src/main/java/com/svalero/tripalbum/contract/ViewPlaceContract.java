package com.svalero.tripalbum.contract;

import com.svalero.tripalbum.domain.Favorite;
import com.svalero.tripalbum.domain.Place;

public interface ViewPlaceContract {

    interface Model {
        interface OnAddFavoriteListener {
            void OnAddFavoriteSuccess(Favorite favorite);
            void OnAddFavoriteError(String message);
        }
        void addFavorite(OnAddFavoriteListener listener, Favorite favorite);
    }

    interface View {
        void showErrorMessage(String message);
    }

    interface Presenter {
        void addFavorite(Favorite favorite);
        void openNewVisit(Place place);
    }
}
