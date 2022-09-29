package com.atom.flightbookingapplication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.splashscreen.SplashScreen;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.atom.flightbookingapplication.R;
import com.atom.flightbookingapplication.models.Constants;
import com.atom.flightbookingapplication.viewmodels.FirebaseAuthenticationViewModel;
import com.google.android.material.textfield.TextInputEditText;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class LoginActivity extends AppCompatActivity {

    private boolean isAndroidReady = false;
    private final int REQUEST_PERMISSIONS_CODE = 343;
    private final static String TAG = "LoginActivity";
    private CompositeDisposable compositeDisposable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen.installSplashScreen(this);
        final View content = findViewById(android.R.id.content);
        content.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener(){
            @Override
            public boolean onPreDraw() {
                if (isAndroidReady){
                    content.getViewTreeObserver().removeOnPreDrawListener(this);
                }
                dropSplashScreen();
                return false;
            }
        });

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        compositeDisposable = new CompositeDisposable();
        TextInputEditText emailText = findViewById(R.id.login_email_edittext);
        TextInputEditText passwordText = findViewById(R.id.login_password_edittext);
        ProgressBar progressBar = findViewById(R.id.login_progressBar);

        FirebaseAuthenticationViewModel authenticationViewModel =
                new ViewModelProvider(this,
                      ViewModelProvider.Factory.from(FirebaseAuthenticationViewModel.initializer))
                        .get(FirebaseAuthenticationViewModel.class);


        findViewById(R.id.login_materialButton).setOnClickListener(
                view -> {
                    Editable emailEditable = emailText.getText();
                    Editable passwordEditable = passwordText.getText();

                    assert emailEditable != null;
                    String email = emailEditable.toString();

                    assert passwordEditable != null;
                    String password = passwordEditable.toString();

                     if((!email.equals(""))&&(!password.equals(""))){
                         progressBar.setVisibility(View.VISIBLE);

                         authenticationViewModel.signInUserWithEmailAndPassword(
                                 this, progressBar, email,password);

                     } else {
                         Toast.makeText(this, "Empty password and email field",
                                 Toast.LENGTH_SHORT).show();
                     }
                }
        );

        findViewById(R.id.create_account_textview).setOnClickListener(view -> {
            startActivity(new Intent(this, RegistrationActivity.class));
            finish();
        });

        findViewById(R.id.forget_password_textview).setOnClickListener(view -> {
            Editable emailEditable = emailText.getText();
            if((emailEditable!=null)&&(emailEditable.toString().matches(Constants.EMAIL_REGEX))){
                if(authenticationViewModel.resetPasswordWithEmail(emailEditable.toString()))
                Toast.makeText(this, "Email sent to your account",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Email field is empty or incorrect email, " +
                        "therefore password cannot be reset", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void dropSplashScreen(){
        new Handler().postDelayed(() -> isAndroidReady=true, 750);
    }

    @Override
    protected void onStart() {
        super.onStart();
        final String[] permissions = new String[] {Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.INTERNET};
        if((ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED)|| (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                !=PackageManager.PERMISSION_GRANTED)){

            ActivityCompat.requestPermissions(this,permissions,REQUEST_PERMISSIONS_CODE);
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        compositeDisposable.dispose();
        finish();
    }
}