package com.svalero.tripalbum.contract;

import com.svalero.tripalbum.domain.Friendship;
import com.svalero.tripalbum.domain.FriendshipDTO;
import com.svalero.tripalbum.domain.User;

import java.util.List;

public interface FriendsListContract {

    interface Model {
        interface OnLoadFriendsListener {
            void OnLoadFriendsSuccess(List<User> friends);
            void OnLoadFriendsError(String message);
        }
        interface OnLoadSearchListener {
            void OnLoadSearchSuccess(List<User> friends);
            void OnLoadSearchError(String message);
        }
        interface OnAddFriendListener {
            void OnAddFriendSuccess(Friendship friendship);
            void OnAddFriendError(String message);
        }
        interface OnLoadFriendshipListener {
            void OnLoadFriendshipSuccess(Friendship friendship);
            void OnLoadFriendshipError(String message);
        }
        interface OnDeleteFriendshipListener {
            void OnDeleteFriendshipSuccess();
            void OnDeleteFriendshipError(String message);
        }
        void loadFriends(FriendsListContract.Model.OnLoadFriendsListener listener, long userId);
        void searchFriends(FriendsListContract.Model.OnLoadSearchListener listener, long userId, String phone);
        void addFriend(FriendsListContract.Model.OnAddFriendListener listener, FriendshipDTO friendshipDTO);
        void loadFriendship(FriendsListContract.Model.OnLoadFriendshipListener listener, long userId, long friendId);
        void deleteFriendship(FriendsListContract.Model.OnDeleteFriendshipListener listener, long friendshipId);
    }

    interface View {
        void listFriends(List<User> friends);
        void listSearchFriends(List<User> searchFriends);
        void showErrorMessage(String message);
    }

    interface Presenter {
        void loadFriends(long userId);
        void loadSearchFriends(long userId, String phone);
        void openMyAlbum(User user);
        void addFriend(long userId, long friendId);
        void loadFriendshipForDelete(long userId, long friendId);
        void deleteFriendship(long friendshipId);
    }
}
