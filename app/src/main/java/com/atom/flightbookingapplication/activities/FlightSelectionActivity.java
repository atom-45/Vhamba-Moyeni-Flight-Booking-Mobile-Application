package com.atom.flightbookingapplication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import android.widget.TextView;
import android.widget.Toast;

import com.atom.flightbookingapplication.R;
import com.atom.flightbookingapplication.models.Constants;
import com.atom.flightbookingapplication.models.FlightPreference;
import com.atom.flightbookingapplication.viewmodels.FirebaseRTDBViewModel;
import com.atom.flightbookingapplication.viewmodels.UserViewModel;

import java.time.LocalDate;
import java.util.Locale;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FlightSelectionActivity extends AppCompatActivity {

    private CompositeDisposable compositeDisposable;
    private Disposable disposable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_selection);

        RecyclerView flightSelectionRecyclerView = findViewById(R.id.flightSelection_recyclerView);
        AutoCompleteTextView dateSelectionAutoTextView = findViewById(R.id.dateSelection_textView);
        TextView dateTextView = findViewById(R.id.currentFlightDate_textview_b);
        String currentLocalDate = LocalDate.now().toString();
        LocalDate localDate = LocalDate.parse(currentLocalDate);
        compositeDisposable = new CompositeDisposable();

        ArrayAdapter<String> datesAdapter = new ArrayAdapter<>(getApplicationContext(),
                com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                Constants.weekDates());

        dateSelectionAutoTextView.setAdapter(datesAdapter);
        dateTextView.setText(String.format(Locale.ENGLISH,"%1$s, %2$d %3$s %4$d",
                localDate.getDayOfWeek().toString(),
                localDate.getDayOfMonth(),
                localDate.getMonth(),localDate.getYear()));


        UserViewModel userViewModel = new ViewModelProvider(this)
                .get(UserViewModel.class);

        FirebaseRTDBViewModel rtdbViewModel = new ViewModelProvider(this,
                ViewModelProvider.Factory.from(FirebaseRTDBViewModel.initializer))
                .get(FirebaseRTDBViewModel.class);

        String airlineName = getIntent().getStringExtra(Constants.AIRLINE_NAME)
                .toLowerCase();

        disposable = userDisposable(userViewModel, rtdbViewModel,
                flightSelectionRecyclerView, airlineName, currentLocalDate);

        compositeDisposable.add(disposable);

        findViewById(R.id.dateSearch_materialButton).setOnClickListener(view -> {
            String selectedDate = dateSelectionAutoTextView.getText().toString();
            if(selectedDate.equals("")){
                Toast.makeText(this, "date not selected", Toast.LENGTH_SHORT).show();
            } else {
                LocalDate selectedLocalDate = LocalDate.parse(selectedDate);
                dateTextView.setText(String.format(Locale.ENGLISH,"%1$s, %2$d %3$s %4$d",
                        selectedLocalDate.getDayOfWeek().toString(),
                        selectedLocalDate.getDayOfMonth(),
                        selectedLocalDate.getMonth(),localDate.getYear()));

                disposable = userDisposable(userViewModel, rtdbViewModel,
                         flightSelectionRecyclerView, airlineName, selectedDate);

                compositeDisposable.add(disposable);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }

    public Disposable userDisposable(UserViewModel userViewModel,
                                     FirebaseRTDBViewModel rtdbViewModel,
                                     RecyclerView recyclerView,
                                     String airlineName,
                                     String currentDate){
        return userViewModel.getAllUsers()
                .subscribeOn(Schedulers.io())
                .doOnNext(users -> {
                    FlightPreference currentUserFlightPreference =
                            users.get(users.size()-1).getFlightPreference();
                    rtdbViewModel.readAirlineData(this,recyclerView,
                            currentUserFlightPreference, airlineName, currentDate);
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }
}