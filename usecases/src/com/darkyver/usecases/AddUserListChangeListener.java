package com.darkyver.usecases;

import com.darkyver.domain.port.OnChangeUserList;
import com.darkyver.domain.port.UserRepository;

public class AddUserListChangeListener {
    private UserRepository repository;

    public AddUserListChangeListener(UserRepository repository) {
        this.repository = repository;
    }

    public void addListenerChangeUserList(OnChangeUserList listener){
        repository.addListUsersChangeListener(listener);
    }
}
