package com.svalero.tripalbum.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.svalero.tripalbum.dao.FriendDao;
import com.svalero.tripalbum.dao.PlaceDao;
import com.svalero.tripalbum.dao.UserDao;
import com.svalero.tripalbum.domain.Friend;
import com.svalero.tripalbum.domain.Place;
import com.svalero.tripalbum.domain.User;

@Database(entities = {Place.class, User.class, Friend.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PlaceDao placeDao();
    public abstract UserDao userDao();
    public abstract FriendDao friendDao();
}
