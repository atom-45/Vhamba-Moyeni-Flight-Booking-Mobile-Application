package com.atom.flightbookingapplication.models;

import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

import java.io.Serializable;
import java.util.Objects;


public class Departure implements Serializable {

    private String airport_name;
    private String departure_city;
    private String time_of_departure;

    public Departure(){}
    public Departure(String airportName, String departureCity, String timeOfDeparture) {
        this.airport_name = airportName;
        this.departure_city = departureCity;
        this.time_of_departure = timeOfDeparture;
    }

    @PropertyName("airport_name")
    public String getAirportName() {
        return airport_name;
    }

    @PropertyName("departure_city")
    public String getDepartureCity() {
        return departure_city;
    }

    @PropertyName("time_of_departure")
    public String getTimeOfDeparture() {
        return time_of_departure;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Departure)) return false;
        Departure departure = (Departure) o;
        return getAirportName().equals(departure.getAirportName()) &&
                getDepartureCity().equals(departure.getDepartureCity()) &&
                getTimeOfDeparture().equals(departure.getTimeOfDeparture());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAirportName(), getDepartureCity(), getTimeOfDeparture());
    }

    @NonNull
    @Override
    public String toString() {
        return "Departure{" +
                "airportName='" + airport_name + '\'' +
                ", departureCity='" + departure_city + '\'' +
                ", timeOfDeparture='" + time_of_departure + '\'' +
                '}';
    }
}
