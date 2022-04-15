package com.svalero.tripalbum.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.svalero.tripalbum.dao.FriendDao;
import com.svalero.tripalbum.dao.PlaceDao;
import com.svalero.tripalbum.domain.Friend;
import com.svalero.tripalbum.domain.Place;

@Database(entities = {Place.class, Friend.class}, version = 4)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PlaceDao placeDao();
    public abstract FriendDao friendDao();
}
