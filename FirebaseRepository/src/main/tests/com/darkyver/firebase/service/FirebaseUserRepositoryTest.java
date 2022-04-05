package com.darkyver.firebase.service;

import com.darkyver.domain.entity.Record;
import com.darkyver.domain.entity.User;
import com.darkyver.domain.port.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class FirebaseUserRepositoryTest {

    private UserRepository repository = new FirebaseUserRepository();

    @BeforeEach
    void setUp() {

    }

    @Test
    void getUser() {
        Optional<User> user = repository.getUser(0);
        assertTrue(user.isPresent());

        System.out.println(user.get());
    }

    @Test
    void getAllUsers() {
    }

    @Test
    void createUser() {
        String name = "Алексей";
        int price = 1500;
        String note = "note...";
        System.out.println(repository.createUser(name, price, note));
    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void addRecordToUser() {
    }

    @Test
    void updateRecordToUser() {
    }
}