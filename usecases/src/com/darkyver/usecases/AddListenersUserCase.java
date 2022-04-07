package com.darkyver.usecases;

import com.darkyver.domain.entity.User;
import com.darkyver.domain.port.OnChangeListener;
import com.darkyver.domain.port.UserRepository;


public class AddListenersUserCase {
    private UserRepository repository;

    public AddListenersUserCase(UserRepository repository) {
        this.repository = repository;
    }
    public void onUserAdd(OnChangeListener<User> listener){
        repository.onUserAdd(listener);
    }
    public void onUserRemove(OnChangeListener<User> listener){
        repository.onUserRemove(listener);
    }
    public void onUserUpdate(OnChangeListener<User> listener){
        repository.onUserUpdate(listener);
    }

    public void onConnect(Runnable runnable){
        repository.onConnect(runnable);
    }
    public void onDisconnect(Runnable runnable){
        repository.onDisconnect(runnable);
    }
}
