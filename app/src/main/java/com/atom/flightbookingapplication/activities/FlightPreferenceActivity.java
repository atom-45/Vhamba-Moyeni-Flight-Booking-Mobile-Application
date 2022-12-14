package com.atom.flightbookingapplication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;


import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.atom.flightbookingapplication.databinding.ActivityFlightPreferenceBinding;
import com.atom.flightbookingapplication.models.Card;
import com.atom.flightbookingapplication.models.Constants;
import com.atom.flightbookingapplication.models.FlightPreference;
import com.atom.flightbookingapplication.models.User;
import com.atom.flightbookingapplication.viewmodels.FirebaseAuthenticationViewModel;
import com.atom.flightbookingapplication.viewmodels.FirebaseRTDBViewModel;
import com.atom.flightbookingapplication.viewmodels.UserViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;



public class FlightPreferenceActivity extends AppCompatActivity {

    private static final String TAG = "FlightPreferenceActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityFlightPreferenceBinding activityFlightPreferenceBinding =
                ActivityFlightPreferenceBinding.inflate(getLayoutInflater());
        setContentView(activityFlightPreferenceBinding.getRoot());

        FirebaseRTDBViewModel rtdbViewModel = new ViewModelProvider(this,
               ViewModelProvider.Factory.from(FirebaseRTDBViewModel.initializer))
               .get(FirebaseRTDBViewModel.class);

        FirebaseAuthenticationViewModel authenticationViewModel =  new ViewModelProvider(this,
                ViewModelProvider.Factory.from(FirebaseAuthenticationViewModel.initializer))
                .get(FirebaseAuthenticationViewModel.class);

        UserViewModel userViewModel = new ViewModelProvider(this)
                .get(UserViewModel.class);

        AutoCompleteTextView firstAirport = activityFlightPreferenceBinding.firstAirportSelection;
        AutoCompleteTextView secondAirport = activityFlightPreferenceBinding.secondAirportSelection;
        AutoCompleteTextView thirdAirport = activityFlightPreferenceBinding.thirdAirportSelection;
        AutoCompleteTextView seatSelection = activityFlightPreferenceBinding.seatSelection;
        AutoCompleteTextView foodSelection = activityFlightPreferenceBinding.foodSelection;
        AutoCompleteTextView classSelection = activityFlightPreferenceBinding.classSelection;

        TextInputEditText bagNumberEditText = activityFlightPreferenceBinding.bagNumberTextInputEditText;
        TextInputEditText cardNumberEditText = activityFlightPreferenceBinding.cardNumberTextInputEditText;
        TextInputEditText expiryDateEditText = activityFlightPreferenceBinding.expiryDateTextInputEditText;
        TextInputEditText cvvEditText = activityFlightPreferenceBinding.cvvTextInputEditText;

        ProgressBar progressBar = activityFlightPreferenceBinding.flightPrefProgressBar;

        String email = getIntent().getStringExtra(Constants.EMAIL);
        String password = getIntent().getStringExtra(Constants.PASSWORD);
        String phoneNumber = getIntent().getStringExtra(Constants.PHONE_NUMBER);
        String title = getIntent().getStringExtra(Constants.TITLE);
        String gender = getIntent().getStringExtra(Constants.GENDER);
        String fullName = getIntent().getStringExtra(Constants.FULL_NAME);
        String surname = getIntent().getStringExtra(Constants.SURNAME);
        String day = getIntent().getStringExtra(Constants.DAY);
        String month = getIntent().getStringExtra(Constants.MONTH);
        String year = getIntent().getStringExtra(Constants.YEAR);
        String dateOfBirth = day+"/"+month+"/"+year;

        
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


        activityFlightPreferenceBinding.proceedMaterialButton.setOnClickListener(

                view -> {

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

                        User user = new User(fullName,surname,gender,title, email, password,
                                phoneNumber, dateOfBirth,userFlightPreference,userCard);

                        String userKey = fullName.toLowerCase().replace(" ","_")
                                +"_"+surname.toLowerCase();

                        Map<String, Object> userObject = new HashMap<>();
                        userObject.put(userKey,user);

                        authenticationViewModel.createUserWithEmailAndPassword(this,
                                progressBar, email, password, userObject, rtdbViewModel);

                        userViewModel.insertUser(user);

                    } else {
                        Toast.makeText(this, "Some fields are missing." +
                                "Please complete all fields.", Toast.LENGTH_SHORT).show();
                    }
                })
        ;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}