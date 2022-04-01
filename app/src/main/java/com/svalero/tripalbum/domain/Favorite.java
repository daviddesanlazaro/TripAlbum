package com.svalero.tripalbum.domain;

import java.io.Serializable;

public class Favorite implements Serializable {
    private long id;
    private User user;
    private Place place;

    public Favorite(long id, User user, Place place) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }
}
