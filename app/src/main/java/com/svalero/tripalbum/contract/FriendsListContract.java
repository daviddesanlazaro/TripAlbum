package com.svalero.tripalbum.contract;

import android.content.Context;

import com.svalero.tripalbum.domain.Friend;
import com.svalero.tripalbum.domain.User;

import java.util.List;

public interface FriendsListContract {

    interface Model {
        interface OnLoadUsersListener {
            void OnLoadUsersSuccess(List<User> users);
            void OnLoadUsersError(String message);
        }
        List<Friend> loadFriends(Context context);
        void loadUsers(OnLoadUsersListener listener, String phone);
        void addFriend(Context context, Friend friend);
        void deleteFriend(Context context, Friend friend);
    }

    interface View {
        void listFriends(List<User> friends);
        void listSearchFriends(List<User> searchFriends);
        void showErrorMessage(String message);
    }

    interface Presenter {
        void loadFriends();
        void loadUsers(String phone);
        void openMyAlbum(User user);
        void addFriend(Friend friend, String userId);
        void deleteFriend(String friendId);
    }
}
