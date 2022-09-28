package com.atom.flightbookingapplication.repositories;


import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import com.atom.flightbookingapplication.daos.UserDAO;
import com.atom.flightbookingapplication.databases.UserDatabase;
import com.atom.flightbookingapplication.models.Card;
import com.atom.flightbookingapplication.models.FlightPreference;
import com.atom.flightbookingapplication.models.User;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;


public class UserRepository {

    private final UserDAO userDAO;


    public UserRepository(@NonNull Application application){
        this.userDAO = UserDatabase.getInstance(application.getApplicationContext()).userDAO();
    }

    public Observable<List<User>> getAllUsers(){
        return userDAO.getAllUsers();
    }

    public Completable deleteAllUsers() { return userDAO.deleteAllUsers(); }

    public Completable updateUserDetails(String name, String surname, String gender,
                                         String title, String email, String password,
                                         String phoneNumber, String dateOfBirth){
        return userDAO.updateUserDetails(name, surname, gender, title, email, password,
                phoneNumber, dateOfBirth);
    }

    public Completable updateUserCardDetails(String cardNumber, Integer cvv, String expiryDate){
        return userDAO.updateUserCardDetails(cardNumber, cvv, expiryDate);
    }
    public Completable updateUserFlightPreference(String firstAirport, String secondAirport,
                                                  String thirdAirport, String airplaneClass,
                                                  String seatPosition, String foodOption,
                                                  String numberOfBags){
        return userDAO.updateUserFlightPreferences(firstAirport, secondAirport, thirdAirport,
                airplaneClass, seatPosition, foodOption, numberOfBags);

    }
    public Completable updateCardDetails(Card card){
        return userDAO.updateCardDetails(card);
    }

    public Completable updateFlightPreference(FlightPreference flightPreference){
        return  userDAO.updateFlightPreference(flightPreference);
    }

    public Completable deleteUserByName(String name){
        return userDAO.deleteUserByName(name);
    }

    public Completable addUser(User user){
        return userDAO.insertUser(user);
    }

    public Completable addCard(Card card){
        return userDAO.insertCard(card);
    }


}
