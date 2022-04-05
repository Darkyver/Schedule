package com.darkyver.javafxview.controllersView.accountingView.elements;

import com.darkyver.domain.entity.Record;
import com.darkyver.javafxview.ApplicationJavaFX;
import com.darkyver.javafxview.controllersView.accountingView.ChangeRecordListener;
import com.darkyver.javafxview.controllersView.utils.AlertBuilder;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.TimeZone;

public class RecordViewHBox extends HBox {

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("E, dd.MM.yyyy HH:mm");
    public static final String wasPaid = "Оплачено";
    public static final String wasNotPaid = "Не оплачено";
    public static final String lessonDone = "Занятие проведено";
    public static final String lessonNoDone = "Не проведено";

    private CheckBoxElement lessonCheckBox = new CheckBoxElement(lessonNoDone);
    private CheckBoxElement paymentCheckBox = new CheckBoxElement(wasNotPaid);
    private ChangeRecordListener recordChangedListener;

    public RecordViewHBox(int userID, Record record) {
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

        lessonCheckBox.addChangeListener((observableValue, aBoolean, t1) -> {
            if (t1) {
                Optional<ButtonType> res = showAlert("Занятие проведено?");
                if (res.isPresent() && res.get().equals(ButtonType.OK)) {
                    long time = System.currentTimeMillis();
                    record.setLessonDoneTime(time);
                    lessonCheckBox.setCheckBoxTRUE(lessonDone, longToTimeString(time));
                    recordChangedListener.accept(userID, record);
                }
            }else {
                Optional<ButtonType> res = showAlert("Занятие НЕ проведено?");
                if (res.isPresent() && res.get().equals(ButtonType.OK)) {
                    record.setLessonDoneTime(0);
                    lessonCheckBox.setCheckBoxFALSE(lessonNoDone);
                    recordChangedListener.accept(userID, record);
                }
            }
        });

        paymentCheckBox.addChangeListener((observableValue, aBoolean, t1) -> {
            if (t1) {
                Optional<ButtonType> res = showAlert("Оплата получена?");
                if (res.isPresent() && res.get().equals(ButtonType.OK)) {
                    long time = System.currentTimeMillis();
                    record.setPaidTime(time);
                    paymentCheckBox.setCheckBoxTRUE(wasPaid, longToTimeString(time));
                    recordChangedListener.accept(userID, record);
                }
            }else {
                Optional<ButtonType> res = showAlert("Оплата НЕ получена?");
                if (res.isPresent() && res.get().equals(ButtonType.OK)) {
                    record.setPaidTime(0);
                    paymentCheckBox.setCheckBoxFALSE(wasNotPaid);
                    recordChangedListener.accept(userID, record);
                }
            }
        });


    }

    private String longToTimeString(long lessonTime) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(lessonTime),
                TimeZone.getDefault().toZoneId());
        return localDateTime.format(dateTimeFormatter);
    }

    private Optional<ButtonType> showAlert(String headerText) {
//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//        alert.setTitle("Подтверждение...");
//        alert.setHeaderText(headerText);
//        return alert.showAndWait();

        return AlertBuilder.builder()
                .setTitle("Подтверждение...")
                .setHeaderText(headerText)
                .setType(Alert.AlertType.CONFIRMATION).build().showAndWait();
    }

    public CheckBoxElement getLessonCheckBox() {
        return lessonCheckBox;
    }

    public CheckBoxElement getPaymentCheckBox() {
        return paymentCheckBox;
    }

    public void setOnRecordChangedListener(ChangeRecordListener recordChangedListener) {
        this.recordChangedListener = recordChangedListener;
    }
}
