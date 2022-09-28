package com.atom.flightbookingapplication.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.atom.flightbookingapplication.R;
import com.atom.flightbookingapplication.activities.EditFlightPreferenceActivity;
import com.atom.flightbookingapplication.models.Card;
import com.atom.flightbookingapplication.models.FlightPreference;
import com.atom.flightbookingapplication.models.User;
import com.atom.flightbookingapplication.viewmodels.UserViewModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FlightPreferenceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FlightPreferenceFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private UserViewModel userViewModel;
    private CompositeDisposable compositeDisposable;


    public FlightPreferenceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditFlightPreferenceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FlightPreferenceFragment newInstance(String param1, String param2) {
        FlightPreferenceFragment fragment = new FlightPreferenceFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_flight_preference, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        compositeDisposable = new CompositeDisposable();
        view.findViewById(R.id.edit_materialButton1).setOnClickListener(view1 -> {

            assert getParentFragment() != null;
            getParentFragment().startActivity(new Intent(view1.getContext(),
                    EditFlightPreferenceActivity.class));
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        View view = requireView();
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        TextView commonAirportsTextView = view.findViewById(R.id.common_flights_textview2);
        TextView numberOfBagsTextView = view.findViewById(R.id.numberOfBags_textview2);
        TextView seatPositionTextView = view.findViewById(R.id.seat_textview2);
        TextView classTextView = view.findViewById(R.id.class_textview2);
        TextView foodOptionTextView = view.findViewById(R.id.food_textview2);
        TextView cardNumberTextView = view.findViewById(R.id.card_number_textview2);
        TextView cvvTextView = view.findViewById(R.id.cvv_textview2);
        TextView expiryDateTextView = view.findViewById(R.id.expiryDate_textview2);

        @SuppressLint("DefaultLocale") Disposable userDisposable = userViewModel.getAllUsers()
                .subscribeOn(Schedulers.io())
                .doOnNext(users -> {
                    User user = users.get(users.size()-1);
                    FlightPreference userFlightPreference = user.getFlightPreference();
                    Card userCard = user.getCard();
                    String commonAirports = "1. "+userFlightPreference.getFirst_airport()+"\n2. "
                            + userFlightPreference.getSecond_airport()+"\n3. "
                            + userFlightPreference.getThird_airport();

                    commonAirportsTextView.setText(commonAirports);
                    numberOfBagsTextView.setText(userFlightPreference.getNumber_of_bags());
                    seatPositionTextView.setText(userFlightPreference.getSeat_position());
                    classTextView.setText(userFlightPreference.getAirplane_class());
                    foodOptionTextView.setText(userFlightPreference.getFood_option());
                    cardNumberTextView.setText(userCard.getCardNumber());
                    cvvTextView.setText(String.format("%d",userCard.getCvv()));
                    expiryDateTextView.setText(userCard.getExpiryDate());

                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();

        compositeDisposable.add(userDisposable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }
}