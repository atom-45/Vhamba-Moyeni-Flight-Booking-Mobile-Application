package com.atom.flightbookingapplication.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@IgnoreExtraProperties
@Entity(tableName = "users")
public class User {

    @PrimaryKey(autoGenerate = true)
    private int userId;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "surname")
    private String surname;

    @ColumnInfo(name = "gender")
    private String gender;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "password")
    private String password;

    @ColumnInfo(name = "phone_number")
    private String phoneNumbers;

    @ColumnInfo(name = "date_of_birth")
    private String dateOfBirth;

    @Embedded
    private FlightPreference flightPreference;

    @Embedded
    private Card card;

    public User(){}

    public User(String name, String surname,
                String gender, String title, String email,
                String password, String phoneNumbers, String dateOfBirth,
                FlightPreference flightPreference, Card card) {

        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.title = title;
        this.email = email;
        this.password = password;
        this.phoneNumbers = phoneNumbers;
        this.dateOfBirth = dateOfBirth;
        this.flightPreference = flightPreference;
        this.card = card;
    }

    @Exclude
    public int getUserId(){
        return userId;
    }

    @PropertyName("name")
    public String getName() {
        return name;
    }

    @PropertyName("surname")
    public String getSurname() {
        return surname;
    }

    @PropertyName("gender")
    public String getGender() {
        return gender;
    }

    @PropertyName("title")
    public String getTitle() {
        return title;
    }

    @Exclude
    public String getEmail() {
        return email;
    }

    @Exclude
    public String getPassword() {
        return password;
    }

    @PropertyName("phone_number")
    public String getPhoneNumbers() {
        return phoneNumbers;
    }

    @PropertyName("date_of_birth")
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    @PropertyName("flight_preferences")
    public FlightPreference getFlightPreference() {
        return flightPreference;
    }

    @PropertyName("banking_details")
    public Card getCard() {
        return card;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setEmail(String email) { this.email = email; }

    public void setPassword(String password) { this.password = password;}

    public void setPhoneNumbers(String phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public void setFlightPreference(FlightPreference flightPreference) {
        this.flightPreference = flightPreference;
    }

    @Exclude
    @Ignore
    public Map<String, Object> toMap(){
        Map<String, Object> result = new HashMap<>();

        result.put("name", name);
        result.put("surname", surname);
        result.put("gender", gender);
        result.put("title", title);
        result.put("phone_number", phoneNumbers);
        result.put("date_of_birth", dateOfBirth);

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getName().equals(user.getName()) &&
                getSurname().equals(user.getSurname()) &&
                getGender().equals(user.getGender()) &&
                getTitle().equals(user.getTitle()) &&
                getEmail().equals(user.getEmail()) &&
                getPassword().equals(user.getPassword()) &&
                getPhoneNumbers().equals(user.getPhoneNumbers()) &&
                getDateOfBirth().equals(user.getDateOfBirth()) &&
                getFlightPreference().equals(user.getFlightPreference()) &&
                getCard().equals(user.getCard());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getSurname(),
                getGender(), getTitle(), getPhoneNumbers(), getDateOfBirth(),
                getEmail(), getPassword(), getFlightPreference(), getCard());
    }

    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", gender='" + gender + '\'' +
                ", title='" + title + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumbers='" + phoneNumbers + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", flightPreference=" + flightPreference +
                ", card=" + card +
                '}';
    }
}
