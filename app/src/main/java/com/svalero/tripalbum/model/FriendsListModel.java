package com.svalero.tripalbum.model;

import android.content.Context;

import androidx.room.Room;

import com.svalero.tripalbum.api.TripAlbumApi;
import com.svalero.tripalbum.api.TripAlbumApiInterface;
import com.svalero.tripalbum.contract.FriendsListContract;
import com.svalero.tripalbum.database.AppDatabase;
import com.svalero.tripalbum.domain.Friend;
import com.svalero.tripalbum.domain.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendsListModel implements FriendsListContract.Model {

    @Override
    public List<Friend> loadFriends(Context context) {
        AppDatabase db = Room.databaseBuilder(context,
                AppDatabase.class, "friends").allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();
        return db.friendDao().getAll();
    }

    @Override
    public void loadUsers(OnLoadUsersListener listener, String phone) {
        TripAlbumApiInterface api = TripAlbumApi.buildInstance();
        Call<List<User>> callUsers = api.getByPhone(phone);
        callUsers.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                listener.OnLoadUsersSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                listener.OnLoadUsersError("Se ha producido un error");
            }
        });
    }

    @Override
    public void addFriend(Context context, Friend friend) {
        AppDatabase db = Room.databaseBuilder(context,
                AppDatabase.class, "friends").allowMainThreadQueries().build();
        db.friendDao().insert(friend);
    }

    @Override
    public void deleteFriend(Context context, Friend friend) {
        AppDatabase db = Room.databaseBuilder(context,
                AppDatabase.class, "friends").allowMainThreadQueries().build();
        db.friendDao().delete(friend);
    }
}
