package com.svalero.tripalbum.domain;

import java.time.LocalDate;

public class Visit {
    private int id;
    private LocalDate date;
    private float rating;
    private String commentary;
    private Place place;

    public Visit(int id, LocalDate date, float rating, String commentary, Place place) {
        this.id = id;
        this.date = date;
        this.rating = rating;
        this.commentary = commentary;
        this.place = place;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
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

    @Override
    public String toString() {
        return place.getName() + ", " + rating;
    }
}
