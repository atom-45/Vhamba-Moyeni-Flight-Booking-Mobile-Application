package com.atom.flightbookingapplication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.atom.flightbookingapplication.R;
import com.atom.flightbookingapplication.models.Card;
import com.atom.flightbookingapplication.models.Constants;
import com.atom.flightbookingapplication.models.FlightPreference;
import com.atom.flightbookingapplication.models.User;
import com.atom.flightbookingapplication.viewmodels.FirebaseRTDBViewModel;
import com.atom.flightbookingapplication.viewmodels.UserViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Map;
import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class EditFlightPreferenceActivity extends AppCompatActivity {

    private CompositeDisposable compositeDisposable;
    private static String TAG = "EditFlightPreferenceActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_preference);

        FirebaseRTDBViewModel rtdbViewModel = new ViewModelProvider(this,
                ViewModelProvider.Factory
                .from(FirebaseRTDBViewModel.initializer))
                .get(FirebaseRTDBViewModel.class);

        UserViewModel userViewModel = new ViewModelProvider(this)
                .get(UserViewModel.class);

        compositeDisposable = new CompositeDisposable();


        AutoCompleteTextView firstAirport = findViewById(R.id.first_airport_selection);
        AutoCompleteTextView secondAirport = findViewById(R.id.second_airport_selection);
        AutoCompleteTextView thirdAirport = findViewById(R.id.third_airport_selection);
        AutoCompleteTextView seatSelection = findViewById(R.id.seat_selection);
        AutoCompleteTextView foodSelection = findViewById(R.id.food_selection);
        AutoCompleteTextView classSelection = findViewById(R.id.class_selection);

        TextInputEditText bagNumberEditText = findViewById(R.id.bagNumber_textInputEditText);
        TextInputEditText cardNumberEditText = findViewById(R.id.cardNumber_textInputEditText);
        TextInputEditText expiryDateEditText = findViewById(R.id.expiryDate_textInputEditText);
        TextInputEditText cvvEditText = findViewById(R.id.cvv_textInputEditText);

        ArrayAdapter<String> firstAdapter = new ArrayAdapter<>(getApplicationContext(),
                com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                Constants.airports());
        ArrayAdapter<String> secondAdapter = new ArrayAdapter<>(getApplicationContext(),
                com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                Constants.airports());
        ArrayAdapter<String> thirdAdapter = new ArrayAdapter<>(getApplicationContext(),
                com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                Constants.airports());
        ArrayAdapter<String> seatAdapter = new ArrayAdapter<>(getApplicationContext(),
                com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                Constants.seatPositions());
        ArrayAdapter<String> foodAdapter = new ArrayAdapter<>(getApplicationContext(),
                com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                Constants.choices());
        ArrayAdapter<String> classAdapter = new ArrayAdapter<>(getApplicationContext(),
                com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                Constants.classes());

        firstAirport.setAdapter(firstAdapter);
        secondAirport.setAdapter(secondAdapter);
        thirdAirport.setAdapter(thirdAdapter);
        seatSelection.setAdapter(seatAdapter);
        foodSelection.setAdapter(foodAdapter);
        classSelection.setAdapter(classAdapter);

        findViewById(R.id.proceed_materialButton).setOnClickListener(view -> {

            String firstAirportEditable = firstAirport.getText().toString();
            String secondAirportEditable = secondAirport.getText().toString();
            String thirdAirportEditable = thirdAirport.getText().toString();
            String seatSelectionEditable = seatSelection.getText().toString();
            String foodSelectionEditable = foodSelection.getText().toString();
            String classSelectionEditable = classSelection.getText().toString();

            String bagNumberEditable = Objects.requireNonNull(bagNumberEditText.getText())
                    .toString();

            String cardNumberEditable = Objects.requireNonNull(cardNumberEditText.getText())
                    .toString();

            String expiryDateEditable = Objects.requireNonNull(expiryDateEditText.getText())
                    .toString();

            String cvvEditable = Objects.requireNonNull(cvvEditText.getText()).toString();

            if((!firstAirportEditable.equals(""))&&
                    (!secondAirportEditable.equals(""))&&
                    (!thirdAirportEditable.equals(""))&&
                    (!seatSelectionEditable.equals(""))&&
                    (!foodSelectionEditable.equals(""))&&
                    (!classSelectionEditable.equals(""))&&
                    (!bagNumberEditable.equals(""))&&
                    (!cardNumberEditable.equals(""))&&
                    (!expiryDateEditable.equals(""))&&
                    (!cvvEditable.equals(""))) {

                FlightPreference userFlightPreference =
                        new FlightPreference(
                                firstAirportEditable,
                                secondAirportEditable,
                                thirdAirportEditable,
                                classSelectionEditable,
                                seatSelectionEditable,
                                foodSelectionEditable,
                                bagNumberEditable);

                Card userCard = new Card(cardNumberEditable, Integer.parseInt(cvvEditable),
                        expiryDateEditable);

                Map<String, Object> flightPreferenceObject = userFlightPreference.toMap();
                Map<String, Object> cardObject = userCard.toMap();

                Disposable disposable = userViewModel.getAllUsers()
                        .subscribeOn(Schedulers.io())
                        .doOnNext(users -> {
                            User user = users.get(users.size()-1);
                            String userKey = user.getName().toLowerCase()
                                    .replace(" ","_")
                                    +"_"+user.getSurname().toLowerCase();

                            userViewModel.updateUserFlightPreferences(firstAirportEditable,
                                    secondAirportEditable,thirdAirportEditable,
                                    classSelectionEditable,seatSelectionEditable,
                                    foodSelectionEditable,bagNumberEditable);
                            userViewModel.updateUserCardDetails(cardNumberEditable
                                    ,Integer.parseInt(cvvEditable),expiryDateEditable);
                            rtdbViewModel.updateUserFlightPreference(flightPreferenceObject,
                                    userKey);
                            rtdbViewModel.updateUserCardDetails(cardObject, userKey);

                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe();

                compositeDisposable.add(disposable);

            } else {
                Toast.makeText(this, "Some fields are missing." +
                        "Please complete all fields.", Toast.LENGTH_SHORT).show();
            }

        });
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        compositeDisposable.clear();
    }
}