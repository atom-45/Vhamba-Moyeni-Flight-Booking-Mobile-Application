package com.atom.flightbookingapplication.models;

import androidx.annotation.NonNull;

import com.google.firebase.database.PropertyName;

import java.io.Serializable;
import java.util.Objects;

public class Ticket implements Serializable {

    private String name;
    private String surname;
    private String airline;
    private String flight_date;
    private String status;
    private String booking_reference;
    private String booking_date;
    private String flight_number;
    private Double ticket_price;
    private Departure departure;
    private Arrival arrival;
    private FlightPreference flight_preference;
    private boolean checked_in;

    public Ticket(){}
    public Ticket(Departure departure, Arrival arrival,
                  String name, String surname, String airlineBrand,
                  String flightDate, String status,
                  String bookingReference, String bookingDate, String flightNumber,
                  Double ticketPrice, FlightPreference flightPreference, boolean checkedIn)
    {
        this.name = name;
        this.surname = surname;
        this.airline = airlineBrand;
        this.flight_date = flightDate;
        this.status = status;
        this.booking_reference = bookingReference;
        this.booking_date = bookingDate;
        this.flight_number = flightNumber;
        this.ticket_price = ticketPrice;
        this.flight_preference = flightPreference;
        this.departure = departure;
        this.arrival = arrival;
        this.checked_in = checkedIn;
    }


    @PropertyName("name")
    public String getName() {
        return name;
    }

    @PropertyName("surname")
    public String getSurname() {
        return surname;
    }

    @PropertyName("airline")
    public String getAirline() {
        return airline;
    }

    @PropertyName("flight_date")
    public String getFlightDate() {
        return flight_date;
    }

    @PropertyName("status")
    public String getStatus() {
        return status;
    }

    @PropertyName("booking_reference")
    public String getBookingReference() {
        return booking_reference;
    }

    @PropertyName("booking_date")
    public String getBookingDate() {
        return booking_date;
    }

    @PropertyName("flight_number")
    public String getFlightNumber() {
        return flight_number;
    }

    @PropertyName("ticket_price")
    public Double getTicketPrice() {
        return ticket_price;
    }

    @PropertyName("departure")
    public Departure getDeparture() {
        return departure;
    }

    @PropertyName("arrival")
    public Arrival getArrival() {
        return arrival;
    }

    @PropertyName("flight_preference")
    public FlightPreference getFlightPreference() {
        return flight_preference;
    }

    @PropertyName("checked_in")
    public boolean isCheckedIn() {
        return checked_in;
    }

    public void setCheckedIn(boolean checked_in) {
        this.checked_in = checked_in;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ticket)) return false;
        Ticket ticket = (Ticket) o;
        return isCheckedIn() == ticket.isCheckedIn() &&
                getName().equals(ticket.getName()) &&
                getSurname().equals(ticket.getSurname()) &&
                getAirline().equals(ticket.getAirline()) &&
                getFlightDate().equals(ticket.getFlightDate()) &&
                getStatus().equals(ticket.getStatus()) &&
                getBookingReference().equals(ticket.getBookingReference()) &&
                getBookingDate().equals(ticket.getBookingDate()) &&
                getFlightNumber().equals(ticket.getFlightNumber()) &&
                getTicketPrice().equals(ticket.getTicketPrice()) &&
                getDeparture().equals(ticket.getDeparture()) &&
                getArrival().equals(ticket.getArrival()) &&
                getFlightPreference().equals(ticket.getFlightPreference());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getSurname(), getAirline(),
                getFlightDate(), getStatus(), getBookingReference(),
                getBookingDate(), getFlightNumber(), getTicketPrice(),
                getDeparture(), getArrival(), getFlightPreference(), isCheckedIn());
    }

    @NonNull
    @Override
    public String toString() {
        return "Ticket{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", airline='" + airline + '\'' +
                ", flight_date='" + flight_date + '\'' +
                ", status='" + status + '\'' +
                ", booking_reference='" + booking_reference + '\'' +
                ", booking_date='" + booking_date + '\'' +
                ", flight_number='" + flight_number + '\'' +
                ", ticket_price=" + ticket_price +
                ", departure=" + departure +
                ", arrival=" + arrival +
                ", flight_preference=" + flight_preference +
                ", checked_in=" + checked_in +
                '}';
    }
}
