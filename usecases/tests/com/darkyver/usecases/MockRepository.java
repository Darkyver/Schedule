package com.darkyver.usecases;

import com.darkyver.domain.entity.Record;
import com.darkyver.domain.entity.User;
import com.darkyver.domain.port.OnChangeListener;
import com.darkyver.domain.port.UserRepository;

import java.util.*;

public class MockRepository implements UserRepository {
    private Map<Integer, User> integerUserMap = new HashMap<>();
    
    @Override
    public void connect() {
        
    }

    @Override
    public Optional<User> getUser(int id) {
        return Optional.ofNullable(integerUserMap.get(id));
    }

    @Override
    public List<User> getAllUsers() {
        return integerUserMap.values().stream().toList();
    }

    @Override
    public boolean updateUser(int id, User user) {
        if(id != user.getId()) return false;
        Optional<User> optionalUser = getUser(id);
        if (optionalUser.isEmpty())return false;
        return integerUserMap.put(id, user) != null;
    }

    @Override
    public boolean deleteUser(int id) {
        if(id < 0) return false;
        return integerUserMap.remove(id) != null;
    }
    @Override
    public boolean createUser(String name, int price, String note) {
        OptionalLong max = integerUserMap.values().stream().mapToLong(u -> u.getId()).max();
        int maxId = -1;
        if(max.isPresent()) maxId = (int) max.getAsLong();
        User user = new User();
        user.setId(++maxId);
        user.setPrice(price);
        user.setNote(note);
        user.setName(name);
        integerUserMap.put(maxId, user);
        return true;
    }

    @Override
    public void onUserAdd(OnChangeListener<User> listener) {

    }

    @Override
    public void onUserRemove(OnChangeListener<User> listener) {

    }

    @Override
    public void onUserUpdate(OnChangeListener<User> listener) {

    }

    @Override
    public void onConnect(Runnable runnable) {

    }

    @Override
    public void onDisconnect(Runnable runnable) {

    }
}
