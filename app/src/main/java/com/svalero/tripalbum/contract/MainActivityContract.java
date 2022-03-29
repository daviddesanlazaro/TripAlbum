package com.svalero.tripalbum.contract;

import android.content.Context;

import com.svalero.tripalbum.domain.Country;
import com.svalero.tripalbum.domain.Place;
import com.svalero.tripalbum.domain.Province;
import com.svalero.tripalbum.domain.Visit;

import java.util.List;

public interface MainActivityContract {

    interface Model {
        List<Country> loadCountries(Context context);
        List<Province> loadProvinces(Context context, int idCountry);
        List<Place> loadPlaces(Context context, int idProvince);
        List<Visit> loadVisits(Context context, int idPlace);
        void deleteVisit(Context context, Visit visit);
    }

    interface View {
        void listCountries(List<Country> countries);
        void listProvinces(List<Province> provinces);
        void listPlaces(List<Place> places);
        void listVisits(List<Visit> visits);
    }

    interface Presenter {
        void loadCountries();
        void loadProvinces(int idCountry);
        void loadPlaces(int idProvince);
        void loadVisits(int idPlace);
        void deleteVisit(Visit visit);
    }
}
