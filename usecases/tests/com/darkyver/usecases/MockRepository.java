package com.darkyver.usecases;

import com.darkyver.domain.entity.Record;
import com.darkyver.domain.entity.User;
import com.darkyver.domain.port.OnChangeUserList;
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
    public boolean addRecordToUser(int idUser, Record record) {
        if(idUser < 0) return false;
        if(!integerUserMap.containsKey(idUser)) return false;
        Map<String, Record> recordMap = integerUserMap.get(idUser).getRecordMap();
        if (recordMap.containsKey(String.valueOf(record.getId()))) {
            return false;
        }
        recordMap.put(String.valueOf(record.getId()), record);
        return true;
    }

    @Override
    public boolean updateRecordToUser(int idUser, Record record) {
        if(idUser < 0) return false;
        if(!integerUserMap.containsKey(idUser)) return false;
        Map<String, Record> recordMap = integerUserMap.get(idUser).getRecordMap();
        if (!recordMap.containsKey(String.valueOf(record.getId()))) {
            return false;
        }
        recordMap.put(String.valueOf(record.getId()), record);
        return true;
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
    public void addListUsersChangeListener(OnChangeUserList listener) {

    }
}
