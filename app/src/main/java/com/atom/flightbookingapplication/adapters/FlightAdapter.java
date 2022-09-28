package com.atom.flightbookingapplication.adapters;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.atom.flightbookingapplication.R;
import com.atom.flightbookingapplication.activities.ItineraryActivity;
import com.atom.flightbookingapplication.models.Arrival;
import com.atom.flightbookingapplication.models.Constants;
import com.atom.flightbookingapplication.models.Departure;
import com.atom.flightbookingapplication.models.Flight;
import com.atom.flightbookingapplication.models.FlightPreference;
import com.atom.flightbookingapplication.models.TicketPrice;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


public class FlightAdapter extends RecyclerView.Adapter<FlightAdapter.ViewHolder> {



    private final int numberOfBags;
    private final List<Flight> filteredFlights;
    private final String bookingDate;

    public FlightAdapter(Map<String, List<Flight>> flightSchedules, String bookingDate,
                         FlightPreference flightPreference) {


        List<Flight> firstFlightPreferences = Objects.requireNonNull(flightSchedules.get(bookingDate))
                .stream()
                .filter(flight -> {
                    String firstAirport = flightPreference.getFirst_airport();
                    String departureAirport = flight.getDeparture().getAirportName();
                    String filteredFirstAirport = "";
                    String filteredDepartureAirport;

                    int indexOfDepartureAirport = departureAirport.indexOf("Int");
                    int indexOfFirstAirport = firstAirport.indexOf("Int");

                    if (indexOfDepartureAirport == -1) {
                        filteredDepartureAirport = departureAirport
                                .substring(0, departureAirport.indexOf("Air"));

                    } else if(indexOfFirstAirport == -1) {
                        filteredDepartureAirport = firstAirport.substring(0,
                                firstAirport.indexOf("Air"));

                    }  else {
                        filteredFirstAirport = firstAirport.substring(0, indexOfFirstAirport);
                        filteredDepartureAirport = departureAirport.substring(0, indexOfDepartureAirport);
                    }
                    return filteredFirstAirport.equals(filteredDepartureAirport);

                }).collect(Collectors.toList());


        List<Flight> secondFlightPreferences = Objects.requireNonNull(flightSchedules.get(bookingDate))
                .stream()
                .filter(flight -> {
                    String secondAirport = flightPreference.getSecond_airport();
                    String departureAirport = flight.getDeparture().getAirportName();
                    String filteredSecondAirport = "";
                    String filteredDepartureAirport = "";

                    int indexOfDepartureAirport = departureAirport.indexOf("Int");
                    int indexOfSecondAirport = secondAirport.indexOf("Int");

                    if (indexOfDepartureAirport == -1) {
                        filteredDepartureAirport = departureAirport
                                .substring(0, departureAirport.indexOf("Air"));

                    } else if(indexOfSecondAirport == -1) {
                        filteredSecondAirport = secondAirport.substring(0,
                                secondAirport.indexOf("Air"));

                    }  else {
                        filteredSecondAirport = secondAirport.substring(0, indexOfSecondAirport);
                        filteredDepartureAirport = departureAirport.substring(0, indexOfDepartureAirport);
                    }
                    return filteredSecondAirport.equals(filteredDepartureAirport);

                }).collect(Collectors.toList());

        List<Flight> thirdFlightPreferences = Objects.requireNonNull(flightSchedules.get(bookingDate))
                .stream()
                .filter(flight -> {
                    String thirdAirport = flightPreference.getThird_airport();
                    String departureAirport = flight.getDeparture().getAirportName();
                    String filteredSecondAirport = "";
                    String filteredDepartureAirport = "";

                    int indexOfDepartureAirport = departureAirport.indexOf("Int");
                    int indexOfSecondAirport = thirdAirport.indexOf("Int");

                    if (indexOfDepartureAirport == -1) {
                        filteredDepartureAirport = departureAirport
                                .substring(0, departureAirport.indexOf("Air"));

                    } else if(indexOfSecondAirport == -1) {
                        filteredSecondAirport = thirdAirport.substring(0,
                                thirdAirport.indexOf("Air"));

                    }  else {
                        filteredSecondAirport = thirdAirport.substring(0, indexOfSecondAirport);
                        filteredDepartureAirport = departureAirport.substring(0, indexOfDepartureAirport);
                    }
                    return filteredSecondAirport.equals(filteredDepartureAirport);

                }).collect(Collectors.toList());


        this.bookingDate = bookingDate;
        this.numberOfBags = Integer.parseInt(flightPreference.getNumber_of_bags());
        this.filteredFlights = new ArrayList<>();
        filteredFlights.addAll(firstFlightPreferences);
        filteredFlights.addAll(secondFlightPreferences);
        filteredFlights.addAll(thirdFlightPreferences);

    }

