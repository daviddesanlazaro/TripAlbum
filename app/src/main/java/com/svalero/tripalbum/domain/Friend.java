package com.svalero.tripalbum.domain;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Friend {
    @PrimaryKey
    @NonNull
    private String id;
    @ColumnInfo
    private String username;
    @ColumnInfo
    private String email;
    @ColumnInfo
    private String phone;

    public Friend(@NonNull String id, String username, String email, String phone) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return username;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof Friend)) {
            return false;
        }
        if (o == this) {
            return true;
        }

        final Friend other = (Friend) o;
        return other.username.equals(this.username);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        result = prime * result;
        if (this.username != null) {
            result += this.username.hashCode();
        }

        return result;
    }
}
