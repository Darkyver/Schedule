package com.darkyver.domain.entity;

import java.util.Objects;

public class Record {
    private long id;
    private long lessonDoneTime;
    private long paidTime;

    public Record() {
    }

    public Record(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getLessonDoneTime() {
        return lessonDoneTime;
    }

    public void setLessonDoneTime(long lessonDoneTime) {
        this.lessonDoneTime = lessonDoneTime;
    }

    public long getPaidTime() {
        return paidTime;
    }

    public void setPaidTime(long paidTime) {
        this.paidTime = paidTime;
    }

    public Record cloneRecord(Record record){
        if(record == null) return this;
        this.lessonDoneTime = record.lessonDoneTime;
        this.paidTime = record.paidTime;
        return this;
    }


    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", lessonDoneTime=" + lessonDoneTime +
                ", paidTime=" + paidTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Record record = (Record) o;
        return id == record.id && lessonDoneTime == record.lessonDoneTime && paidTime == record.paidTime;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lessonDoneTime, paidTime);
    }
}
