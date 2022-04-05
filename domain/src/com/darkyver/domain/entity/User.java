package com.darkyver.domain.entity;

import java.util.*;

public class User implements Comparable<User> {
    private int id;
    private String name;
    private String note;
    private int price;

    private Map<String, Record> recordMap = new HashMap<>();

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

//    public List<Record> getRecordList() {
//        return recordMap.values().stream().toList();
//    }

    public Map<String, Record> getRecordMap() {
        return recordMap;
    }

    public void setRecordMap(Map<String, Record> recordMap) {
        this.recordMap = recordMap;
    }

    public Record getRecord(int id){
        return recordMap.get(String.valueOf(id));
    }

    public boolean addRecord(Record record){
        String key = String.valueOf(record.getId());
        if (recordMap.containsKey(key)) return false;
        recordMap.put(key, record);
        return true;
    }

    public boolean updateRecord(Record record){
        String key = String.valueOf(record.getId());
        if (!recordMap.containsKey(key)) return false;
        recordMap.put(key, record);
        return  true;
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
