package com.darkyver.config;

import com.darkyver.domain.port.OnChangeUserList;
import com.darkyver.firebase.service.FirebaseUserRepository;
import com.darkyver.domain.entity.User;
import com.darkyver.domain.entity.Record;
import com.darkyver.domain.port.UserRepository;
import com.darkyver.usecases.*;

import java.util.List;
import java.util.Optional;

public class Config {
    private UserRepository userRepository = new FirebaseUserRepository();
    private CRUDActionsForUser crudActionsForUser = new CRUDActionsForUser(userRepository);
    private GetUserAnalytics getUserAnalytics     = new GetUserAnalytics(userRepository);
    private UpdateRecordToUser updateRecordToUser = new UpdateRecordToUser(userRepository);
    private CreateNewRecord createNewRecord       = new CreateNewRecord(userRepository);
    private ConnectToDataBaseInBackground connectToDataBaseInBackground = new ConnectToDataBaseInBackground(userRepository);
    private AddUserListChangeListener addUserListChangeListener = new AddUserListChangeListener(userRepository);

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

    public Optional<Record> createNewRecord(int idUser, Record record){
        return createNewRecord.createRecord(idUser, record);
    }

    public boolean updateRecord(int userId, Record record){
        return updateRecordToUser.updateRecord(userId, record);
    }

    public void connectInBackground(){
        connectToDataBaseInBackground.connect();
    }

    public void addUserListChangeListener(OnChangeUserList listener){
        addUserListChangeListener.addListenerChangeUserList(listener);

    }

}
