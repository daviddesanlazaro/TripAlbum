package com.svalero.tripalbum.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.svalero.tripalbum.domain.Friend;

import java.util.List;

@Dao
public interface FriendDao {
    @Query("SELECT * FROM friend")
    List<Friend> getAll();

    @Insert
    void insert(Friend friend);

    @Delete
    void delete(Friend friend);
}
