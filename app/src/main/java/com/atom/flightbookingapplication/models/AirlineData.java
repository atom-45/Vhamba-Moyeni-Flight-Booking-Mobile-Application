package com.atom.flightbookingapplication.models;

import androidx.annotation.NonNull;


import com.google.firebase.database.PropertyName;

import java.util.List;
import java.util.Objects;


public class AirlineData {

    private String iata;
    private String icao;
    private String callsign;
    private String extras;
    private long fleet_size;
    private long destination_number;
    private List<String> aircraft_types;
    private FlightStatus flight_statuses;

    public AirlineData(){}

    public AirlineData(String IATA, String ICAO, String callSign,
                       String extras, long fleetSize, long destinationNumber,
                       List<String> aircraftTypes, FlightStatus flightStatus) {

        this.iata = IATA;
        this.icao = ICAO;
        this.callsign = callSign;
        this.extras = extras;
        this.fleet_size = fleetSize;
        this.destination_number = destinationNumber;
        this.aircraft_types = aircraftTypes;
        this.flight_statuses = flightStatus;
    }

    @PropertyName("iata")
    public String getIATA() {
        return iata;
    }

    @PropertyName("icao")
    public String getICAO() {
        return icao;
    }

    @PropertyName("callsign")
    public String getCallSign() {
        return callsign;
    }

    @PropertyName("extras")
    public String getExtras() {
        return extras;
    }

    @PropertyName("fleet_size")
    public long getFleetSize() {
        return fleet_size;
    }

    @PropertyName("aircraft_types")
    public List<String> getAircraftTypes() {
        return aircraft_types;
    }

    @PropertyName("flight_statuses")
    public FlightStatus getFlightStatus() {
        return flight_statuses;
    }

    @PropertyName("destination_number")
    public long getDestinationNumber() {return destination_number;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AirlineData)) return false;
        AirlineData that = (AirlineData) o;
        return  getFleetSize() == that.getFleetSize() &&
                getDestinationNumber() == that.getDestinationNumber() &&
                getIATA().equals(that.getIATA()) &&
                getICAO().equals(that.getICAO()) &&
                getCallSign().equals(that.getCallSign()) &&
                getExtras().equals(that.getExtras()) &&
                getAircraftTypes().equals(that.getAircraftTypes()) &&
                getFlightStatus().equals(that.getFlightStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIATA(), getICAO(), getCallSign(), getExtras(),
                getFleetSize(), getDestinationNumber(), getAircraftTypes(), getFlightStatus());
    }

    @NonNull
    @Override
    public String toString() {
        return "AirlineData{" +
                "IATA='" + iata + '\'' +
                ", ICAO='" + icao + '\'' +
                ", CallSign='" + callsign + '\'' +
                ", extras='" + extras + '\'' +
                ", fleetSize=" + fleet_size +
                ", destinationNumber=" + destination_number +
                ", aircraftTypes=" + aircraft_types +
                ", flightStatus=" + flight_statuses +
                '}';
    }
}
