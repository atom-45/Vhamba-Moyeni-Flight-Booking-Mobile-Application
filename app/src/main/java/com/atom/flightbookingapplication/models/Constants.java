package com.atom.flightbookingapplication.models;

import androidx.annotation.NonNull;

import com.atom.flightbookingapplication.R;

import java.util.ArrayList;
import java.util.List;

public class Constants {

    public static String FULL_NAME = "FULL_NAME";
    public static String SURNAME = "SURNAME";
    public static String PHONE_NUMBER = "PHONE_NUMBER";
    public static String PASSWORD = "PASSWORD";
    public static String EMAIL = "EMAIL";
    public static String DAY = "DAY";
    public static String MONTH = "MONTH";
    public static String YEAR = "YEAR";
    public static String TITLE = "TITLE";
    public static String GENDER = "GENDER";
    public static String EMAIL_REGEX = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    public static String AIRLINE_NAME = "AIRLINE_NAME";
    public static String SERIALIZABLE_FLIGHT = "SERIALIZABLE_FLIGHT";
    public static String SERIALIZABLE_TICKET = "SERIALIZABLE_TICKET";
    public static String TICKET_PRICE = "TICKET_PRICE";
    public static String BOOKING_DATE = "BOOKING_DATE";
    public static String TICKET_KEY = "TICKET_KEY";
    public static String TIME_CHECK_IN = "TIME_CHECK_IN";

    public static final double BOMBARDIER_FUEL_BURN_RATE = 0.359; //measured in Litres per second
    public  static final double AIRBUS_FUEL_BURN_RATE = 2.202;
    public  static final double BOEING_FUEL_BURN_RATE = 4.000;
    public  static final double EMBRAER_FUEL_BURN_RATE = 0.667;

    public  static final double BOMBARDIER_SEAT_CAPACITY = 60; //measured in Litres
    public  static final double AIRBUS_SEAT_CAPACITY = 260; //Litres
    public  static final double BOEING_SEAT_CAPACITY = 189;
    public  static final double EMBRAER_SEAT_CAPACITY = 98;

    public static final double AVERAGE_PASSENGER_MASS = 70; //measured in kilograms

    public static final double KEROSENE_FUEL_DENSITY = 820; //kerosene - measured in kg per cubic metre
    public static final double KEROSENE_FUEL_PRICE_IN_DOLLARS = 1.087;

    public static final double USD_TO_ZAR_CURRENCY_EXCHANGE = 16.20;//exchange rate between the Rand and US Dollar

    @NonNull
    public static List<String> weekDates(){
        List<String> weekDates = new ArrayList<>();
        weekDates.add("2022-12-28");
        weekDates.add("2022-12-29");
        weekDates.add("2022-12-30");
        weekDates.add("2022-12-31");
        weekDates.add("2023-01-01");
        weekDates.add("2023-01-02");
        weekDates.add("2023-01-03");
        weekDates.add("2023-01-04");
        weekDates.add("2023-01-05");
        weekDates.add("2023-01-06");
        weekDates.add("2023-01-07");
        weekDates.add("2023-01-08");
        weekDates.add("2023-01-09");
        weekDates.add("2023-01-10");
        return weekDates;
    }

    public static List<Shop> shops(){
        List<Shop> shops = new ArrayList<>();

        shops.add(new Shop("McDonald","09:30",
                "20:30", "Finding comfort and positivity within food",
                R.drawable.mcdonalds_meal,R.drawable.mcdonalds_logo));

        shops.add(new Shop("Nandos","10:30",
                "19:30","Bringing flames into your life",
                R.drawable.nandos_meal,R.drawable.nandos_logo_2));

        shops.add(new Shop("KFC","07:00","19:30",
                "Bringing joy in families\nFinger Licking Good",
                R.drawable.kfc_meal_2, R.drawable.kfc_logo));

        shops.add(new Shop("Spurs","10:30",
                "21:00","Best flame grill food at your doorstep",
                R.drawable.spurs_food_4,R.drawable.spurs_logo));

        shops.add(new Shop("Wimpy","08:00","19:30",
                "Delicious, bright mouth-watering breakfast to make your morning",
                R.drawable.wimpy_breakfast,R.drawable.wimpy_logo_2));

        shops.add(new Shop("Mugg and Bean","08:00","18:00",
                "Serving breakfast on the go",R.drawable.mugg_and_bean_meal_3,
                R.drawable.mugg_and_bean_logo));

        return shops;
    }

    @NonNull
    public static List<String> choices(){
        List<String> choices = new ArrayList<>();
        choices.add("Yes");
        choices.add("No");

        return choices;
    }

    @NonNull
    public static List<String> titles(){
        List<String> titles = new ArrayList<>();

        titles.add("Mr");
        titles.add("Mrs");
        titles.add("Miss");
        titles.add("Dr.");
        titles.add("Prof.");

        return titles;
    }

    @NonNull
    public static List<String> genders(){
        List<String> genders = new ArrayList<>();

        genders.add("Male");
        genders.add("Female");
        genders.add("N/A");

        return genders;
    }

    @NonNull
    public static List<String> airports(){
        List<String> airports = new ArrayList<>();

        airports.add("O.R Tambo Intl. Airport");
        airports.add("Cape Town Intl. Airport");
        airports.add("King Shaka Intl. Airport");
        airports.add("George Airport");
        airports.add("Lanseria Airport");
        airports.add("Chief Dawid Stuurman Intl. Airport");
        airports.add("Bram Fischer Intl. Airport");
        airports.add("Polokwane Intl. Airport");

        return airports;
    }

    @NonNull
    public static List<String> seatPositions(){

        List<String> positions = new ArrayList<>();

        positions.add("A");
        positions.add("B");
        positions.add("C");
        positions.add("D");
        positions.add("E");
        positions.add("F");

        return positions;
    }

    @NonNull
    public static List<String> classes(){
        List<String> airplaneClass = new ArrayList<>();
        airplaneClass.add("Economy");
        airplaneClass.add("Business");

        return airplaneClass;
    }
}

//bad methods or problematics
  /*for (DataSnapshot ds : snapshot.getChildren()) {
                       if (Objects.equals(ds.child("flight_number").getValue(), "4Z813")) {
                           String childKey = ds.getKey();
                           Long seatsAvailable = (Long) ds.child("seats_available").getValue();
                           String flightNumber = ds.child("flight_number").getValue(String.class);

                           if(seatsAvailable!=null&&seatsAvailable>0){
                               Log.d(TAG, "onDataChange: seats " + ds.child("seats_available").getValue());
                               Log.d(TAG, "onDataChange: key "+childKey);
                               Log.d(TAG, "onDataChange: flight_number "+flightNumber);
                               //airlink/flight_statuses/2022-08-13/45
                               databaseReference.child(airlineName+"/flight_statuses/"
                                               +flightDate+"/"+childKey+"/seats_available")
                                       .setValue(seatsAvailable-1);
                           } else {
                               Log.d(TAG, "onDataChange: no seats available");
                           }
                       }
                   }**/