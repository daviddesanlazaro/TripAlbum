package com.svalero.tripalbum.contract;

import android.content.Context;

import com.svalero.tripalbum.domain.Country;
import com.svalero.tripalbum.domain.Province;

import java.util.List;

public interface NewProvinceContract {

    interface Model {
        List<Country> loadCountries(Context context);
        void addProvince(Context context, Province province);
    }

    interface View {
        void listCountries(List<Country> countries);
    }

    interface Presenter {
        void loadCountries();
        void addProvince(Province province);
    }
}
