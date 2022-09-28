package com.atom.flightbookingapplication.viewmodels;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import com.atom.flightbookingapplication.activities.LoginActivity;
import com.atom.flightbookingapplication.activities.MainActivity;
import com.atom.flightbookingapplication.applications.MyApplication;
import com.atom.flightbookingapplication.repositories.FirebaseAuthenticationRepository;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseUser;


import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Singleton;

public class FirebaseAuthenticationViewModel extends AndroidViewModel {

    private final FirebaseAuthenticationRepository firebaseAuthenticationRepository;
    private final String TAG = "FirebaseAuthRepository";

    @Inject
    public FirebaseAuthenticationViewModel(@NonNull Application application,
                                           FirebaseAuthenticationRepository firebaseAuthenticationRepository)
    {
        super(application);
        this.firebaseAuthenticationRepository = firebaseAuthenticationRepository;

    }
    public static final ViewModelInitializer<FirebaseAuthenticationViewModel> initializer =
            new ViewModelInitializer<>(FirebaseAuthenticationViewModel.class,
                    creationExtras -> {

                        /* The object casting does not work even when it is legal according the java rules
                          I cannot cast android.app.Application to MyApplication,
                          even when MyApplication is a child class of Application,
                          I simply lost/confused about this. I have two problems as to why
                          this might occur, 1st I cannot
                          cast or cast an instance object, second it might be it was really pointless
                          to cast it as MyApplication is already an Application object
                          and probably an instance?

                          MyApplication application = (MyApplication) Objects.requireNonNull(creationExtras
                                        .get(ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY))
                                .getApplicationContext()
                         **/

                        MyApplication application = new MyApplication();

                        Log.d("Application", application.toString());
                        return new FirebaseAuthenticationViewModel(application,
                                application.applicationComponent.firebaseAuthenticationRepository());

                    });

    public FirebaseUser getCurrentFirebaseUser(){
        return firebaseAuthenticationRepository.getCurrentFirebaseUser();
    }

    public void signInUserWithEmailAndPassword(Context context,
                                               ProgressBar progressBar,
                                               String email,
                                               String password) {
        firebaseAuthenticationRepository.signInUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        context.startActivity(
                                new Intent(context, MainActivity.class));

                    } else {
                        if(progressBar!=null){
                            Toast.makeText(context,
                                    Objects.requireNonNull(task.getException()).getLocalizedMessage(),
                                    Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                            progressBar.setIndeterminate(false);
                            Log.d(TAG, Objects.requireNonNull(task.getException()).toString());

                        }
                    }
                });
    }

    public void createUserWithEmailAndPassword(Context context, ProgressBar progressBar,
                                               String email, String password,
                                               Map<String, Object> userObject,
                                               FirebaseRTDBViewModel rtdbViewModel){

        firebaseAuthenticationRepository.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        progressBar.setVisibility(View.VISIBLE);

                        Toast.makeText(context,
                                "Your account has been created!", Toast.LENGTH_SHORT).show();
                        rtdbViewModel.saveUserToDatabase(context, userObject);

                    } else {
                        progressBar.setVisibility(View.INVISIBLE);
                        progressBar.setIndeterminate(false);
                        Toast.makeText(context,
                                Objects.requireNonNull(task.getException()).toString(),
                                Toast.LENGTH_SHORT).show();
                        Log.e(TAG, Objects.requireNonNull(task.getException()).toString());
                    }
                });
    }
    public boolean resetPasswordWithEmail(String email){
        return firebaseAuthenticationRepository.resetPasswordWithEmail(email);
    }

    public void deleteUserAccount(Fragment fragment, UserViewModel userViewModel){
        firebaseAuthenticationRepository.deleteUserAccount().addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Toast.makeText(fragment.getContext(),
                                "Account deleted.", Toast.LENGTH_SHORT).show();
                        fragment.startActivity(new Intent(fragment.getContext(),
                                LoginActivity.class));
                        userViewModel.deleteAllUsers();
                    } else {
                        Toast.makeText(fragment.getContext(),
                                "Account cannot be deleted.", Toast.LENGTH_SHORT).show();
                    }
        });
    }
    public void updatePassword(Context context, String oldEmail, String oldPassword,
                               Map<String, String> newProfileDetails, UserViewModel userViewModel){
        FirebaseUser firebaseUser = getCurrentFirebaseUser();
        AuthCredential credential = EmailAuthProvider.getCredential(oldEmail, oldPassword);

        String newEmail = newProfileDetails.get("email");
        String newPassword =  newProfileDetails.get("password");
        String name = newProfileDetails.get("name");
        String surname = newProfileDetails.get("surname");
        String title = newProfileDetails.get("title");
        String gender = newProfileDetails.get("gender");
        String dateOfBirth = newProfileDetails.get("date_of_birth");
        String phoneNumber = newProfileDetails.get("phone_number");

        firebaseUser.reauthenticate(credential).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                assert newPassword != null;
                firebaseUser.updatePassword(newPassword).addOnCompleteListener(task1 -> {
                    if(task1.isSuccessful()){
                        userViewModel.updateUserDetails(name,surname,gender,title,newEmail,
                                newPassword,phoneNumber,dateOfBirth);
                        Toast.makeText(context,
                                "Password Updated!", Toast.LENGTH_SHORT).show();

                    } else {
                        Log.e(TAG, "updatePassword: ", task1.getException());
                    }
                });
            } else {
                Log.e(TAG, "updatePassword re-authentication: ", task.getException());
            }
        });
    }
    public void updateEmailAndPassword(Context context, UserViewModel userViewModel,
                                       Map<String, String> newProfileDetails,
                                       String oldEmail, String oldPassword){

        FirebaseUser firebaseUser = getCurrentFirebaseUser();
        AuthCredential credential = EmailAuthProvider.getCredential(oldEmail, oldPassword);

        String newEmail = newProfileDetails.get("email");
        String newPassword =  newProfileDetails.get("password");
        String name = newProfileDetails.get("name");
        String surname = newProfileDetails.get("surname");
        String title = newProfileDetails.get("title");
        String gender = newProfileDetails.get("gender");
        String dateOfBirth = newProfileDetails.get("date_of_birth");
        String phoneNumber = newProfileDetails.get("phone_number");


       firebaseUser.reauthenticate(credential).addOnCompleteListener(task -> {
            if(task.isSuccessful()){

                assert newPassword != null;
                firebaseUser.updatePassword(newPassword).addOnCompleteListener(task1 -> {
                    if(task1.isSuccessful()){
                        Log.d(TAG, "updatePassword: Password updated!. ");
                    } else {
                        Log.d(TAG, "updatePassword: ", task1.getException());
                    }
                });
                assert newEmail != null;
                firebaseUser.updateEmail(newEmail).addOnCompleteListener(task12 -> {
                    if(task12.isSuccessful()){
                        Log.d(TAG, "updateEmail: Email is updated");
                        userViewModel.updateUserDetails(name,surname,gender,title,newEmail,
                                newPassword,phoneNumber,dateOfBirth);
                        context.startActivity(new Intent(context, MainActivity.class));
                        Toast.makeText(context, "Profile updated!", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e(TAG, "updateEmail: ", task12.getException());
                    }
                });
                Log.d(TAG, "updateEmailAndPassword: Updates successful");
            } else {
                Log.e(TAG, "updateEmailAndPassword: ", task.getException());
            }
        });
    }
}
