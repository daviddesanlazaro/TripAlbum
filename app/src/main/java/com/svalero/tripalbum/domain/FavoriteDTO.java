package com.svalero.tripalbum.domain;

import java.io.Serializable;

public class FavoriteDTO implements Serializable  {
    private long user;
    private long place;

    public FavoriteDTO(long user, long place) {
        this.user = user;
        this.place = place;
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
