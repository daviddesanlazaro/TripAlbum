package com.svalero.tripalbum.domain;

import java.io.Serializable;

public class VisitDTO implements Serializable {
    private long id;
    private long user;
    private long place;
    private String date;
    private float rating;
    private String commentary;
//    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
//    private byte[] image;

    public VisitDTO(long user, long place, String date, float rating, String commentary) {
        this.user = user;
        this.place = place;
        this.date = date;
        this.rating = rating;
        this.commentary = commentary;
//        this.image = image;
    }

    public VisitDTO() {

    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
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

    public long getPlace() {
        return place;
    }

    public void setPlace(long place) {
        this.place = place;
    }

    public long getUser() {
        return user;
    }

    public void setUser(long user) {
        this.user = user;
    }

//    public byte[] getImage() {
//        return image;
//    }
//
//    public void setImage(byte[] image) {
//        this.image = image;
//    }

    @Override
    public String toString() {
        return date + ", " + rating + ", " + commentary + ", " + id;
    }
}