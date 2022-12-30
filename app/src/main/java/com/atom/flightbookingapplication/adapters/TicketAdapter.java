package com.atom.flightbookingapplication.adapters;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.atom.flightbookingapplication.R;
import com.atom.flightbookingapplication.activities.AssistantActivity;
import com.atom.flightbookingapplication.databinding.BookingCardviewBinding;
import com.atom.flightbookingapplication.models.Constants;
import com.atom.flightbookingapplication.models.Ticket;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.ViewHolder> {

    private final List<Ticket> userTickets;
    private final List<String> userKeys;
    private static final String TAG = "TicketAdapter";
    private LayoutInflater layoutInflater;

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
        if(layoutInflater==null){
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        BookingCardviewBinding bookingCardviewBinding = DataBindingUtil.inflate(layoutInflater,
                R.layout.booking_cardview, parent, false);
        return new ViewHolder(bookingCardviewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        CardView ticketCardView = holder.bookingCardviewBinding.bookingCardView;

        holder.bindTickets(userTickets.get(position));

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


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final BookingCardviewBinding bookingCardviewBinding;

        public ViewHolder(@NonNull BookingCardviewBinding bookingCardviewBinding) {
            super(bookingCardviewBinding.getRoot());
            this.bookingCardviewBinding = bookingCardviewBinding;
        }
        public void bindTickets(Ticket ticket){
            bookingCardviewBinding.setTicket(ticket);
            bookingCardviewBinding.executePendingBindings();
        }
    }
}
