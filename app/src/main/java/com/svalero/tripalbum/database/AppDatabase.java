package com.svalero.tripalbum.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.svalero.tripalbum.dao.CountryDao;
import com.svalero.tripalbum.dao.PlaceDao;
import com.svalero.tripalbum.dao.ProvinceDao;
import com.svalero.tripalbum.dao.VisitDao;
import com.svalero.tripalbum.domain.Country;
import com.svalero.tripalbum.domain.Place;
import com.svalero.tripalbum.domain.Province;
import com.svalero.tripalbum.domain.Visit;

@Database(entities = {Country.class, Province.class, Place.class, Visit.class}, version = 8)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CountryDao countryDao();
    public abstract ProvinceDao provinceDao();
    public abstract PlaceDao placeDao();
    public abstract VisitDao visitDao();
}
