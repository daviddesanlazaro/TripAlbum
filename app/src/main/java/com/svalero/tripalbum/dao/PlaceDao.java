package com.svalero.tripalbum.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.svalero.tripalbum.domain.Place;
import com.svalero.tripalbum.domain.Province;

import java.util.List;

@Dao
public interface PlaceDao {

    @Query("SELECT * FROM place")
    List<Place> getAll();

    @Query("SELECT * FROM place WHERE name LIKE :name")
    List<Place> getByName(String name);

    @Query("SELECT * FROM place WHERE province_id = :provinceId")
    List<Place> getByProvince(String provinceId);

    @Query("SELECT * FROM place WHERE name LIKE :name AND province_id = :provinceId")
    List<Place> getByProvinceAndName(String provinceId, String name);

    @Insert
    void insert(Place place);

    @Delete
    void delete(Place place);
}
