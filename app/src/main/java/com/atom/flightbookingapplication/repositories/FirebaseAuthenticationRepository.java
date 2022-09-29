package com.atom.flightbookingapplication.repositories;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class FirebaseAuthenticationRepository {

    private final FirebaseAuth firebaseAuth;
    private static final String TAG = "FirebaseAuthenticationRepository";

    @Inject
    public FirebaseAuthenticationRepository(FirebaseAuth firebaseAuth){
        this.firebaseAuth = firebaseAuth;
    }

    public Task<AuthResult> createUserWithEmailAndPassword(String email, String password){
        return firebaseAuth.createUserWithEmailAndPassword(email, password);

    }
    public Task<AuthResult> signInUserWithEmailAndPassword(String email, String password){
        return firebaseAuth.signInWithEmailAndPassword(email,password);
    }
    public boolean resetPasswordWithEmail(String email){
       return firebaseAuth.sendPasswordResetEmail(email).isSuccessful();
    }
    public FirebaseUser getCurrentFirebaseUser(){
        return firebaseAuth.getCurrentUser();
    }

    public String getUserToken(){
        Task<GetTokenResult> getTokenResultTask = Objects.requireNonNull(firebaseAuth.getCurrentUser())
                .getIdToken(true);
        return getTokenResultTask.getResult().getToken();
    }
    public Task<Void> deleteUserAccount(){
        return Objects.requireNonNull(firebaseAuth.getCurrentUser()).delete();
    }

}
