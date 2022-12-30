package com.atom.flightbookingapplication.adapters;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class BindingAdapters {

    @BindingAdapter({"departureTime","arrivalTime"})
    public static void setTimeDuration(@NonNull TextView textView, String departTime,
                                       String arriveTime){
        LocalTime departureTime = LocalTime.parse(departTime);
        LocalTime arrivalTime = LocalTime.parse(arriveTime);

        long timeDifference = departureTime.until(arrivalTime, ChronoUnit.MINUTES);
        textView.setText(String.format("%1$s - %2$s, %3$s",
                departTime,arriveTime, flightTimeInString(timeDifference)));


    }
    @BindingAdapter("imageResource")
    public static void setImageResource(ImageView imageView, int imageID){
        imageView.setImageResource(imageID);
    }

    private static String flightTimeInString(long timeDifference){
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
