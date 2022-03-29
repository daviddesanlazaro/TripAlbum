package com.svalero.tripalbum.model;

import android.content.Context;

import androidx.room.Room;

import com.svalero.tripalbum.contract.NewCountryContract;
import com.svalero.tripalbum.database.AppDatabase;
import com.svalero.tripalbum.domain.Country;

public class NewCountryModel implements NewCountryContract.Model {
    @Override
    public void addCountry(Context context, Country country) {
        AppDatabase db = Room.databaseBuilder(context,
                AppDatabase.class, "countries").allowMainThreadQueries().build();
        db.countryDao().insert(country);
    }
}
