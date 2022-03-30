package com.svalero.tripalbum.contract;

import android.content.Context;

import com.svalero.tripalbum.domain.Country;
import com.svalero.tripalbum.domain.Place;
import com.svalero.tripalbum.domain.Province;
import com.svalero.tripalbum.domain.Visit;

import java.util.List;

public interface ViewVisitsContract {

    interface Model {
        interface OnLoadCountriesListener {
            void OnLoadCountriesSuccess(List<Country> countries);
            void OnLoadCountriesError(String message);
        }
        interface OnLoadProvincesListener {
            void OnLoadProvincesSuccess(List<Province> provinces);
            void OnLoadProvincesError(String message);
        }
        interface OnLoadPlacesListener {
            void OnLoadPlacesSuccess(List<Place> places);
            void OnLoadPlacesError(String message);
        }
        interface OnLoadVisitsListener {
            void OnLoadVisitsSuccess(List<Visit> visits);
            void OnLoadVisitsError(String message);
        }

        void loadAllCountries(OnLoadCountriesListener listener);
        void loadProvinces(OnLoadProvincesListener listener, int countryId);
        void loadPlaces(OnLoadPlacesListener listener, int provinceId);
        void loadVisits(OnLoadVisitsListener listener, int placeId);

        void deleteVisit(Context context, Visit visit);

        List<Visit> loadVisits(Context context, int placeId);
    }

    interface View {
        void listCountries(List<Country> countries);
        void listProvinces(List<Province> provinces);
        void listPlaces(List<Place> places);
        void listVisits(List<Visit> visits);

        void showErrorMessage(String message);
    }

    interface Presenter {
        void loadAllCountries();
        void loadProvinces(int countryId);
        void loadPlaces(int provinceId);
        void loadVisits(int placeId);
        void deleteVisit(Visit visit);

        void loadVisitsRoom(int placeId);
    }
}
