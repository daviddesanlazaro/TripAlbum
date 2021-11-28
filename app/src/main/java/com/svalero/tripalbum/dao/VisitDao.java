package com.svalero.tripalbum.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.svalero.tripalbum.domain.Visit;

import java.util.List;

@Dao
public interface VisitDao {
    @Query("SELECT * FROM visit WHERE placeId = :idPlace")
    List<Visit> getVisitsByPlace(int idPlace);

    @Insert
    void insert(Visit visit);

    @Update
    void update(Visit visit);

    @Delete
    void delete(Visit visit);
}
