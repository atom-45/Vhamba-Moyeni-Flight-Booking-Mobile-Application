package com.atom.flightbookingapplication.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
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
@Entity(tableName = "cards")
public class Card {

    @PrimaryKey(autoGenerate = true)
    private int cardId;

    @ColumnInfo(name = "card_number")
    private String cardNumber;

    @ColumnInfo(name = "cvv")
    private Integer cvv;

    @ColumnInfo(name = "expiry_date")
    private String expiryDate;

    public Card(String cardNumber, Integer cvv, String expiryDate) {
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.expiryDate = expiryDate;
    }

    @Exclude
    public int getCardId(){
        return cardId;
    }

    @PropertyName("card_number")
    public String getCardNumber() {
        return cardNumber;
    }

    @PropertyName("cvv")
    public Integer getCvv() {
        return cvv;
    }

    @PropertyName("expiry_date")
    public String getExpiryDate() {
        return expiryDate;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setCvv(Integer cvv) {
        this.cvv = cvv;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card)) return false;
        Card card = (Card) o;
        return getCardNumber().equals(card.getCardNumber()) &&
                getCvv().equals(card.getCvv()) &&
                getExpiryDate().equals(card.getExpiryDate());
    }
    @Exclude
    @Ignore
    public Map<String, Object> toMap(){
        Map<String, Object> result = new HashMap<>();

        result.put("card_number", cardNumber);
        result.put("cvv", cvv);
        result.put("expiry_date", expiryDate);

        return result;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCardNumber(), getCvv(), getExpiryDate());
    }

    @NonNull
    @Override
    public String toString() {
        return "Card{" +
                "cardNumber='" + cardNumber + '\'' +
                ", cvv=" + cvv +
                ", expiryDate='" + expiryDate + '\'' +
                '}';
    }
}
