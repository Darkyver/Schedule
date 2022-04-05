package com.darkyver.usecases;

import com.darkyver.domain.entity.Record;
import com.darkyver.domain.entity.User;
import com.darkyver.domain.port.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class GetUserAnalytics {
    private UserRepository userRepository;

    public GetUserAnalytics(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public int countUnCompletedRecords(int userId){
        Optional<User> user = userRepository.getUser(userId);
        if(user.isEmpty()) return 0;
        Collection<Record> recordList = user.get().getRecordMap().values();
        return (int) recordList.stream().filter(record -> record.getPaidTime() == 0 | record.getLessonDoneTime() == 0).count();
    }

    public int countPrepayment(int userId){
        Optional<User> user = userRepository.getUser(userId);
        if(user.isEmpty()) return 0;
        Collection<Record> recordList = user.get().getRecordMap().values();
        return (int) recordList.stream().filter(record -> record.getPaidTime() != 0 & record.getLessonDoneTime() != 0).count();
    }
}
