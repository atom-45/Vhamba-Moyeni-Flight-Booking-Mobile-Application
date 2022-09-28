package com.atom.flightbookingapplication.models;

import androidx.annotation.NonNull;

import java.util.Objects;

public class Shop {

    private final String name;
    private final String openingTime;
    private final String closingTime;
    private final String description;
    private final int imageBackgroundID;
    private final int imageLogoID;

    public Shop(String name, String openingTime, String closingTime,
                String description, int imageBackgroundID, int imageLogoID) {
        this.name = name;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.description = description;
        this.imageBackgroundID = imageBackgroundID;
        this.imageLogoID = imageLogoID;
    }

    public String getName() {
        return name;
    }

    public String getOpeningTime() {
        return openingTime;
    }

    public String getClosingTime() {
        return closingTime;
    }

    public String getDescription() {
        return description;
    }

    public int getImageBackgroundID() {
        return imageBackgroundID;
    }

    public int getImageLogoID() {
        return imageLogoID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Shop)) return false;
        Shop shop = (Shop) o;
        return getImageBackgroundID() == shop.getImageBackgroundID() &&
                getImageLogoID() == shop.getImageLogoID() &&
                getName().equals(shop.getName()) &&
                getOpeningTime().equals(shop.getOpeningTime()) &&
                getClosingTime().equals(shop.getClosingTime()) &&
                getDescription().equals(shop.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getOpeningTime(), getClosingTime(), getDescription(),
                getImageBackgroundID(), getImageLogoID());
    }

    @NonNull
    @Override
    public String toString() {
        return "Shop{" +
                "name='" + name + '\'' +
                ", openingTime='" + openingTime + '\'' +
                ", closingTime='" + closingTime + '\'' +
                ", description='" + description + '\'' +
                ", imageBackgroundID=" + imageBackgroundID +
                ", imageLogoID=" + imageLogoID +
                '}';
    }
}
