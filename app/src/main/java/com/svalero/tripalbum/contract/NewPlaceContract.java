package com.svalero.tripalbum.contract;

import android.content.Context;

import com.svalero.tripalbum.domain.Country;
import com.svalero.tripalbum.domain.Place;
import com.svalero.tripalbum.domain.Province;

import java.util.List;

public interface NewPlaceContract {

    interface Model {
        List<Country> loadCountries(Context context);
        List<Province> loadProvinces(Context context, int idCountry);
        void addPlace(Context context, Place place);
    }

    interface View {
        void listCountries(List<Country> countries);
        void listProvinces(List<Province> provinces);
    }

    interface Presenter {
        void loadCountries();
        void loadProvinces(int idCountry);
        void addPlace(Place place);
    }
}
