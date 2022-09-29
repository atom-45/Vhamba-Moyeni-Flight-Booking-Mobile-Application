package com.atom.flightbookingapplication.activities;



import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;



import android.os.Bundle;
import android.widget.PopupMenu;

import com.atom.flightbookingapplication.R;
import com.atom.flightbookingapplication.databinding.ActivityMain1Binding;

public class MainActivity extends FragmentActivity {

    private static final String TAG = "MainActivity2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.atom.flightbookingapplication.databinding.ActivityMain1Binding
                activityMain1Binding = ActivityMain1Binding.inflate(getLayoutInflater());


        setContentView(activityMain1Binding.getRoot());
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.nav_host_container);

        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();

        PopupMenu popupMenu = new PopupMenu(this, null);
        popupMenu.inflate(R.menu.bottom_navigation_menu);

        activityMain1Binding.smoothBottomBar1.
                setupWithNavController(popupMenu.getMenu(), navController);


    }
}