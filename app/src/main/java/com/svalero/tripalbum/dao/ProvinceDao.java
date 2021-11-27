package com.svalero.tripalbum.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.svalero.tripalbum.domain.Province;

import java.util.List;

@Dao
public interface ProvinceDao {

    @Query("SELECT * FROM province")
    List<Province> getAll();

    @Query("SELECT * FROM province WHERE countryId = :idCountry")
    List<Province> getProvincesByCountry(int idCountry);

    @Insert
    void insert(Province province);
}
