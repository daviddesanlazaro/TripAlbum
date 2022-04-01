package com.svalero.tripalbum.domain;

public class Province {
    private long id;
    private String name;
    private long countryId;

    public Province(long id, String name, long countryId) {
        this.id = id;
        this.name = name;
        this.countryId = countryId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCountryId() {
        return countryId;
    }

    public void setCountryId(long countryId) {
        this.countryId = countryId;
    }

    @Override
    public String toString() {
        return name;
    }
}
