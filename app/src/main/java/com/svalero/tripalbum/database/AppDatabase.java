package com.svalero.tripalbum.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.svalero.tripalbum.dao.CountryDao;
import com.svalero.tripalbum.dao.PlaceDao;
import com.svalero.tripalbum.dao.ProvinceDao;
import com.svalero.tripalbum.domain.Country;
import com.svalero.tripalbum.domain.Place;
import com.svalero.tripalbum.domain.Province;

@Database(entities = {Country.class, Province.class, Place.class}, version = 5)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CountryDao countryDao();
    public abstract ProvinceDao provinceDao();
    public abstract PlaceDao placeDao();
}
