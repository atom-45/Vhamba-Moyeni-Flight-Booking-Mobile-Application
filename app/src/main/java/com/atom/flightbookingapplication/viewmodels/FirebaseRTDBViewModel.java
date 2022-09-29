package com.atom.flightbookingapplication.viewmodels;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.ViewModelInitializer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.atom.flightbookingapplication.R;
import com.atom.flightbookingapplication.activities.MainActivity;
import com.atom.flightbookingapplication.adapters.FlightAdapter;
import com.atom.flightbookingapplication.adapters.TicketAdapter;
import com.atom.flightbookingapplication.applications.MyApplication;
import com.atom.flightbookingapplication.models.Flight;
import com.atom.flightbookingapplication.models.FlightPreference;
import com.atom.flightbookingapplication.models.Ticket;
import com.atom.flightbookingapplication.models.User;
import com.atom.flightbookingapplication.repositories.FirebaseRTDBRepository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FirebaseRTDBViewModel extends AndroidViewModel {

    private final FirebaseRTDBRepository firebaseRTDBRepository;

    private static final String TAG = "FirebaseRTDBViewModel";

    @Inject
    public FirebaseRTDBViewModel(@NonNull Application application,
                                 FirebaseRTDBRepository firebaseRTDBRepository) {
        super(application);
        this.firebaseRTDBRepository = firebaseRTDBRepository;
    }

    public static final ViewModelInitializer<FirebaseRTDBViewModel> initializer =
            new ViewModelInitializer<>(
                    FirebaseRTDBViewModel.class,
                    creationExtras -> {

                        MyApplication application  = new MyApplication();
                        return new FirebaseRTDBViewModel(application,
                                application.applicationComponent.firebaseRTDBRepository());
                    }
            );

    public void updateAirlineFlightSeats(String selectedAirline, String selectedFlightDate,
                                       String selectedFlightNumber){

        firebaseRTDBRepository.updateAirlineFlightSeats(selectedAirline, selectedFlightDate,
                selectedFlightNumber);

    }

    public void addCustomerQuery(Context context, Map<String, Object> customerQuery){
        firebaseRTDBRepository.addCustomerQueryConcern(customerQuery)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Toast.makeText(context,
                                "Thank your sending in your query" +
                                        " we will contact you through your email", Toast.LENGTH_LONG)
                                .show();
                    } else {
                        Toast.makeText(context, "Unable to submit your concern",
                                Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "addCustomerQuery: ", task.getException());
                    }
                });
    }


    public void saveUserToDatabase(Context context, Map<String, Object> userMap){
        firebaseRTDBRepository.saveUserToDatabase(userMap).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                context.startActivity(new Intent(context, MainActivity.class));

            } else {
                Log.d(TAG, Objects.requireNonNull(task.getException()).toString());
            }
        });
    }

    public void updateUser(Context context,FirebaseAuthenticationViewModel authenticationViewModel,
                           UserViewModel userViewModel, Map<String, String> newProfileDetails,
                           Map<String, Object> newUserDetails, String userKey){

        CompositeDisposable compositeDisposable = new CompositeDisposable();

        firebaseRTDBRepository.updateUser(newUserDetails,userKey).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                userViewModel.getAllUsers()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<List<User>>() {
                            @Override
                            public void onSubscribe(
                                    @io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                                if(!d.isDisposed()){
                                    compositeDisposable.add(d);
                                }
                            }
                            @Override
                            public void onNext(
                                    @io.reactivex.rxjava3.annotations.NonNull List<User> users) {
                                if(users.size()!=0){
                                    User user = users.get(users.size()-1);
                                    String oldPassword = user.getPassword();
                                    String oldEmail = user.getEmail();
                                    authenticationViewModel
                                            .updatePassword(context,oldEmail,oldPassword,
                                                    newProfileDetails,userViewModel);

                                } else {
                                    Log.e(TAG, "onNext: index out bounds, " +
                                            "no user object found in the array");
                                }
                            }

                            @Override
                            public void onError(
                                    @io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                                Log.e(TAG, "onError: "+e.getMessage());
                                compositeDisposable.dispose();
                            }

                            @Override
                            public void onComplete() {
                                compositeDisposable.dispose();
                            }
                        });
            } else {
                Log.e(TAG, "updateUser: ", task.getException());
            }
        });
    }

    public void updateUserFlightPreference(Map<String, Object> flightPrefUpdate, String userKey){
       firebaseRTDBRepository.updateUserFlightPreferences(flightPrefUpdate, userKey)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Toast.makeText(getApplication().getApplicationContext(),
                                "Flight preferences updated", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplication().getApplicationContext(),
                                "Cannot update user flight preferences", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, Objects.requireNonNull(task.getException()).toString());
                    }
                })
       ;
    }

    public void updateUserCardDetails(Map<String, Object> cardUpdate, String userKey){
        firebaseRTDBRepository.updateUserCardDetails(cardUpdate, userKey)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Toast.makeText(getApplication().getApplicationContext(),
                                "User card details updated", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplication().getApplicationContext(),
                                "Cannot update card details", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "updateUserCardDetails: ", task.getException());
                    }
                })
        ;
    }

    public boolean deleteUserFromDatabase(String userName){
        return firebaseRTDBRepository.deleteUserFromDatabase(userName);
    }

    public void saveUserAirplaneTicket(Context context, Ticket ticket){
        firebaseRTDBRepository.saveUserAirplaneTicket(ticket)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Toast.makeText(context,
                                "Your plane ticket has been booked", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(context,
                                "Your plane ticket cannot be booked", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "saveUserAirplaneTicket: ", task.getException());
                    }
                })
        ;
    }

    public void updateUserCheckInValue(Context context, String key,boolean value){
        firebaseRTDBRepository.updateUserCheckInValue(key, value)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Toast.makeText(context, "Congratulations," +
                                "You have checked in for your flight", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e(TAG, "onComplete: ",task.getException());
                    }
                })
        ;
    }

    public void readCurrentAirplaneTicketForDisplay(ImageView checkInImageView,
                                                    TextView flightTextView, TextView dateTextView,
                                                    TextView timeTextView, TextView fromTextVew,
                                                    TextView toTextView, String name,
                                                    String surname){

        firebaseRTDBRepository.readAirplaneTickets().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<Map<String, Ticket>> ticketMapGenericTypeIndicator =
                        new GenericTypeIndicator<Map<String, Ticket>>() {};

                if(snapshot.exists()){
                    Map<String, Ticket> ticketMap = snapshot.getValue(ticketMapGenericTypeIndicator);
                    assert ticketMap != null;
                    List<Ticket> tickets = ticketMap.values()
                            .stream()
                            .filter(ticket -> ticket.getName().equals(name)&&
                                    ticket.getSurname().equals(surname))
                            .sorted((ticket, t1) -> {
                                LocalDateTime localDateTime = LocalDateTime.parse(ticket.getBookingDate());
                                LocalDateTime localDateTime1 = LocalDateTime.parse(t1.getBookingDate());
                                return localDateTime.compareTo(localDateTime1);
                            })
                            .collect(Collectors.toList());

                    Ticket ticket;
                    if(tickets.size()>0){
                        ticket = tickets.get(tickets.size()-1);
                        flightTextView.setText(String.format(Locale.ENGLISH,"%1$s - %2$s",
                                ticket.getAirline(),ticket.getFlightNumber()));
                        dateTextView.setText(ticket.getFlightDate());
                        timeTextView.setText(ticket.getDeparture().getTimeOfDeparture());
                        fromTextVew.setText(ticket.getDeparture().getAirportName());
                        toTextView.setText(ticket.getArrival().getAirportName());

                        if(ticket.isCheckedIn()){
                            checkInImageView.setImageResource(R.drawable.accept_a);
                        } else {
                            checkInImageView.setImageResource(R.drawable.remove);
                        }
                    } else {
                        flightTextView.setText(R.string.not_available);
                        dateTextView.setText(R.string.not_available);
                        timeTextView.setText(R.string.not_available);
                        fromTextVew.setText(R.string.not_available);
                        toTextView.setText(R.string.not_available);
                    }

                } else {
                    flightTextView.setText(R.string.not_available);
                    dateTextView.setText(R.string.not_available);
                    timeTextView.setText(R.string.not_available);
                    fromTextVew.setText(R.string.not_available);
                    toTextView.setText(R.string.not_available);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "onCancelled: ", error.toException());
            }
        });
    }

    public void readAirplaneTickets(Context context, RecyclerView recyclerView,
                                    String name, String surname, boolean toDisplay){

        firebaseRTDBRepository.readAirplaneTickets()
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        GenericTypeIndicator<Map<String, Ticket>> ticketMapGenericTypeIndicator =
                                new GenericTypeIndicator<Map<String, Ticket>>() {};

                        if(snapshot.exists()) {
                            Map<String, Ticket> tickets = snapshot.getValue(ticketMapGenericTypeIndicator);
                            if(toDisplay) {
                                assert tickets != null;
                                recyclerView.setAdapter(new TicketAdapter(tickets,name,surname));
                                recyclerView.setLayoutManager(new LinearLayoutManager(context));

                            }
                        } else {
                            Log.e(TAG, "onDataChange: no ticket data existing in the database");
                            Toast.makeText(context, "Unable to get your previous flight tickets",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(context, "Unable to get your previous flight tickets",
                                Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "onCancelled: ", error.toException());
                    }
                });
    }

    public void readAirlineData(Context context, RecyclerView recyclerView,
                                FlightPreference flightPreference, String airlineName,
                                String bookingDate){

       firebaseRTDBRepository.readAirlineData(airlineName)
               .addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {

               if(snapshot.exists()){
                   GenericTypeIndicator<Map<String, List<Flight>>> mapGenericTypeIndicator =
                           new GenericTypeIndicator<Map<String, List<Flight>>>() {};
                   Map<String, List<Flight>> flightSchedules =
                           snapshot.child("flight_statuses").getValue(mapGenericTypeIndicator);

                   assert flightSchedules != null;
                   recyclerView.setAdapter(
                           new FlightAdapter(flightSchedules,bookingDate,flightPreference));
                   recyclerView.setLayoutManager(new LinearLayoutManager(context));

               } else {
                   Log.d(TAG, "onDataChange: snapshot does not exist");
               }
           }
           @Override
           public void onCancelled(@NonNull DatabaseError error) {
               Log.e(TAG, "onCancelled readAirlineData: ", error.toException());
           }
       });
    }
}
