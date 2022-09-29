package com.atom.flightbookingapplication.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.atom.flightbookingapplication.R;
import com.atom.flightbookingapplication.activities.MainActivity;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;


/**
 * This a flightTimeService class it is a redundant class as it is not officially used in the
 * app. This was me trying to test how to use foreground services by sending a notification, but I
 * realised that there is a significant problem to which I receive or get a Null Pointer Exception of the
 * Ticket object.
 */
public class FlightTimeService extends Service {

    private final String NOTIFICATION_CHANNEL_ID = "NOTIFICATION";
    private String text;


    @Override
    public void onCreate() {
        super.onCreate();

        LocalTime currentLocalTime = LocalTime.now();
        LocalTime laterLocalTime = LocalTime.parse("00:22");
        long difference = currentLocalTime.until(laterLocalTime, ChronoUnit.MINUTES);

        if(difference<5){
            text = "This is a test that the foreground service actually works";
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //Ticket currentTicket = (Ticket) intent.getSerializableExtra(Constants.SERIALIZABLE_TICKET);


       /* LocalTime departureLocalTime = LocalTime.parse(currentTicket.getDeparture().getTimeOfDeparture());
        LocalDateTime currentLocalDateTime = LocalDateTime.now();

        if(!currentTicket.isCheckedIn()){
            String[] flightDateArray = currentTicket.getFlightDate().split(", ");
            String currentDate = currentLocalDateTime.getDayOfMonth()
                    +" "+currentLocalDateTime.getMonth()+" "+currentLocalDateTime.getYear();

            if(flightDateArray[1].equals(currentDate)){
                String text = "Checked in for your upcoming "
                        +currentTicket.getAirline()+" "+currentTicket.getFlightNumber()+" flight";
                long timeDifference = currentLocalTime.until(departureLocalTime, ChronoUnit.MINUTES);
                if (timeDifference<1440){
                    startForeground(2451,createNotificationBuilder(text).build());
                }
            }
        } **/

        startForeground(2451,createNotificationBuilder(text).build());
        return super.onStartCommand(intent, flags, startId);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public NotificationCompat.Builder createNotificationBuilder(String text){

        createNotificationChannel();
        CharSequence title = getString(R.string.flight_check_in_service);

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 343,
                notificationIntent, PendingIntent.FLAG_IMMUTABLE);
        return new NotificationCompat
                .Builder(this, NOTIFICATION_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(R.drawable.flight_d)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

    }

    private void createNotificationChannel(){
        String channelName = "FLIGHT TIME SERVICE ID";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,
                channelName,importance);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.cancel(2451);
        notificationManager.createNotificationChannel(channel);

        //Below is used to display a notification without using a service
        /*NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        int NOTIFICATION_ID = 2451;
        notificationManagerCompat.notify(NOTIFICATION_ID, createNotificationBuilder(text).build());**/

    }
}
