package com.darkyver.usecases;

import com.darkyver.domain.entity.Record;
import com.darkyver.domain.entity.User;
import com.darkyver.domain.port.UserRepository;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AddNewRecordTest {
    UserRepository repository;

    String name0 = "name A";
    String name1 = "name B";
    String name2 = "name C";

    String note0 = "note for A";
    String note1 = "note for B";
    String note2 = "note for C";

    int price0 = 1000;
    int price1 = 2000;
    int price2 = 3000;

    int[] expectedIDs = {0,1,2};

    AddNewRecord addNewRecord;

    @Test
    void testRepository() {
        List<User> users = repository.getAllUsers();
        assertEquals(3, users.size());
        for (int i = 0; i < users.size(); i++) {
            assertEquals(expectedIDs[i], users.get(i).getId());
        }
    }


    @BeforeEach
    void setUp() {
        repository = new MockRepository();
        repository.createUser(name0, price0, note0);
        repository.createUser(name1, price1, note1);
        repository.createUser(name2, price2, note2);

        addNewRecord = new AddNewRecord(repository);
    }

    @Test
    void addFirstLessonToUser() {
        long time = System.currentTimeMillis();
        assertTrue(addNewRecord.addNewLessonsToUser(expectedIDs[0], 1, time));
        Map<String, Record> recordMap = repository.getUser(expectedIDs[0]).get().getRecordMap();
        assertEquals(1, recordMap.size());
        assertEquals(time, recordMap.get(String.valueOf(0)).getLessonDoneTime());
        assertEquals(0, recordMap.get(String.valueOf(0)).getPaidTime());
    }

    @Test
    void addLessonToUserPaymentArePresent() {
        long time = System.currentTimeMillis();
        addNewRecord.addNewPaymentsToUser(expectedIDs[0], 1, time);

        assertTrue(addNewRecord.addNewLessonsToUser(expectedIDs[0], 1, time));
        Map<String, Record> recordMap = repository.getUser(expectedIDs[0]).get().getRecordMap();
        assertEquals(1, recordMap.size());
        Record record = recordMap.get(String.valueOf(0));
        assertNotNull(record);
        assertEquals(time, record.getLessonDoneTime());
        assertEquals(time, record.getPaidTime());

        long timePayment = System.currentTimeMillis();
        addNewRecord.addNewPaymentsToUser(expectedIDs[0], 2, timePayment);
        long timeLesson = System.currentTimeMillis();
        assertTrue(addNewRecord.addNewLessonsToUser(expectedIDs[0], 1, timeLesson));
        assertEquals(3, recordMap.size());
        Record record1 = recordMap.get(String.valueOf(1));
        assertEquals(timeLesson, record1.getLessonDoneTime());
        assertEquals(timePayment, record1.getPaidTime());

        timeLesson = System.currentTimeMillis();
        assertTrue(addNewRecord.addNewLessonsToUser(expectedIDs[0], 2, timeLesson));
        assertEquals(4, recordMap.size());
        Record record2 = recordMap.get(String.valueOf(2));
        assertEquals(timeLesson, record2.getLessonDoneTime());
        assertEquals(timePayment, record2.getPaidTime());
        Record record3 = recordMap.get(String.valueOf(3));
        assertEquals(timeLesson, record3.getLessonDoneTime());
        assertEquals(0, record3.getPaidTime());
    }

    @Test
    void addNewPaymentToUser() {
        long time = System.currentTimeMillis();
        assertTrue(addNewRecord.addNewPaymentsToUser(expectedIDs[0], 1, time));
        Map<String, Record> recordMap = repository.getUser(expectedIDs[0]).get().getRecordMap();
        assertEquals(1, recordMap.size());
        assertEquals(time, recordMap.get(String.valueOf(0)).getPaidTime());
        assertEquals(0, recordMap.get(String.valueOf(0)).getLessonDoneTime());
    }

    @Test
    void addPaymentToUserLessonsArePresent() {
        long time = System.currentTimeMillis();
        addNewRecord.addNewLessonsToUser(expectedIDs[0], 1, time);

        assertTrue(addNewRecord.addNewPaymentsToUser(expectedIDs[0], 1, time));
        Map<String, Record> recordMap = repository.getUser(expectedIDs[0]).get().getRecordMap();
        assertEquals(1, recordMap.size());
        Record record = recordMap.get(String.valueOf(0));
        assertNotNull(record);
        assertEquals(time, record.getLessonDoneTime());
        assertEquals(time, record.getPaidTime());

        long timeLesson = System.currentTimeMillis();
        addNewRecord.addNewLessonsToUser(expectedIDs[0], 2, timeLesson);
        long timePayment = System.currentTimeMillis();
        assertTrue(addNewRecord.addNewPaymentsToUser(expectedIDs[0], 1, timePayment));
        assertEquals(3, recordMap.size());
        Record record1 = recordMap.get(String.valueOf(1));
        assertEquals(timePayment, record1.getPaidTime());
        assertEquals(timeLesson, record1.getLessonDoneTime());

        timePayment = System.currentTimeMillis();
        assertTrue(addNewRecord.addNewPaymentsToUser(expectedIDs[0], 2, timePayment));
        assertEquals(4, recordMap.size());
        Record record2 = recordMap.get(String.valueOf(2));
        assertEquals(timeLesson, record2.getLessonDoneTime());
        assertEquals(timePayment, record2.getPaidTime());
        Record record3 = recordMap.get(String.valueOf(3));
        assertEquals(timePayment, record3.getPaidTime());
        assertEquals(0, record3.getLessonDoneTime());
    }

}