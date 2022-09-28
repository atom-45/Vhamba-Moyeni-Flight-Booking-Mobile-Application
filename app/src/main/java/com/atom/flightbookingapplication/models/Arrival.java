package com.atom.flightbookingapplication.models;

import androidx.annotation.NonNull;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

import java.io.Serializable;
import java.util.Objects;


public class Arrival  implements Serializable {

    private String airport_name;
    private String arrival_city;
    private String time_of_arrival;


    public Arrival(){}
    public Arrival(String airportName, String arrivalCity, String timeOfArrival) {
        this.airport_name = airportName;
        this.arrival_city = arrivalCity;
        this.time_of_arrival = timeOfArrival;
    }

    @PropertyName("airport_name")
    public String getAirportName() {
        return airport_name;
    }

    @PropertyName("arrival_city")
    public String getArrivalCity() {
        return arrival_city;
    }

    @PropertyName("time_of_arrival")
    public String getTimeOfArrival() {
        return time_of_arrival;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Arrival)) return false;
        Arrival arrival = (Arrival) o;
        return getAirportName().equals(arrival.getAirportName()) &&
                getArrivalCity().equals(arrival.getArrivalCity()) &&
                getTimeOfArrival().equals(arrival.getTimeOfArrival());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAirportName(), getArrivalCity(), getTimeOfArrival());
    }

    @NonNull
    @Override
    public String toString() {
        return "Arrival{" +
                "airportName='" + airport_name + '\'' +
                ", arrivalCity='" + arrival_city + '\'' +
                ", timeOfArrival='" + time_of_arrival + '\'' +
                '}';
    }
}
