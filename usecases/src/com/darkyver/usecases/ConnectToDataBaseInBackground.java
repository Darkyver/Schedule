package com.darkyver.usecases;

import com.darkyver.domain.port.UserRepository;

public class ConnectToDataBaseInBackground {
    private UserRepository repository;

    public ConnectToDataBaseInBackground(UserRepository repository) {
        this.repository = repository;
    }

    public void connect(){
        new Thread(() -> repository.connect()).start();
    }
}
