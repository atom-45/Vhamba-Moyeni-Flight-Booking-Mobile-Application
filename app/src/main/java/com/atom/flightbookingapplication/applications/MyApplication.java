package com.atom.flightbookingapplication.applications;

import android.app.Application;

import com.atom.flightbookingapplication.components.ApplicationComponent;
import com.atom.flightbookingapplication.components.DaggerApplicationComponent;


public class MyApplication extends Application {

   public ApplicationComponent applicationComponent = DaggerApplicationComponent.create();



}
