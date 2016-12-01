package com.franmontiel.networkcalls.entities;

/**
 * Created by Francisco J. Montiel on 1/12/16.
 */

public class User {
    private int id;
    private String name;
    private String website;

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

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Override
    public String toString() {
        return name + " (" + website + ")";
    }
}
