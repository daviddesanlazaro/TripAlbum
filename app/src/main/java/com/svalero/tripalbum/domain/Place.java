package com.svalero.tripalbum.domain;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Place implements Serializable {
    @PrimaryKey
    @NonNull
    private String id;
    @ColumnInfo
    private String name;
    @ColumnInfo
    private String description;
    @ColumnInfo
    private float latitude;
    @ColumnInfo
    private float longitude;
    @ColumnInfo
    private String province_id;

    public Place(@NonNull String id, String name, String description, float latitude, float longitude, String province_id) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.province_id = province_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getProvince_id() {
        return province_id;
    }

    public void setProvince_id(String province_id) {
        this.province_id = province_id;
    }

//    public String getProvinceId() {
//        return province_id;
//    }
//
//    public void setProvinceId(String province_id) {
//        this.province_id = province_id;
//    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof Place)) {
            return false;
        }
        if (o == this) {
            return true;
        }

        final Place other = (Place) o;
        return other.name.equals(this.name);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        result = prime * result;
        if (this.name != null) {
            result += this.name.hashCode();
        }

        return result;
    }
}
