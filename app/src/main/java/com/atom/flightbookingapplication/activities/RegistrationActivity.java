package com.atom.flightbookingapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;


import com.atom.flightbookingapplication.databinding.ActivityRegistrationBinding;
import com.atom.flightbookingapplication.models.Constants;
import com.google.android.material.textfield.TextInputEditText;

import java.time.LocalDate;


public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityRegistrationBinding registrationBinding =
                ActivityRegistrationBinding.inflate(getLayoutInflater());
        setContentView(registrationBinding.getRoot());

        AutoCompleteTextView titleSpinner = registrationBinding.titles;
        AutoCompleteTextView genderSpinner = registrationBinding.genderSelection;
        TextInputEditText fullNameEditText = registrationBinding.fullName;
        TextInputEditText surnameEditText = registrationBinding.surname;
        TextInputEditText phoneNumberEditText = registrationBinding.phoneEditText;
        TextInputEditText passwordEditText = registrationBinding.password;
        TextInputEditText emailEditText = registrationBinding.emailEdittext;
        TextInputEditText dayEditText = registrationBinding.day;
        TextInputEditText monthEditText = registrationBinding.month;
        TextInputEditText yearEditText = registrationBinding.year;



        ArrayAdapter<String> titleArrayAdapter =
                new ArrayAdapter<>(getApplicationContext(),
                        com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                        Constants.titles());

        ArrayAdapter<String> genderArrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                Constants.genders());

        assert titleSpinner != null;
        assert genderSpinner != null;
        titleSpinner.setAdapter(titleArrayAdapter);
        genderSpinner.setAdapter(genderArrayAdapter);


        registrationBinding.nextMaterialButton.setOnClickListener(view -> {
            Intent registrationIntent = new Intent(this,FlightPreferenceActivity.class);
            String fullName;
            String surname;
            String phoneNumber;
            String email;
            String password;
            String day;
            String month;
            String year;
            String title;
            String gender;

            if((yearEditText.getText()!=null)
                    && (monthEditText.getText()!=null)
                    && (dayEditText.getText()!=null)
                    && (phoneNumberEditText.getText()!=null)
                    && (fullNameEditText.getText()!=null)
                    && (surnameEditText.getText()!=null)
                    && (emailEditText.getText()!=null)
                    && (passwordEditText.getText() != null)
                    && (titleSpinner.getText() != null)
                    && (genderSpinner.getText() != null)){

                fullName = fullNameEditText.getText().toString();
                surname = surnameEditText.getText().toString();
                phoneNumber = phoneNumberEditText.getText().toString();
                email = emailEditText.getText().toString();
                password = passwordEditText.getText().toString();
                day = dayEditText.getText().toString();
                month = monthEditText.getText().toString();
                year = yearEditText.getText().toString();
                title = titleSpinner.getText().toString();
                gender = genderSpinner.getText().toString();

                if ((email.matches(Constants.EMAIL_REGEX)||validateDateOfBirth(day,month,year))&&
                        (password.length()>6)) {
                    registrationIntent.putExtra(Constants.FULL_NAME, fullName);
                    registrationIntent.putExtra(Constants.SURNAME, surname);
                    registrationIntent.putExtra(Constants.PHONE_NUMBER, phoneNumber);
                    registrationIntent.putExtra(Constants.EMAIL, email);
                    registrationIntent.putExtra(Constants.PASSWORD, password);
                    registrationIntent.putExtra(Constants.DAY, day);
                    registrationIntent.putExtra(Constants.MONTH, month);
                    registrationIntent.putExtra(Constants.YEAR, year);
                    registrationIntent.putExtra(Constants.TITLE, title);
                    registrationIntent.putExtra(Constants.GENDER, gender);
                    startActivity(registrationIntent);
                    finish();
                } else {
                    Toast.makeText(this, "Invalid email, date of birth or "
                                    + "password length is less than 6.\n"
                                    + "Please enter a valid email address, date of birth or password.",
                            Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(this, "Some fields are empty, please complete" +
                                " the registration form",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateDateOfBirth(String day, String month, String year){
        int currentYear = LocalDate.now().getYear();

        int dayNumber;
        int monthNumber;
        int yearNumber;

        if(day.equals("")||month.equals("")||year.equals("")){
            return false;
        } else {
            dayNumber = Integer.parseInt(day);
            monthNumber = Integer.parseInt(month);
            yearNumber = Integer.parseInt(year);
        }

        if(dayNumber<0||dayNumber>31){
            return false;
        } else if(monthNumber<1||monthNumber>12){
            return false;
        } else {
            return (yearNumber >= 1900) && (yearNumber <= currentYear);
        }
    }
}