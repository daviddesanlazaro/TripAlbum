package com.svalero.tripalbum.model;

import android.content.Context;

import androidx.room.Room;

import com.svalero.tripalbum.contract.ViewPlaceContract;
import com.svalero.tripalbum.database.AppDatabase;
import com.svalero.tripalbum.domain.Place;

import java.util.List;

public class ViewPlaceModel implements ViewPlaceContract.Model {

    @Override
    public void addFavorite(Place place, Context context) {
        AppDatabase db = Room.databaseBuilder(context,
                AppDatabase.class, "places").allowMainThreadQueries().build();
        db.placeDao().insert(place);
    }

    @Override
    public void deleteFavorite(Place place, Context context) {
        AppDatabase db = Room.databaseBuilder(context,
                AppDatabase.class, "places").allowMainThreadQueries().build();
        db.placeDao().delete(place);
    }

    @Override
    public List<Place> loadFavorites(Context context) {
        AppDatabase db = Room.databaseBuilder(context,
                AppDatabase.class, "places").allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();
        return db.placeDao().getAll();
    }
}
