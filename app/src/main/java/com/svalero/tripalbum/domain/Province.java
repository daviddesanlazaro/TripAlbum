package com.svalero.tripalbum.domain;

public class Province {
    private String id;
    private String name;
    private String countryId;

    public Province(String id, String name, String countryId) {
        this.id = id;
        this.name = name;
        this.countryId = countryId;
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

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    @Override
    public String toString() {
        return name;
    }
}
