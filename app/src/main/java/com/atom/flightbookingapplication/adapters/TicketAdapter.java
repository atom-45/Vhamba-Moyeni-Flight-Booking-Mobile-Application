package com.atom.flightbookingapplication.adapters;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.atom.flightbookingapplication.R;
import com.atom.flightbookingapplication.activities.AssistantActivity;
import com.atom.flightbookingapplication.models.Constants;
import com.atom.flightbookingapplication.models.Ticket;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.ViewHolder> {

    private final List<Ticket> userTickets;
    private final List<String> userKeys;
    private static final String TAG = "TicketAdapter";

    public TicketAdapter(Map<String, Ticket> tickets, String name, String surname){

        //Below is how to filter a HashMap using stream and filter
        Map<String, Ticket> filteredTicketsMap = tickets.entrySet()
                .stream()
                .filter(stringTicketEntry -> stringTicketEntry.getValue().getName().equals(name)
                            && stringTicketEntry.getValue().getSurname().equals(surname))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        this.userTickets = new ArrayList<>();
        userTickets.addAll(filteredTicketsMap.values());

        this.userKeys = new ArrayList<>();
        userKeys.addAll(filteredTicketsMap.keySet());

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).
                inflate(R.layout.booking_cardview,parent,false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CardView ticketCardView = holder.cardView;


        String departTimeString = userTickets.get(position).getDeparture().getTimeOfDeparture();
        String arrivalTimeString = userTickets.get(position).getArrival().getTimeOfArrival();
        LocalTime departureTime = LocalTime.parse(departTimeString);
        LocalTime arrivalTime = LocalTime.parse(arrivalTimeString);
        long timeDifference = departureTime.until(arrivalTime, ChronoUnit.MINUTES);

        TextView flightAirlineTextView = ticketCardView.findViewById(R.id.payment_airline);
        TextView flightNumberTextView = ticketCardView.findViewById(R.id.payment_flightnumber);
        TextView bookingReferenceTextView = ticketCardView.findViewById(R.id.reference_textview_b);
        TextView flightDateTextView = ticketCardView.findViewById(R.id.payment_flightDate_2);
        TextView flightFromTextView = ticketCardView.findViewById(R.id.payment_flightFrom_2);
        TextView flightToTextView = ticketCardView.findViewById(R.id.payment_flightTo_2);
        TextView flightClassTextView = ticketCardView.findViewById(R.id.payment_flightClass_2);
        TextView flightTimeDurationTextView = ticketCardView.findViewById(R.id.payment_flightDuration_2);
        TextView flightTicketPriceTextView = ticketCardView.findViewById(R.id.payment_flightTicketPrice_2);


        flightAirlineTextView.setText(userTickets.get(position).getAirline());
        flightNumberTextView.setText(userTickets.get(position).getFlightNumber());
        bookingReferenceTextView.setText(userTickets.get(position).getBookingReference());
        flightDateTextView.setText(userTickets.get(position).getFlightDate());
        flightFromTextView.setText(userTickets.get(position).getDeparture().getAirportName());
        flightToTextView.setText(userTickets.get(position).getArrival().getAirportName());
        flightClassTextView.setText(userTickets.get(position).getFlightPreference().getAirplane_class());
        flightTicketPriceTextView.setText(String.format(Locale.ENGLISH,
                "R %.2f",userTickets.get(position).getTicketPrice()));
        flightTimeDurationTextView.setText(String.format("%1$s - %2$s, %3$s",
                departTimeString,arrivalTimeString, flightTimeInString(timeDifference)));

        ticketCardView.setOnClickListener(view -> {

            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.SERIALIZABLE_TICKET,
                    userTickets.get(holder.getBindingAdapterPosition()));

            ticketCardView.getContext()
                    .startActivity(new Intent(ticketCardView.getContext(),
                            AssistantActivity.class)
                            .putExtra(Constants.TICKET_KEY,
                                    userKeys.get(holder.getBindingAdapterPosition()))
                            .putExtras(bundle));
        });
    }

    @Override
    public int getItemCount() {
        return userTickets.size();
    }


    private String flightTimeInString(long timeDifference){
        long remainingMinutes;
        long hours;
        if(timeDifference>=60){
            remainingMinutes = timeDifference % 60;
            hours = timeDifference / 60;
            return hours+"hr "+remainingMinutes+"min";
        }
        return timeDifference+"min";
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final CardView cardView;

        public ViewHolder(@NonNull CardView cardView) {
            super(cardView);
            this.cardView = cardView;
        }

    }
}
