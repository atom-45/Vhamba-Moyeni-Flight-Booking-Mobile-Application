package com.atom.flightbookingapplication.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@IgnoreExtraProperties
@Entity(tableName = "flight_preferences")
public class FlightPreference implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int flightPreferenceId;

    @ColumnInfo(name = "first_airport")
    private String first_airport;

    @ColumnInfo(name = "second_airport")
    private String second_airport;

    @ColumnInfo(name = "third_airport")
    private String third_airport;

    @ColumnInfo(name = "class")
    private String airplane_class;

    @ColumnInfo(name = "seat_position")
    private String seat_position;

    @ColumnInfo(name = "food_option")
    private String food_option;

    @ColumnInfo(name = "number_of_bags")
    private String number_of_bags;

    public FlightPreference(){}
    public FlightPreference( String first_airport, String second_airport, String third_airport,
                             String airplane_class, String seat_position, String food_option,
                             String number_of_bags) {

        this.first_airport = first_airport;
        this.second_airport = second_airport;
        this.third_airport = third_airport;
        this.airplane_class = airplane_class;
        this.seat_position = seat_position;
        this.food_option = food_option;
        this.number_of_bags = number_of_bags;
    }

    @Exclude
    public int getFlightPreferenceId() {
        return flightPreferenceId;
    }

    public void setFlightPreferenceId(int flightPreferenceId) {
        this.flightPreferenceId = flightPreferenceId;
    }

    @PropertyName("first_airport")
    public String getFirst_airport() {
        return first_airport;
    }

    public void setFirst_airport(String first_airport) {
        this.first_airport = first_airport;
    }

    @PropertyName("second_airport")
    public String getSecond_airport() {
        return second_airport;
    }

    public void setSecond_airport(String second_airport) {
        this.second_airport = second_airport;
    }

    @PropertyName("third_airport")
    public String getThird_airport() {
        return third_airport;
    }

    public void setThird_airport(String third_airport) {
        this.third_airport = third_airport;
    }

    @PropertyName("airplane_class")
    public String getAirplane_class() {
        return airplane_class;
    }

    public void setAirplane_class(String airplane_class) {
        this.airplane_class = airplane_class;
    }

    @PropertyName("seat_position")
    public String getSeat_position() {
        return seat_position;
    }

    public void setSeat_position(String seat_position) {
        this.seat_position = seat_position;
    }

    @PropertyName("food_option")
    public String getFood_option() {
        return food_option;
    }

    public void setFood_option(String food_option) {
        this.food_option = food_option;
    }

    @PropertyName("number_of_bags")
    public String getNumber_of_bags() {
        return number_of_bags;
    }

    public void setNumber_of_bags(String number_of_bags) {
        this.number_of_bags = number_of_bags;
    }

    @Exclude
    @Ignore
    public Map<String, Object> toMap(){
        Map<String, Object> result = new HashMap<>();
        result.put("first_airport", first_airport);
        result.put("second_airport", second_airport);
        result.put("third_airport", third_airport);
        result.put("airplane_class", airplane_class);
        result.put("seat_position", seat_position);
        result.put("food_option", food_option);
        result.put("number_of_bags", number_of_bags);

        return result;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FlightPreference)) return false;
        FlightPreference that = (FlightPreference) o;
        return getFlightPreferenceId() == that.getFlightPreferenceId() &&
                getFirst_airport().equals(that.getFirst_airport()) &&
                getSecond_airport().equals(that.getSecond_airport()) &&
                getThird_airport().equals(that.getThird_airport()) &&
                getAirplane_class().equals(that.getAirplane_class()) &&
                getSeat_position().equals(that.getSeat_position()) &&
                getFood_option().equals(that.getFood_option()) &&
                getNumber_of_bags().equals(that.getNumber_of_bags());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFlightPreferenceId(), getFirst_airport(), getSecond_airport(), getThird_airport(), getAirplane_class(), getSeat_position(), getFood_option(), getNumber_of_bags());
    }

    @NonNull
    @Override
    public String toString() {
        return "FlightPreference{" +
                "firstAirport='" + first_airport + '\'' +
                ", secondAirport='" + second_airport + '\'' +
                ", thirdAirport='" + third_airport + '\'' +
                ", airplaneClass='" + airplane_class + '\'' +
                ", seatPosition='" + seat_position + '\'' +
                ", foodOption='" + food_option + '\'' +
                ", numberOfBags='" + number_of_bags + '\'' +
                '}';
    }
}
