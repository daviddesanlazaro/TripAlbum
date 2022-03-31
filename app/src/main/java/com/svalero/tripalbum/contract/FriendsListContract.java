package com.svalero.tripalbum.contract;

import com.svalero.tripalbum.domain.User;

import java.util.List;

public interface FriendsListContract {

    interface Model {
        interface OnLoadFriendsListener {
            void OnLoadFriendsSuccess(List<User> friends);
            void OnLoadFriendsError(String message);
        }
        void loadFriends(FriendsListContract.Model.OnLoadFriendsListener listener, int userId);
    }

    interface View {
        void listFriends(List<User> friends);
        void showErrorMessage(String message);
    }

    interface Presenter {
        void loadFriends(int userId);
        void openMyAlbum(int userId);
    }
}
