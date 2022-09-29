package com.atom.flightbookingapplication.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import com.atom.flightbookingapplication.applications.MyApplication;
import com.atom.flightbookingapplication.models.Card;
import com.atom.flightbookingapplication.models.FlightPreference;
import com.atom.flightbookingapplication.models.User;
import com.atom.flightbookingapplication.repositories.UserRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UserViewModel extends AndroidViewModel {

    private final UserRepository userRepository;
    private final CompositeDisposable compositeDisposable;
    private final static String TAG = "UserRepository";

    public UserViewModel(@NonNull Application application) {
        super(application);

        this.userRepository = new UserRepository(application);
        compositeDisposable = new CompositeDisposable();
    }

   /*public static final ViewModelInitializer<UserViewModel> initializer =
            new ViewModelInitializer<>(
                    UserViewModel.class, creationExtras -> {

                MyApplication application = new MyApplication();
                return new UserViewModel(application,
                        application.applicationComponent.userRepository());

            });**/

    public Observable<List<User>> getAllUsers(){
        return userRepository.getAllUsers();
    }

    public void deleteAllUsers(){
        userRepository.deleteAllUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d)
                    {
                        if(!d.isDisposed()){
                            compositeDisposable.add(d);
                        }
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG,"onComplete: All Users deleted");
                        compositeDisposable.dispose();
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.e(TAG,e.getLocalizedMessage());
                        compositeDisposable.dispose();
                    }
                });
    }


    public void deleteUserByName(String name){
        userRepository.deleteUserByName(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d)
                    {
                        if(!d.isDisposed()){
                            compositeDisposable.add(d);
                        }
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: current user profile deleted");
                        compositeDisposable.dispose();
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.e(TAG,"onError: "+e.getLocalizedMessage());
                        compositeDisposable.dispose();
                    }
                })
        ;
    }

    public void updateUserDetails(String name, String surname, String gender, String title,
                                  String email, String password, String phoneNumber, String dateOfBirth){
        userRepository.updateUserDetails(name, surname, gender, title, email, password,
                        phoneNumber, dateOfBirth)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        if(!d.isDisposed()){
                            compositeDisposable.add(d);
                        }
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG,"onComplete -- User details updated in Room database");
                        compositeDisposable.dispose();

                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.e(TAG,"onError: "+e.getMessage());
                        compositeDisposable.dispose();
                    }
                })
        ;
    }

    public void updateUserCardDetails(String cardNumber, Integer cvv, String expiryDate){
        userRepository.updateUserCardDetails(cardNumber, cvv, expiryDate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(
                            @io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        if(!d.isDisposed()){
                            compositeDisposable.add(d);
                        }
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: user card details updated in room database");
                        compositeDisposable.dispose();
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.e(TAG, "onError Card details update: ",e);
                        compositeDisposable.dispose();

                    }
                });
    }


    public void updateUserFlightPreferences(String firstAirport, String secondAirport,
                                            String thirdAirport, String airplaneClass,
                                            String seatPosition, String foodOption,
                                            String numberOfBags){
        userRepository.updateUserFlightPreference(firstAirport, secondAirport, thirdAirport,
                airplaneClass, seatPosition, foodOption, numberOfBags)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(
                            @io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        if(!d.isDisposed()){
                            compositeDisposable.add(d);
                        }
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: user flight preferences updated in room database");
                        compositeDisposable.dispose();

                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.e(TAG, "onError Flight Preference Update: ",e);
                        compositeDisposable.dispose();
                    }
                });
    }

    ///Some method below are crap as they do not work as intended.
    public void updateCardDetails(Card card){
        userRepository.updateCardDetails(card)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        if(!d.isDisposed()){
                            compositeDisposable.add(d);
                        }
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: Card Details updated in Room database");
                        compositeDisposable.dispose();
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.e(TAG, "onError: "+e.getMessage());
                        compositeDisposable.dispose();


                    }
                });
    }

    public void updateFlightPreference(FlightPreference flightPreference){
        userRepository.updateFlightPreference(flightPreference)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        if(!d.isDisposed()){
                            compositeDisposable.add(d);
                        }
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: Flight Preferences updated in Room database");
                        compositeDisposable.dispose();
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.e(TAG, "onError: ", e);
                        compositeDisposable.dispose();

                    }
                });
    }

    public void insertUser(User user){
        userRepository.addUser(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d)
                    {
                        if(!d.isDisposed()){compositeDisposable.add(d);}
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG,"onComplete: User object added to the room database");
                        compositeDisposable.dispose();
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.e(TAG,"onError: "+e.getLocalizedMessage());
                        compositeDisposable.dispose();

                    }
                })
        ;
    }

    public void insertCard(Card card){
        userRepository.addCard(card)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d)
                    {
                     if(!d.isDisposed()){ compositeDisposable.add(d);}
                    }

                    @Override
                    public void onComplete() {
                        compositeDisposable.dispose();

                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.e(TAG,"onError: "+e.getLocalizedMessage());
                        compositeDisposable.dispose();
                    }
                })
        ;
    }
}
