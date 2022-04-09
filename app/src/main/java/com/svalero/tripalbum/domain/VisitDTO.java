package com.svalero.tripalbum.domain;

import java.io.Serializable;

public class VisitDTO implements Serializable {
    private String user;
    private String place;
    private String date;
    private float rating;
    private String commentary;
    private String image;

    public VisitDTO(String user, String place, String date, float rating, String commentary, String image) {
        this.user = user;
        this.place = place;
        this.date = date;
        this.rating = rating;
        this.commentary = commentary;
        this.image = image;
    }

    public VisitDTO() {

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

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return date + ", " + rating + ", " + commentary;
    }
}