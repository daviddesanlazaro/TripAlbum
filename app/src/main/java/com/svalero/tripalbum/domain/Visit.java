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
    private long id;
    @ColumnInfo
    private long userId;
    @ColumnInfo
    private long placeId;
    @ColumnInfo
//    @TypeConverters({TimestampConverter.class})
    private String date;
    @ColumnInfo
    private float rating;
    @ColumnInfo
    private String commentary;
//    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
//    private byte[] image;

    public Visit(long userId, long placeId, String date, float rating, String commentary) {
//        this.id = id;
        this.userId = userId;
        this.placeId = placeId;
        this.date = date;
        this.rating = rating;
        this.commentary = commentary;
//        this.image = image;
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

    public long getPlaceId() {
        return placeId;
    }

    public void setPlaceId(long placeId) {
        this.placeId = placeId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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
        return date + ", " + rating + ", " + commentary + ", " + id + ", " + placeId;
    }
}
