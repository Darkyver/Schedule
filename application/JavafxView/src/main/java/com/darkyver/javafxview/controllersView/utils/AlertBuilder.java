package com.darkyver.javafxview.controllersView.utils;

import javafx.scene.control.Alert;

public class AlertBuilder {
    private static AlertBuilder instance = new AlertBuilder();

    private Alert alert = new Alert(Alert.AlertType.INFORMATION);

    public static AlertBuilder builder() {
        return instance;
    }


    public AlertBuilder setHeaderText(String headerText){
        alert.setHeaderText(headerText);
        return this;
    }

    public AlertBuilder setType(Alert.AlertType type){
        alert.setAlertType(type);
        return this;
    }

    public AlertBuilder setTitle(String title){
        alert.setTitle(title);
        return this;
    }

    public Alert build(){
        return alert;
    }


}
