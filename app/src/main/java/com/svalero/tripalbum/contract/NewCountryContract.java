package com.svalero.tripalbum.contract;

import android.content.Context;

import com.svalero.tripalbum.domain.Country;

public interface NewCountryContract {

    interface Model {
        void addCountry(Context context, Country country);
    }

    interface View {

    }

    interface Presenter {
        void addCountry(Country country);
    }
}
