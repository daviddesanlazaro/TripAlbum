package com.svalero.tripalbum.model;

import android.content.Context;

import androidx.room.Room;

import com.svalero.tripalbum.contract.NewProvinceContract;
import com.svalero.tripalbum.database.AppDatabase;
import com.svalero.tripalbum.domain.Country;
import com.svalero.tripalbum.domain.Province;

import java.util.List;

public class NewProvinceModel implements NewProvinceContract.Model {
    @Override
    public List<Country> loadCountries(Context context) {
        AppDatabase db = Room.databaseBuilder(context,
                AppDatabase.class, "countries").allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();
        return db.countryDao().getAll();
    }

    @Override
    public void addProvince(Context context, Province province) {
        AppDatabase db = Room.databaseBuilder(context,
                AppDatabase.class, "provinces").allowMainThreadQueries().build();
        db.provinceDao().insert(province);
    }
}
