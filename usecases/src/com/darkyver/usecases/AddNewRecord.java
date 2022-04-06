package com.darkyver.usecases;

import com.darkyver.domain.entity.Record;
import com.darkyver.domain.entity.User;
import com.darkyver.domain.port.UserRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalLong;

public class AddNewRecord {
    private UserRepository repository;

    public AddNewRecord(UserRepository repository) {
        this.repository = repository;
    }

    public boolean addNewLessonsToUser(int userId, int amountRecords, long lessonsTime) {
        return addNewRecordsToUser(userId, amountRecords, lessonsTime, Type.LESSON);
    }


    public boolean addNewPaymentsToUser(int userId, int amountRecords, long paymentTime) {
        return addNewRecordsToUser(userId, amountRecords, paymentTime, Type.PAYMENT);
    }

    private long getLastRecordId(User user) {
        OptionalLong max = user.getRecordMap().values().stream().mapToLong(r -> r.getId()).max();
        if (max.isEmpty()) return -1;
        return max.getAsLong();
    }

    private boolean addNewRecordsToUser(int userId, int amountRecords, long time, Type type) {
        if (userId < 0 | amountRecords <= 0 | time == 0) return false;
        Optional<User> userOptional = repository.getUser(userId);
        if (userOptional.isEmpty()) return false;
        User user = userOptional.get();
        Map<String, Record> recordMap = user.getRecordMap();
        List<Record> records = recordMap.values().stream().filter(r -> (type.equals(Type.PAYMENT) ? r.getPaidTime() : r.getLessonDoneTime()) == 0).toList();
        for (int i = 0; i < records.size() & i < amountRecords; i++) {
            switch (type){
                case LESSON -> records.get(i).setLessonDoneTime(time);
                case PAYMENT -> records.get(i).setPaidTime(time);
            }
        }
        int countNewRecords = amountRecords - records.size();
        if (countNewRecords > 0) {
            long startId = getLastRecordId(user);
            for (int i = 0; i < countNewRecords; i++) {
                Record record = new Record(++startId);
                switch (type){
                    case LESSON -> record.setLessonDoneTime(time);
                    case PAYMENT -> record.setPaidTime(time);
                }

                recordMap.put(String.valueOf(record.getId()), record);
            }
        }
        repository.updateUser(user.getId(), user);
        return true;
    }


    private enum Type {
        PAYMENT,
        LESSON
    }


}
