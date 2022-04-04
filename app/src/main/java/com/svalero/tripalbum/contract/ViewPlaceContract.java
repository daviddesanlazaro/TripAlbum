package com.svalero.tripalbum.contract;

import com.svalero.tripalbum.domain.Favorite;
import com.svalero.tripalbum.domain.FavoriteDTO;
import com.svalero.tripalbum.domain.Place;

public interface ViewPlaceContract {

    interface Model {
        interface OnAddFavoriteListener {
            void OnAddFavoriteSuccess(Favorite favorite);
            void OnAddFavoriteError(String message);
        }
        void addFavorite(OnAddFavoriteListener listener, FavoriteDTO favoriteDto);
    }

    interface View {
        void showErrorMessage(String message);
    }

    interface Presenter {
        void addFavorite(FavoriteDTO favoriteDto);
        void openNewVisit(Place place);
        void openViewMap(Place place);
    }
}
