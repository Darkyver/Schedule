package com.darkyver.firebase.pojo;

import com.darkyver.domain.entity.Record;
import com.darkyver.domain.entity.User;

import java.util.*;

public class UserFirebase implements Comparable<com.darkyver.domain.entity.User> {
    private int id;
    private String name;
    private String note;
    private int price;

    private Map<String, Record> recordMap = new TreeMap<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Map<String, Record> getRecordMap() {
        return recordMap;
    }

    public void setRecordMap(Map<String, Record> recordMap) {
        this.recordMap = recordMap;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", note='" + note + '\'' +
                ", price=" + price +
                ", recordMap=" + recordMap +
                '}';
    }

    public User toUser(){
        User user = new User();
        user.setNote(note);
        user.setPrice(price);
        user.setName(name);
        user.setId(id);
        TreeMap<Integer, Record> map = new TreeMap<>();
        recordMap.values().forEach(record -> map.put((int) record.getId(), record));
        user.setRecordMap(map);
        return user;
    }

    public static UserFirebase fromUser(User user){
        UserFirebase userFirebase = new UserFirebase();
        userFirebase.setId(user.getId());
        userFirebase.setName(user.getName());
        userFirebase.setNote(user.getNote());
        userFirebase.setPrice(user.getPrice());
        HashMap<String, Record> map = new HashMap<>();
        user.getRecordMap().values().forEach(record -> map.put(String.valueOf(record.getId()), record));
        userFirebase.setRecordMap(map);
        return userFirebase;
    }


    @Override
    public int hashCode() {
        return Objects.hash(id, name, note, price, recordMap);
    }

    @Override
    public int compareTo(com.darkyver.domain.entity.User o) {
        return o.getId() - getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserFirebase that = (UserFirebase) o;
        return id == that.id && price == that.price && Objects.equals(name, that.name) && Objects.equals(note, that.note) && Objects.equals(recordMap, that.recordMap);
    }
}
