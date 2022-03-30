package com.svalero.tripalbum.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//@Entity
public class User {
//    @PrimaryKey(autoGenerate = true)
    private int id;
//    @ColumnInfo
    private String name;
//    @ColumnInfo
    private String surname;
//    @ColumnInfo
    private String email;
//    @ColumnInfo
    private String phone;
//    @ColumnInfo
    private boolean sendData;

    public User(int id, String name, String surname, String email, String phone, boolean sendData) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.sendData = sendData;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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

    public boolean isSendData() {
        return sendData;
    }

    public void setSendData(boolean sendData) {
        this.sendData = sendData;
    }

    @Override
    public String toString() {
        return name + " " + surname;
    }
}