package com.svalero.tripalbum.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.svalero.tripalbum.domain.Country;

import java.util.List;

@Dao
public interface CountryDao {

    @Query("SELECT * FROM country")
    List<Country> getAll();

    @Insert
    void insert(Country country);

}
