package com.darkyver.usecases;

import com.darkyver.domain.entity.Record;
import com.darkyver.domain.port.UserRepository;

public class UpdateRecordToUser {
    private UserRepository userRepository;

    public UpdateRecordToUser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean updateRecord(int idUser, Record record){
        return userRepository.updateRecordToUser(idUser, record);
    }
}
