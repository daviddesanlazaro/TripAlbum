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

        void loadProvinces(OnLoadProvincesListener listener);
        void loadPlaces(OnLoadPlacesListener listener, String provinceId, String name);
        void loadAllPlaces(OnLoadAllPlacesListener listener, String name);
    }

    interface View {
        void listProvinces(List<Province> provinces);
        void listPlaces(List<Place> places);
        void showErrorMessage(String message);
    }

    interface Presenter {
        void loadProvinces();
        void loadPlaces(String provinceId, String name);

        void openViewPlace(Place place);
    }
}
