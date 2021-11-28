package com.svalero.tripalbum.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.svalero.tripalbum.database.TimestampConverter;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
public class Visit implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo
    @TypeConverters({TimestampConverter.class})
    private LocalDate date;
    @ColumnInfo
    private float rating;
    @ColumnInfo
    private String commentary;
    @ColumnInfo
    private int placeId;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] image;

    public Visit(int id, LocalDate date, float rating, String commentary, int placeId, byte[] image) {
        this.id = id;
        this.date = date;
        this.rating = rating;
        this.commentary = commentary;
        this.placeId = placeId;
        this.image = image;
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

    public int getPlaceId() {
        return placeId;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return date + ", " + rating + ", " + commentary + ", " + id + ", " + placeId + " " + image;
    }
}
