package com.darkyver.config;

import com.darkyver.domain.port.OnChangeListener;
import com.darkyver.firebase.service.FirebaseUserRepository;
import com.darkyver.domain.entity.User;
import com.darkyver.domain.port.UserRepository;
import com.darkyver.usecases.*;

import java.util.List;
import java.util.Optional;

public class Config {
    private UserRepository userRepository = new FirebaseUserRepository();
    private CRUDActionsForUser crudActionsForUser = new CRUDActionsForUser(userRepository);
    private GetUserAnalytics getUserAnalytics     = new GetUserAnalytics(userRepository);
    private ConnectToDataBaseInBackground connectToDataBaseInBackground = new ConnectToDataBaseInBackground(userRepository);
    private AddListenersUserCase addUserListChangeListener = new AddListenersUserCase(userRepository);
    private AddNewRecord addNewRecord = new AddNewRecord(userRepository);

    public boolean createUser(String name, int id, String note){
        return crudActionsForUser.createUser(name, id, note);
    }

    public Optional<User> getUser(int id){
        return crudActionsForUser.getUser(id);
    }

    public List<User> getAllUsers(){
        return crudActionsForUser.getAllUsers();
    }

    public boolean updateUser(int id, User user){
        return crudActionsForUser.updateUser(id, user);
    }

    public boolean deleteUser(int id){
        return crudActionsForUser.deleteUser(id);
    }

    public void connectInBackground(){
        connectToDataBaseInBackground.connect();
    }

    public void onUserAdd(OnChangeListener<User> listener){
        addUserListChangeListener.onUserAdd(listener);
    }
    public void onUserRemove(OnChangeListener<User> listener){
        addUserListChangeListener.onUserRemove(listener);
    }
    public void onUserUpdate(OnChangeListener<User> listener){
        addUserListChangeListener.onUserUpdate(listener);
    }

    public void onConnect(Runnable runnable){
        addUserListChangeListener.onConnect(runnable);
    }
    public void onDisconnect(Runnable runnable){
        addUserListChangeListener.onDisconnect(runnable);
    }

    public boolean addNewLessonsToUser(int userId, int amountLessons, long time){
        return addNewRecord.addNewLessonsToUser(userId, amountLessons, time);
    }
    public boolean addNewPaymentsToUser(int userId, int amountPayments, long time){
        return addNewRecord.addNewPaymentsToUser(userId, amountPayments, time);
    }

}
