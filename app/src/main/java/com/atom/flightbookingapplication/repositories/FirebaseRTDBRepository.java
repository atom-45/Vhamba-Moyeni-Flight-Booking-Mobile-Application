package com.atom.flightbookingapplication.repositories;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.atom.flightbookingapplication.models.Ticket;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.Transaction;


import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class FirebaseRTDBRepository {

    private final DatabaseReference databaseReference;
    private final static String TAG = "FirebaseRTDBRepository";

    @Inject
    public FirebaseRTDBRepository(DatabaseReference databaseReference){
        this.databaseReference = databaseReference;
    }

    public Task<Void> addCustomerQueryConcern(Map<String, Object> customerQuery){
        String key = databaseReference.push().getKey();
        return  databaseReference.child("complaints/"+key).updateChildren(customerQuery);
    }


    public Task<Void> saveUserToDatabase(Map<String, Object> userMap) {
        return databaseReference.child("users").updateChildren(userMap);
    }

    public Task<Void> updateUser(Map<String, Object> userMap, String userKey){
        return databaseReference.child("users/"+userKey).updateChildren(userMap);
    }

    public Task<Void> updateUserFlightPreferences(Map<String, Object> flightPrefUpdate, String userKey)
    {
        return databaseReference.child("users/"+userKey+"/flight_preferences")
                .updateChildren(flightPrefUpdate);
    }
    public Task<Void> updateUserCardDetails(Map<String, Object> cardUpdate, String userKey){
        return databaseReference.child("users/"+userKey+"/banking_details")
                .updateChildren(cardUpdate);
    }

    public boolean deleteUserFromDatabase(String userName){
        return databaseReference.child(userName).removeValue().isSuccessful();
    }

    public Task<Void> saveUserAirplaneTicket(Ticket bookingTicket){
        return databaseReference.child("tickets").push().setValue(bookingTicket);
    }

    public Task<Void> updateUserCheckInValue(String key, boolean value){
        return  databaseReference.child("tickets/"+key+"/checked_in").setValue(value);
    }

    public void updateAirlineFlightSeats(String airlineName, String flightDate,
                                         String selectedFlightNumber){

        databaseReference.child(airlineName+"/flight_statuses/"+flightDate)
                .runTransaction(new Transaction.Handler() {
                    @NonNull
                    @Override
                    public Transaction.Result doTransaction(@NonNull MutableData currentData) {

                        if(currentData.hasChildren()){
                            currentData.getChildren().forEach(mutableData -> {

                                String flightNumber = mutableData.child("flight_number")
                                        .getValue(String.class);

                                assert flightNumber != null;
                                if (flightNumber.equals(selectedFlightNumber)){
                                    Long seatsAvailable = mutableData.child("seats_available")
                                            .getValue(Long.class);

                                    if (seatsAvailable!=null&&seatsAvailable>0){
                                        mutableData.child("seats_available")
                                                .setValue(ServerValue.increment(-1));
                                    }
                                }
                            });
                        }
                        return Transaction.success(currentData);
                    }

                    @Override
                    public void onComplete(@Nullable DatabaseError error, boolean committed,
                                           @Nullable DataSnapshot currentData) {

                        Log.d(TAG, "onComplete: currentData - "+currentData);

                    }
                });

    }

    public DatabaseReference readAirplaneTickets(){
        return databaseReference.child("tickets");
    }

    public DatabaseReference readAirlineData(String airlineName){
        return databaseReference.child(airlineName);
    }
}
