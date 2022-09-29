package com.atom.flightbookingapplication.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.atom.flightbookingapplication.R;
import com.atom.flightbookingapplication.activities.EditProfileActivity;
import com.atom.flightbookingapplication.models.User;
import com.atom.flightbookingapplication.viewmodels.FirebaseAuthenticationViewModel;
import com.atom.flightbookingapplication.viewmodels.UserViewModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private UserViewModel userViewModel;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private static final String TAG = "ProfileFragment";


    FirebaseAuthenticationViewModel authenticationViewModel;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){

        view.findViewById(R.id.edit_materialButton2).setOnClickListener(view1 -> {
            assert getParentFragment() != null;
            getParentFragment().startActivity(new Intent(view1.getContext(),
                    EditProfileActivity.class));
        });
        view.findViewById(R.id.delete_materialButton).setOnClickListener(view12 -> {
            authenticationViewModel.deleteUserAccount(getParentFragment(),userViewModel);
            assert getParentFragment() != null;
            getParentFragment().requireActivity().finish();
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = requireView();

        userViewModel = new ViewModelProvider(this)
                .get(UserViewModel.class);

        authenticationViewModel = new ViewModelProvider(this,
                ViewModelProvider.Factory.from(FirebaseAuthenticationViewModel.initializer))
                .get(FirebaseAuthenticationViewModel.class);

        TextView fullNameTextView = view.findViewById(R.id.fullname_textview4);
        TextView surnameTextView = view.findViewById(R.id.surname_textview4);
        TextView phoneNumberTextView = view.findViewById(R.id.phone_number_textview4);
        TextView emailTextView = view.findViewById(R.id.email_textview4);
        TextView dobTextView = view.findViewById(R.id.dob_textview4);
        TextView title = view.findViewById(R.id.title_textview4);
        TextView gender = view.findViewById(R.id.gender_textview4);

        Disposable disposable = userViewModel.getAllUsers()
                .subscribeOn(Schedulers.io())
                .doOnNext(users -> {
                    if(users.size()!=0){
                        User user = users.get(users.size()-1);
                        fullNameTextView.setText(user.getName());
                        surnameTextView.setText(user.getSurname());
                        phoneNumberTextView.setText(user.getPhoneNumbers());
                        emailTextView.setText(user.getEmail());
                        dobTextView.setText(user.getDateOfBirth());
                        title.setText(user.getTitle());
                        gender.setText(user.getGender());
                    } else {
                        Log.e(TAG, "onStart:  empty list. No recorded user data " +
                                "in Room database");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();

        compositeDisposable.add(disposable);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        compositeDisposable.dispose();
    }
}