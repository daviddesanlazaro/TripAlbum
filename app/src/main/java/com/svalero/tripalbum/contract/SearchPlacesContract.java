package com.svalero.tripalbum.contract;

import com.svalero.tripalbum.domain.Place;
import com.svalero.tripalbum.domain.Province;

import java.util.List;

public interface SearchPlacesContract {

    interface Model {
        interface OnLoadProvincesListener {
            void OnLoadProvincesSuccess(List<Province> provinces);
            void OnLoadProvincesError(String message);
        }
        interface OnLoadPlacesListener {
            void OnLoadPlacesSuccess(List<Place> places);
            void OnLoadPlacesError(String message);
        }
        interface OnLoadAllPlacesListener {
            void OnLoadAllPlacesSuccess(List<Place> places);
            void OnLoadAllPlacesError(String message);
        }

        void loadProvinces(OnLoadProvincesListener listener, long countryId);
        void loadPlaces(OnLoadPlacesListener listener, long provinceId, String name);
        void loadAllPlaces(OnLoadAllPlacesListener listener, String name);
    }

    interface View {
        void listProvinces(List<Province> provinces);
        void listPlaces(List<Place> places);
        void showErrorMessage(String message);
    }

    interface Presenter {
        void loadProvinces(long countryId);
        void loadPlaces(long provinceId, String name);

        void openNewVisit(long userId, Place place);
        void openViewPlace(Place place);
    }
}
