package com.atom.flightbookingapplication.models;

import androidx.annotation.NonNull;

import com.google.firebase.database.PropertyName;

import java.util.List;


public class FlightStatus {


    private List<Flight> scheduledFlights;
    private final String dateChoice = "2022-08-13";

    public FlightStatus(){}
    public FlightStatus(List<Flight> scheduledFlights) {
        this.scheduledFlights = scheduledFlights;
    }


    @PropertyName("2022-08-12")
    public List<Flight> getScheduledFlights() {
        return scheduledFlights;
    }


    @NonNull
    @Override
    public String toString() {
        return "FlightStatus{" +
                "scheduledFlights=" + scheduledFlights +
                '}';
    }
}
