package com.svalero.tripalbum.model;

import android.content.Context;

import androidx.room.Room;

import com.svalero.tripalbum.contract.MainActivityContract;
import com.svalero.tripalbum.database.AppDatabase;
import com.svalero.tripalbum.domain.Country;
import com.svalero.tripalbum.domain.Place;
import com.svalero.tripalbum.domain.Province;
import com.svalero.tripalbum.domain.Visit;

import java.util.List;

public class MainActivityModel implements MainActivityContract.Model {

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
    public List<Place> loadPlaces(Context context, int idProvince) {
        AppDatabase db = Room.databaseBuilder(context,
                AppDatabase.class, "places").allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();
        return db.placeDao().getPlacesByProvince(idProvince);
    }

    @Override
    public List<Visit> loadVisits(Context context, int idPlace) {
        AppDatabase db = Room.databaseBuilder(context,
                AppDatabase.class, "visits").allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();
        return db.visitDao().getVisitsByPlace(idPlace);
    }

    @Override
    public void deleteVisit(Context context, Visit visit) {
        AppDatabase db = Room.databaseBuilder(context,
                AppDatabase.class, "visits").allowMainThreadQueries().build();
        db.visitDao().delete(visit);
    }
}
