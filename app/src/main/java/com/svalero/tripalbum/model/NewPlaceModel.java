package com.svalero.tripalbum.model;

import android.content.Context;

import androidx.room.Room;

import com.svalero.tripalbum.contract.NewPlaceContract;
import com.svalero.tripalbum.database.AppDatabase;
import com.svalero.tripalbum.domain.Country;
import com.svalero.tripalbum.domain.Place;
import com.svalero.tripalbum.domain.Province;

import java.util.List;

public class NewPlaceModel implements NewPlaceContract.Model {
    @Override
    public List<Country> loadCountries(Context context) {
        AppDatabase db = Room.databaseBuilder(context,
                AppDatabase.class, "countries").allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();
        return db.countryDao().getAll();
    }

    @Override
    public List<Province> loadProvinces(Context context, int idCountry) {
        AppDatabase db = Room.databaseBuilder(context,
                AppDatabase.class, "provinces").allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();
        return db.provinceDao().getProvincesByCountry(idCountry);
    }

    @Override
    public void addPlace(Context context, Place place) {
        AppDatabase db = Room.databaseBuilder(context,
                AppDatabase.class, "places").allowMainThreadQueries().build();
        db.placeDao().insert(place);
    }
}
