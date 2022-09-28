package com.atom.flightbookingapplication.daos;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.atom.flightbookingapplication.models.Card;
import com.atom.flightbookingapplication.models.FlightPreference;
import com.atom.flightbookingapplication.models.User;

import java.util.List;

import dagger.Provides;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

@Dao
public interface UserDAO {

    @Query("SELECT * FROM users")
    Observable<List<User>> getAllUsers();


    @Query("DELETE FROM users")
    Completable deleteAllUsers();

    @Query("DELETE FROM users WHERE name = :userName")
    Completable deleteUserByName(String userName);

    @Query("UPDATE users SET " +
            "name = :userName, " +
            "surname = :userSurname, " +
            "gender = :userGender, " +
            "title = :userTitle, " +
            "email = :userEmail, " +
            "password = :userPassword, " +
            "phone_number = :phoneNumber, " +
            "date_of_birth = :dateOfBirth")
    Completable updateUserDetails(String userName,String userSurname,
                                  String userGender,String userTitle,
                                  String userEmail, String userPassword,
                                  String phoneNumber, String dateOfBirth);

    @Query("UPDATE users SET card_number = :cardNumber, cvv = :cardCVV, " +
            "expiry_date = :cardExpiryDate")
    Completable updateUserCardDetails(String cardNumber, Integer cardCVV, String cardExpiryDate);


    @Query("UPDATE users SET " +
            "first_airport = :firstAirport, " +
            "second_airport = :secondAirport, " +
            "third_airport = :thirdAirport, " +
            "class = :airplaneClass, " +
            "seat_position = :seatPosition, " +
            "food_option = :foodOption, " +
            "number_of_bags = :numberOfBags")
    Completable updateUserFlightPreferences(String firstAirport, String secondAirport,
                                            String thirdAirport, String airplaneClass,
                                            String seatPosition, String foodOption,
                                            String numberOfBags);


    @Update(entity = Card.class)
    Completable updateCardDetails(Card card);

    @Update(entity = FlightPreference.class)
    Completable updateFlightPreference(FlightPreference flightPreference);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertUser(User user);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertCard(Card card);

}
