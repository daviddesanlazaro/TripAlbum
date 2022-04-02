package com.svalero.tripalbum.domain;

import java.io.Serializable;

public class Friendship implements Serializable {
    private long id;
    private User user;
    private User friend;

    public Friendship(long id, User user, User friend) {
        this.id = id;
        this.user = user;
        this.friend = friend;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getFriend() {
        return friend;
    }

    public void setFriend(User friend) {
        this.friend = friend;
    }
}
