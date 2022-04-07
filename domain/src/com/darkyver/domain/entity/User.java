package com.darkyver.domain.entity;

import java.util.*;

public class User implements Comparable<User> {
    private int id;
    private String name;
    private String note;
    private int price;

    private Map<Integer, Record> recordMap = new TreeMap<>();

    public User() {
    }

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

    public Map<Integer, Record> getRecordMap() {
        return recordMap;
    }

    public void setRecordMap(Map<Integer, Record> recordMap) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && price == user.price && Objects.equals(name, user.name) && Objects.equals(note, user.note) && Objects.equals(recordMap, user.recordMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, note, price, recordMap);
    }

    @Override
    public int compareTo(User o) {
        return o.getId() - getId();
    }
}
