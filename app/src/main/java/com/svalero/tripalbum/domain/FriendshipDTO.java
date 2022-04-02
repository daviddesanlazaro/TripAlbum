package com.svalero.tripalbum.domain;

import java.io.Serializable;

public class FriendshipDTO implements Serializable  {
    private long user;
    private long friend;

    public FriendshipDTO(long user, long friend) {
        this.user = user;
        this.friend = friend;
    }

    public long getUser() {
        return user;
    }

    public void setUser(long user) {
        this.user = user;
    }

    public long getFriend() {
        return friend;
    }

    public void setFriend(long friend) {
        this.friend = friend;
    }
}
