package com.svalero.tripalbum.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.svalero.tripalbum.domain.Place;

import java.util.List;

@Dao
public interface PlaceDao {
    @Query("SELECT * FROM place")
    List<Place> getAll();

    @Query("SELECT * FROM place WHERE provinceId = :idProvince")
    List<Place> getPlacesByProvince(int idProvince);

    @Insert
    void insert(Place place);
}
