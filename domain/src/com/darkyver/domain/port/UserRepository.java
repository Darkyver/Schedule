package com.darkyver.domain.port;

import com.darkyver.domain.entity.Record;
import com.darkyver.domain.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    void connect();
    Optional<User> getUser(int id);
    List<User> getAllUsers();
    boolean updateUser(int id, User user);
    boolean deleteUser(int id);

    boolean addRecordToUser(int idUser,  Record record);
    boolean updateRecordToUser(int id, Record record);

    boolean createUser(String name, int price, String note);

    void addListUsersChangeListener(OnChangeUserList listener);

}
