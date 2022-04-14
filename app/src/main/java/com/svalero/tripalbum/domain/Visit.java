package com.svalero.tripalbum.domain;

import java.io.Serializable;

public class Visit implements Serializable {
    private String id;
    private User user;
    private Place place;
    private String date;
    private float rating;
    private String commentary;

    public Visit(String id, User user, Place place, String date, float rating, String commentary) {
        this.id = id;
        this.user = user;
        this.place = place;
        this.date = date;
        this.rating = rating;
        this.commentary = commentary;
    }

    public Visit() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return date + ", " + rating + ", " + commentary + ", " + id;
    }
}
