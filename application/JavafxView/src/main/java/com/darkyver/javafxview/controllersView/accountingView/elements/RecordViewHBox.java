package com.darkyver.javafxview.controllersView.accountingView.elements;

import com.darkyver.domain.entity.Record;
import com.darkyver.javafxview.ApplicationJavaFX;
import com.darkyver.javafxview.controllersView.accountingView.ChangeRecordListener;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

public class RecordViewHBox extends HBox {

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("E, dd.MM.yyyy HH:mm");
    public static final String wasPaid = "Оплачено";
    public static final String wasNotPaid = "Не оплачено";
    public static final String lessonDone = "Занятие проведено";
    public static final String lessonNoDone = "Не проведено";

    private CheckBoxElement lessonCheckBox = new CheckBoxElement(lessonNoDone);
    private CheckBoxElement paymentCheckBox = new CheckBoxElement(wasNotPaid);

    public RecordViewHBox(Record record) {
        setStyle("-fx-background-color: #4c7551;");
        setPrefHeight(60);
        getChildren().add(lessonCheckBox);
        getChildren().add(paymentCheckBox);
        getStyleClass().add(ApplicationJavaFX.class.getResource("css/application.css").toExternalForm());
        setHgrow(lessonCheckBox, Priority.ALWAYS);
        setHgrow(paymentCheckBox, Priority.ALWAYS);


        long lessonTime = record.getLessonDoneTime();
        long paymentTime = record.getPaidTime();
        if (lessonTime != 0) {
            lessonCheckBox.setCheckBoxTRUE(lessonDone, longToTimeString(lessonTime));
        }
        if (paymentTime != 0) {
            paymentCheckBox.setCheckBoxTRUE(wasPaid, longToTimeString(paymentTime));
        }

    }

    private String longToTimeString(long lessonTime) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(lessonTime),
                TimeZone.getDefault().toZoneId());
        return localDateTime.format(dateTimeFormatter);
    }

}
