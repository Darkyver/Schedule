package com.darkyver.usecases;

import com.darkyver.domain.entity.Record;
import com.darkyver.domain.entity.User;
import com.darkyver.domain.port.UserRepository;

import java.util.Optional;
import java.util.OptionalInt;

public class CreateNewRecord {
    private UserRepository repository;

    public CreateNewRecord(UserRepository repository) {
        this.repository = repository;
    }

    public Optional<Record> createRecord(int idUser, Record record){
        Optional<User> optionalUser = repository.getUser(idUser);
        if (optionalUser.isEmpty()) return Optional.empty();

        User user = optionalUser.get();
        OptionalInt optionalInt = user.getRecordMap().keySet().stream().mapToInt(Integer::parseInt).max();
        if(optionalInt.isEmpty()) optionalInt = OptionalInt.of(0);
        record.setId(optionalInt.getAsInt()+1);
        repository.addRecordToUser(idUser, record);
        return Optional.of(record);
    }
}
