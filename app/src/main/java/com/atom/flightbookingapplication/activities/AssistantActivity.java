package com.atom.flightbookingapplication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.atom.flightbookingapplication.databinding.ActivityAssistantBinding;
import com.atom.flightbookingapplication.models.Constants;
import com.atom.flightbookingapplication.models.Ticket;
import com.atom.flightbookingapplication.models.User;
import com.atom.flightbookingapplication.viewmodels.FirebaseRTDBViewModel;
import com.atom.flightbookingapplication.viewmodels.UserViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AssistantActivity extends AppCompatActivity {

    private CompositeDisposable compositeDisposable;
    private final static String TAG = "AssistantActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAssistantBinding assistantBinding =
                ActivityAssistantBinding.inflate(getLayoutInflater());
        setContentView(assistantBinding.getRoot());

        TextInputEditText customerServiceEditText = assistantBinding.customerServiceEditText;
        ImageView qrCodeImageView = assistantBinding.bagdropQrcodeImageview;

        UserViewModel userViewModel = new ViewModelProvider(this)
                .get(UserViewModel.class);

        FirebaseRTDBViewModel rtdbViewModel = new ViewModelProvider(this,
                ViewModelProvider.Factory.from(FirebaseRTDBViewModel.initializer)).
                get(FirebaseRTDBViewModel.class);

        String userTicketKey = getIntent().getStringExtra(Constants.TICKET_KEY);
        Ticket ticket = (Ticket) getIntent().getSerializableExtra(Constants.SERIALIZABLE_TICKET);

        generateBagDropQRCode(qrCodeImageView, ticket);

        compositeDisposable = new CompositeDisposable();

        assistantBinding.assistantCheckInMaterialButton.setOnClickListener(view -> {

            if(isFlightDateValid(ticket.getFlightDate(),ticket.getDeparture().getTimeOfDeparture())){
                ticket.setCheckedIn(true);
                rtdbViewModel.updateUserCheckInValue(this, userTicketKey,ticket.isCheckedIn());

            } else {
                Toast.makeText(this, "Flight ticket has been used or expired.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        assistantBinding.assistantSubmitMaterialButton.setOnClickListener(view -> {

            String customerQueryMessage =
                    Objects.requireNonNull(customerServiceEditText.getText()).toString();

            if(!customerQueryMessage.equals("")){

                Disposable userDisposable = userViewModel.getAllUsers()
                        .subscribeOn(Schedulers.io())
                        .doOnNext(users -> {
                            User user = users.get(users.size()-1);
                            Map<String, Object> customerQuery = new HashMap<>();
                            customerQuery.put("name_and_surname", ticket.getName()
                                    +" "+ticket.getSurname());
                            customerQuery.put("message", customerQueryMessage);
                            customerQuery.put("email",user.getEmail());
                            customerQuery.put("flight_date",ticket.getFlightDate());
                            customerQuery.put("airline_flight",
                                    ticket.getAirline()+" "+ticket.getFlightNumber());
                            customerQuery.put("time_and_date", LocalDateTime.now().toString());
                            rtdbViewModel.addCustomerQuery(this,customerQuery);
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe();

                compositeDisposable.add(userDisposable);

            } else {
                Toast.makeText(this, "" +
                        "Please write your concern", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
        finish();
    }

    private void generateBagDropQRCode(ImageView imageView, Ticket ticket){

        String text = ticket.getBookingReference()+"--"+ticket.getBookingDate();
        MultiFormatWriter writer = new MultiFormatWriter();
        try {
            int width = 350;
            int height = 350;
            BitMatrix matrix = writer.encode(text, BarcodeFormat.QR_CODE, width, height);

            BarcodeEncoder encoder = new BarcodeEncoder();
            Bitmap bitmap = encoder.createBitmap(matrix);
            imageView.setImageBitmap(bitmap);

        } catch (WriterException e) {
            Log.e(TAG, "generateBagDropQRCode: ",e);
        }
    }

    private boolean isFlightDateValid(String flightDate, String flightTime){

        LocalDate localDate  = LocalDate.now();
        LocalTime localTime = LocalTime.now();
        long timeDifference = localTime.until(LocalTime.parse(flightTime), ChronoUnit.MINUTES);
        String currentDate = String.format(Locale.ENGLISH,"%1$s, %2$d %3$s %4$d",
                localDate.getDayOfWeek().toString(),
                localDate.getDayOfMonth(),
                localDate.getMonth(),localDate.getYear());

        return flightDate.equals(currentDate)&&(timeDifference>0);
    }
}