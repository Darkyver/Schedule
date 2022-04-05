package com.darkyver.usecases;

import com.darkyver.domain.entity.User;
import com.darkyver.domain.port.UserRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class CRUDActionsForUser {
    private UserRepository userRepository;

    public CRUDActionsForUser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean createUser(String name, int price, String note){
        return userRepository.createUser(name, price, note);
    }

    public boolean updateUser(int id, User user){
        return userRepository.updateUser(id, user);
    }

    public boolean deleteUser(int id){
        return userRepository.deleteUser(id);
    }

    public Optional<User> getUser(int id){
        return userRepository.getUser(id);
    }

    public List<User> getAllUsers(){
        List<User> users = userRepository.getAllUsers();
        users.sort(Comparator.comparingInt(User::getId).reversed());
        return users;
    }


}
