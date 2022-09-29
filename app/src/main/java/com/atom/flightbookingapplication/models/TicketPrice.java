package com.atom.flightbookingapplication.models;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public class TicketPrice {

    private final Arrival arrival;
    private final Departure departure;
    private final String airline;
    private final double seatsAvailable;
    private final double numberOfBags;
    private double fuelCapacity;
    private double totalSeatCapacity;


    public TicketPrice(Arrival arrival, Departure departure, String airline,
                       double seatsAvailable, double numberOfBags) {
        this.arrival = arrival;
        this.departure = departure;
        this.airline = airline.toLowerCase(Locale.ROOT);
        this.seatsAvailable = seatsAvailable;
        this.numberOfBags = numberOfBags;
        this.fuelCapacity = 0;

    }

    private double getTotalSeatCapacity() {
        switch (airline) {
            case "airlink":
                return totalSeatCapacity = Constants.EMBRAER_SEAT_CAPACITY;

            case "flysafair":
                return totalSeatCapacity = Constants.BOEING_SEAT_CAPACITY;

            case "south african airways":
                return totalSeatCapacity = Constants.AIRBUS_SEAT_CAPACITY;

            case "flycemair":
                return totalSeatCapacity = Constants.BOMBARDIER_SEAT_CAPACITY;

            default:
                return totalSeatCapacity;
        }
    }

    private double getFuelCapacity() {

        LocalTime departureTime = LocalTime.parse(departure.getTimeOfDeparture());
        LocalTime arrivalTime = LocalTime.parse(arrival.getTimeOfArrival());
        long timeDifference = departureTime.until(arrivalTime, ChronoUnit.MINUTES);

        switch (airline) {
            case "airlink":
                return fuelCapacity = (Constants.EMBRAER_FUEL_BURN_RATE * 60) * timeDifference;

            case "flysafair":
                return fuelCapacity = (Constants.BOEING_FUEL_BURN_RATE * 60) * timeDifference;

            case "south african airways":
                return fuelCapacity = (Constants.AIRBUS_FUEL_BURN_RATE * 60) * timeDifference;

            case "flycemair":
                return fuelCapacity = (Constants.BOMBARDIER_FUEL_BURN_RATE * 60) * timeDifference;

            default:
                return fuelCapacity;
        }
    }

    private double calculateTotalFuelMass(){
        return Constants.KEROSENE_FUEL_DENSITY * (getFuelCapacity()/1000.0);
    }

    private double calculateTotalPassengerMass(){
        double seatDifference = getTotalSeatCapacity() - seatsAvailable;
        return (Constants.AVERAGE_PASSENGER_MASS * seatDifference) + (numberOfBags * 20); // 20 is in kgs and is the standard bag mass needed.
    }

    public double calculateTicketPrice(){
        double fuelPassengerDifference = calculateTotalFuelMass()-calculateTotalPassengerMass();

        if((seatsAvailable!=0)&&(seatsAvailable==getTotalSeatCapacity())){

            return (1/seatsAvailable) * Math.abs(getFuelCapacity() *
                            Constants.KEROSENE_FUEL_PRICE_IN_DOLLARS *
                            Constants.USD_TO_ZAR_CURRENCY_EXCHANGE);

        } else if((seatsAvailable!=0)&&(seatsAvailable<getTotalSeatCapacity())){
            double currentFuelVolume =
                    (fuelPassengerDifference/Constants.KEROSENE_FUEL_DENSITY) * 1000; //in Litres

            return (1/seatsAvailable) * Math.abs(currentFuelVolume *
                            Constants.KEROSENE_FUEL_PRICE_IN_DOLLARS *
                            Constants.USD_TO_ZAR_CURRENCY_EXCHANGE);
        } else {
            return 0;
        }
    }
}
