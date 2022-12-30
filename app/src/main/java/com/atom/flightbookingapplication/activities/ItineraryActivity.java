package com.atom.flightbookingapplication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import android.widget.TextView;

import com.atom.flightbookingapplication.databinding.ActivityItineraryBinding;
import com.atom.flightbookingapplication.databinding.OnewayCardviewBinding;
import com.atom.flightbookingapplication.models.Arrival;
import com.atom.flightbookingapplication.models.Constants;
import com.atom.flightbookingapplication.models.Departure;
import com.atom.flightbookingapplication.models.Flight;
import com.atom.flightbookingapplication.models.FlightPreference;
import com.atom.flightbookingapplication.models.Ticket;
import com.atom.flightbookingapplication.models.User;
import com.atom.flightbookingapplication.viewmodels.FirebaseRTDBViewModel;
import com.atom.flightbookingapplication.viewmodels.UserViewModel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Random;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ItineraryActivity extends AppCompatActivity {

    private CompositeDisposable compositeDisposable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityItineraryBinding activityItineraryBinding =
                ActivityItineraryBinding.inflate(getLayoutInflater());
        setContentView(activityItineraryBinding.getRoot());


        UserViewModel userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        FirebaseRTDBViewModel rtdbViewModel =
                new ViewModelProvider(this, ViewModelProvider.Factory
                        .from(FirebaseRTDBViewModel.initializer))
                        .get(FirebaseRTDBViewModel.class);

        compositeDisposable = new CompositeDisposable();

        Flight selectedFlight = (Flight) getIntent().getSerializableExtra(Constants.SERIALIZABLE_FLIGHT);
        String date = getIntent().getStringExtra(Constants.BOOKING_DATE);
        double ticketPrice = getIntent().getDoubleExtra(Constants.TICKET_PRICE,0);
        LocalDate currentDate = LocalDate.parse(date);

        LocalTime departureTime = LocalTime.parse(selectedFlight.getDeparture().getTimeOfDeparture());
        LocalTime arrivalTime = LocalTime.parse(selectedFlight.getArrival().getTimeOfArrival());
        String localDateTime = LocalDateTime.now().toString();

        String flightDate = String.format(Locale.ENGLISH,"%1$s, %2$d %3$s %4$d",
                currentDate.getDayOfWeek().toString(),
                currentDate.getDayOfMonth(),
                currentDate.getMonth(),currentDate.getYear());


        Random random = new Random();
        long timeDifference = departureTime.until(arrivalTime, ChronoUnit.MINUTES);
        int randomInteger = random.nextInt(98);
        String flightDuration = flightTimeInString(timeDifference);
        String bookingReference = selectedFlight.getFlightNumber().substring(0,2) +
                selectedFlight.getArrival().getArrivalCity().
                        substring(0,2).toUpperCase() +
                        selectedFlight.getDeparture().getDepartureCity().
                                substring(0,2).toUpperCase() + randomInteger + "XJ";



        OnewayCardviewBinding itineraryCardView = activityItineraryBinding.itinerary;

        TextView flightDateTextView = itineraryCardView.onewayFlightDate;
        TextView flightNumberTextView = itineraryCardView.flightNumberTextview;
        TextView airportFromTextView  = itineraryCardView.airportFromTv;
        TextView airportToTextView = itineraryCardView.airportToTv;
        TextView flightDurationTextView = itineraryCardView.tofTextview;
        TextView departureTimeTextView = itineraryCardView.departureDateOne;
        TextView arrivalTimeTextView = itineraryCardView.arrivalDateOne;
        TextView fullNameTextView = itineraryCardView.fullnameTextview2;
        TextView seatPositionTextView = itineraryCardView.seatnumberTextview2;
        TextView flightBagsNumberTextView = itineraryCardView.checkedbagsTextview2;
        TextView flightClassTypeTextView = itineraryCardView.planeClassTextview2;
        TextView flightTicketPriceTextView = itineraryCardView.totalTicketpriceTextview2;
        TextView bookingReferenceTextView = itineraryCardView.bookingReferenceTextviewB;


        Disposable disposable = userViewModel.getAllUsers()
                .subscribeOn(Schedulers.io())
                .doOnNext(users -> {
                    User user = users.get(users.size()-1);
                    FlightPreference preferences = user.getFlightPreference();
                    String fullName = user.getName()+" "+user.getSurname();
                    String classType = preferences.getAirplane_class();
                    String numberOfBags = preferences.getNumber_of_bags();
                    String seatPosition = preferences.getSeat_position();

                    flightDateTextView.setText(flightDate);

                    flightNumberTextView.setText(String.format(Locale.ENGLISH,"%1$s %2$s",
                            selectedFlight.getAirlineName(), selectedFlight.getFlightNumber()));

                    airportFromTextView.setText(selectedFlight.getDeparture().getAirportName());
                    airportToTextView.setText(selectedFlight.getArrival().getAirportName());
                    departureTimeTextView.setText(selectedFlight.getDeparture().getTimeOfDeparture());
                    arrivalTimeTextView.setText(selectedFlight.getArrival().getTimeOfArrival());
                    fullNameTextView.setText(fullName);
                    seatPositionTextView.setText(seatPosition);
                    flightBagsNumberTextView.setText(
                            String.format(Locale.ENGLISH,"%s x 20kg",numberOfBags));
                    flightDurationTextView.setText(
                            String.format(Locale.ENGLISH,"Flight Duration: %s",flightDuration));
                    flightClassTypeTextView.setText(classType);
                    flightTicketPriceTextView.setText(String.format(Locale.ENGLISH,
                            "R %.2f",ticketPrice));
                    bookingReferenceTextView.setText(bookingReference);

                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();

        compositeDisposable.add(disposable);

        activityItineraryBinding.itineraryPayButton.setOnClickListener(view -> {

            Disposable disposable1 = userViewModel.getAllUsers()
                    .subscribeOn(Schedulers.io())
                    .doOnNext(users -> {
                        User user = users.get(users.size()-1);
                        FlightPreference flightPreference = user.getFlightPreference();
                        String name = user.getName();
                        String surname = user.getSurname();
                        String flightNumber = selectedFlight.getFlightNumber();
                        String flightStatus = selectedFlight.getStatus();
                        String airlineName = selectedFlight.getAirlineName();
                        String airlineNameLowerCase = airlineNameEdit(selectedFlight.getAirlineName());
                        Departure departure = selectedFlight.getDeparture();
                        Arrival arrival = selectedFlight.getArrival();

                        rtdbViewModel.saveUserAirplaneTicket(this,
                                new Ticket(departure,arrival,
                                        name,surname,airlineName,flightDate,flightStatus,
                                bookingReference,localDateTime,flightNumber
                                ,ticketPrice,flightPreference, false));

                        rtdbViewModel.updateAirlineFlightSeats(airlineNameLowerCase,date,flightNumber);
                        startActivity(new Intent(this, MainActivity.class));
                        finish();

                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe();
            compositeDisposable.add(disposable1);

        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }

    private String airlineNameEdit(String airline){
        switch (airline) {
            case "South African Airways":
                return "sa_airways";

            case "FlySafair":

            case "FlyCemair":

            case "Airlink":
                return airline.toLowerCase();
            default:
                return "none";
        }
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
}