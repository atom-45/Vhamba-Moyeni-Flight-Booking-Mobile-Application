package com.atom.flightbookingapplication.components;

import com.atom.flightbookingapplication.modules.FirebaseModule;
import com.atom.flightbookingapplication.repositories.FirebaseAuthenticationRepository;
import com.atom.flightbookingapplication.repositories.FirebaseRTDBRepository;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {FirebaseModule.class})
public interface ApplicationComponent {

    //Application application();
    //UserRepository userRepository();
    FirebaseAuthenticationRepository firebaseAuthenticationRepository();
    FirebaseRTDBRepository firebaseRTDBRepository();


}


