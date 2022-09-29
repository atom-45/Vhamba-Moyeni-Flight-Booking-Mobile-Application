package com.atom.flightbookingapplication.databases;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.atom.flightbookingapplication.daos.UserDAO;
import com.atom.flightbookingapplication.models.Card;
import com.atom.flightbookingapplication.models.FlightPreference;
import com.atom.flightbookingapplication.models.User;

@Database(entities = {User.class, Card.class, FlightPreference.class}, version = 1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {
    private static volatile UserDatabase instance;

    public static UserDatabase getInstance(final Context context){
        if(instance==null){
            synchronized (UserDatabase.class){
                if(instance==null){
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            UserDatabase.class,"user_database").build();
                }
            }
        }
        return instance;
    }

    public abstract UserDAO userDAO();
}