    @NonNull
    @Override
    public FlightAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).
                inflate(R.layout.flight_selection,parent,false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull FlightAdapter.ViewHolder holder, int position) {

        CardView cardViewHolder = holder.cardView;

        TextView airlineNameTextView = cardViewHolder.findViewById(R.id.airlineName_textView);
        TextView airlineFlightNumberTextView = cardViewHolder.findViewById(R.id.airlineFN_textview);
        TextView airlineFlightStatusTextView = cardViewHolder.findViewById(R.id.flightStatus_textView_b);
        TextView originTextView = cardViewHolder.findViewById(R.id.airlineFrom_textView_b);
        TextView destinationTextView = cardViewHolder.findViewById(R.id.airlineTo_textView_b);
        TextView originTimeTextView = cardViewHolder.findViewById(R.id.airlineFromTime_textView);
        TextView destinationTimeTextView = cardViewHolder.findViewById(R.id.airlineToTime_textView);
        TextView airlineSeatsAvailable = cardViewHolder.findViewById(R.id.airlineSeat_textView_b);
        TextView airlineTicketPrice = cardViewHolder.findViewById(R.id.airlinePrice_textView_b);

        List<Double> ticketPrices = flightTicketPrices();

        airlineNameTextView.setText(filteredFlights.get(position).getAirlineName());
        airlineFlightNumberTextView.setText(filteredFlights.get(position).getFlightNumber());
        airlineFlightStatusTextView.setText(filteredFlights.get(position).getStatus());
        originTextView.setText(filteredFlights.get(position).getDeparture().getAirportName());
        destinationTextView.setText(filteredFlights.get(position).getArrival().getAirportName());
        originTimeTextView.setText(filteredFlights.get(position).getDeparture().getTimeOfDeparture());
        destinationTimeTextView.setText(filteredFlights.get(position).getArrival().getTimeOfArrival());
        airlineSeatsAvailable.setText(String.format(Locale.ENGLISH," %d",
                filteredFlights.get(position).getSeatNumber()));
        airlineTicketPrice.setText(String.format(Locale.ENGLISH,
                "R %.2f",ticketPrices.get(position)));

        cardViewHolder.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.SERIALIZABLE_FLIGHT,
                    filteredFlights.get(holder.getBindingAdapterPosition()));
            cardViewHolder.getContext()
                    .startActivity(new Intent(cardViewHolder.getContext(), ItineraryActivity.class)
                            .putExtra(Constants.BOOKING_DATE,bookingDate)
                            .putExtra(Constants.TICKET_PRICE,flightTicketPrices()
                                    .get(holder.getBindingAdapterPosition()))
                            .putExtras(bundle));

        });
    }

    @Override
    public int getItemCount() {
        if(filteredFlights != null){
            return filteredFlights.size();
        } else {
            return 0;
        }
    }

    private List<Double> flightTicketPrices(){

        List<Double> flightTicketPrices = new ArrayList<>();
        Departure departure;
        Arrival arrival;
        String airline;
        double seatsAvailable;

        for (Flight flight: filteredFlights) {
            departure = flight.getDeparture();
            arrival = flight.getArrival();
            airline = flight.getAirlineName();
            seatsAvailable = flight.getSeatNumber();

            flightTicketPrices.add(new TicketPrice(arrival,departure,airline,seatsAvailable,
                    numberOfBags).calculateTicketPrice());

        }
        return flightTicketPrices;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final CardView cardView;
        public ViewHolder(@NonNull CardView cardView) {
            super(cardView);

            this.cardView = cardView;
        }
    }
}
