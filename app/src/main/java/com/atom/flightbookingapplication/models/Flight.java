package com.atom.flightbookingapplication.models;

import androidx.annotation.NonNull;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

import java.io.Serializable;
import java.util.Objects;

public class Flight implements Serializable {

    private String status;
    private String flight_number;
    private String airline;
    private Departure departure;
    private Integer seats_available;
    private Arrival  arrival;

    public Flight(){}
    public Flight(String status, String flightNumber,
                  String airlineName, Departure departure,
                  Integer seatNumber, Arrival arrival) {

        this.status = status;
        this.flight_number = flightNumber;
        this.airline = airlineName;
        this.departure = departure;
        this.seats_available = seatNumber;
        this.arrival = arrival;
    }

    @PropertyName("status")
    public String getStatus() {
        return status;
    }

    @PropertyName("flight_number")
    public String getFlightNumber() {
        return flight_number;
    }

    @PropertyName("airline")
    public String getAirlineName() {
        return airline;
    }

    @PropertyName("departure")
    public Departure getDeparture() {
        return departure;
    }

    @PropertyName("seats_available")
    public Integer getSeatNumber() {
        return seats_available;
    }

    @PropertyName("arrival")
    public Arrival getArrival() {
        return arrival;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Flight)) return false;
        Flight flight = (Flight) o;
        return getStatus().equals(flight.getStatus()) &&
                getFlightNumber().equals(flight.getFlightNumber()) &&
                getAirlineName().equals(flight.getAirlineName()) &&
                getDeparture().equals(flight.getDeparture()) &&
                getSeatNumber().equals(flight.getSeatNumber()) &&
                getArrival().equals(flight.getArrival());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStatus(), getFlightNumber(),
                getAirlineName(), getDeparture(), getSeatNumber(),
                getArrival());
    }

    @NonNull
    @Override
    public String toString() {
        return "Flight{" +
                "status='" + status + '\'' +
                ", flightNumber='" + flight_number + '\'' +
                ", airlineName='" + airline + '\'' +
                ", departure=" + departure +
                ", seatNumber=" + seats_available +
                ", arrival=" + arrival +
                '}';
    }
}
