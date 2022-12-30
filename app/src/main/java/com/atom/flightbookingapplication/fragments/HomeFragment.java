package com.atom.flightbookingapplication.fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.atom.flightbookingapplication.R;
import com.atom.flightbookingapplication.activities.FlightSelectionActivity;
import com.atom.flightbookingapplication.adapters.ShopAdapter;
import com.atom.flightbookingapplication.databinding.FragmentHomeBinding;
import com.atom.flightbookingapplication.models.Constants;
import com.atom.flightbookingapplication.models.User;
import com.atom.flightbookingapplication.viewmodels.FirebaseRTDBViewModel;
import com.atom.flightbookingapplication.viewmodels.UserViewModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private CompositeDisposable compositeDisposable;
    private FragmentHomeBinding homeBinding;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        homeBinding = FragmentHomeBinding.inflate(inflater, container, false);

        return homeBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        compositeDisposable = new CompositeDisposable();

        ViewPager2 shopViewPager2 = homeBinding.shopViewViewPager;
        shopViewPager2.setClipToPadding(false);
        shopViewPager2.setClipChildren(false);
        shopViewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        shopViewPager2.setOffscreenPageLimit(1);
        shopViewPager2.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);


        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(1));
        compositePageTransformer.addTransformer((page, position) -> {
            float r = 1 - Math.abs(position);
            //page.setScaleX(0.85f + r * 0.15f);
            page.setScaleY(0.75f + r * 0.25f);


        });

        shopViewPager2.setPageTransformer(compositePageTransformer);
        shopViewPager2.setAdapter(new ShopAdapter(Constants.shops()));

        homeBinding.cemairCardview.setOnClickListener(view12 ->
                startActivity(new Intent(getContext(), FlightSelectionActivity.class)
                .putExtra(Constants.AIRLINE_NAME,"FLYCEMAIR")));

        homeBinding.saalinkCardview.setOnClickListener(view1 ->
                startActivity(new Intent(getContext(), FlightSelectionActivity.class)
                .putExtra(Constants.AIRLINE_NAME,"AIRLINK")));

        homeBinding.saaCardview.setOnClickListener(view13 ->
                startActivity(new Intent(getContext(), FlightSelectionActivity.class)
                .putExtra(Constants.AIRLINE_NAME,"SA_AIRWAYS")));

        homeBinding.flysafairCardview.setOnClickListener(view14 ->
                startActivity(new Intent(getContext(), FlightSelectionActivity.class)
                .putExtra(Constants.AIRLINE_NAME, "FLYSAFAIR")));
    }
    @Override
    public void onStart() {
        super.onStart();

        View view = requireView();
        FirebaseRTDBViewModel rtdbViewModel =
                new ViewModelProvider(this,ViewModelProvider.Factory
                        .from(FirebaseRTDBViewModel.initializer))
                        .get(FirebaseRTDBViewModel.class);

        UserViewModel userViewModel = new ViewModelProvider(this)
                .get(UserViewModel.class);

        TextView currentFlight = homeBinding.flightNumberAndAirlineMaTextView;
        TextView currentDateTextView = homeBinding.datetimeTextviewB;
        TextView currentTimeTextView = homeBinding.timemaTextviewA;
        TextView currentFromTextView = homeBinding.fromAirportTextview;
        TextView currentToTextView = homeBinding.toAirportTextview;
        ImageView checkInImageView = homeBinding.checkInImageView;

        Disposable disposable = userViewModel.getAllUsers()
                .subscribeOn(Schedulers.io())
                .doOnNext(users -> {
                    User user = users.get(users.size()-1);
                    rtdbViewModel.readCurrentAirplaneTicketForDisplay(checkInImageView,
                            currentFlight, currentDateTextView, currentTimeTextView,
                            currentFromTextView, currentToTextView, user.getName(), user.getSurname());

                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();

        compositeDisposable.add(disposable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }
}