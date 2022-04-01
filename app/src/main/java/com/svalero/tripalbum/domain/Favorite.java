package com.svalero.tripalbum.domain;

import java.io.Serializable;

public class Favorite implements Serializable {
    private long id;
    private long user;
    private long place;

    public Favorite(long id, long user, long place) {
        this.id = id;
        this.user = user;
        this.place = place;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUser() {
        return user;
    }

    public void setUser(long user) {
        this.user = user;
    }

    public long getPlace() {
        return place;
    }

    public void setPlace(long place) {
        this.place = place;
    }
}
