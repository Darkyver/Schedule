package com.darkyver.javafxview.controllersView.accountingView.elements;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

public class CheckBoxElement extends VBox {

    private CheckBox checkBox = new CheckBox();
    private Label label = new Label("");
    public CheckBoxElement(String checkBoxText) {
        setCheckBoxFALSE(checkBoxText);

    }

    public void setCheckBoxFALSE(String checkBoxText){
        getChildren().clear();

        checkBox.setSelected(false);
        checkBox.setAlignment(Pos.CENTER);
        checkBox.setContentDisplay(ContentDisplay.CENTER);
        checkBox.setMaxHeight(Double.MAX_VALUE);
        checkBox.setMaxWidth(Double.MAX_VALUE);
        checkBox.setText(checkBoxText);
        checkBox.setTextFill(Paint.valueOf("WHITE"));
        checkBox.setFont(new Font("Arial", 14.0));
        setVgrow(checkBox, Priority.ALWAYS);
        setStyle("-fx-background-color: #8c2a2a;");
        getChildren().add(checkBox);
    }

    public void setCheckBoxTRUE(String checkBoxText, String labelText){
        getChildren().clear();

        checkBox.setText(checkBoxText);
        checkBox.setSelected(true);
        checkBox.setAlignment(Pos.BOTTOM_CENTER);
        checkBox.setContentDisplay(ContentDisplay.CENTER);
        checkBox.setMaxHeight(Double.MAX_VALUE);
        checkBox.setMaxWidth(Double.MAX_VALUE);
        checkBox.setTextFill(Paint.valueOf("WHITE"));
        checkBox.setFont(new Font("Arial", 14.0));
        setVgrow(checkBox, Priority.ALWAYS);



        label.setAlignment(Pos.TOP_CENTER);
        label.setContentDisplay(ContentDisplay.CENTER);
        label.setMaxHeight(Double.MAX_VALUE);
        label.setMaxWidth(Double.MAX_VALUE);
        label.setTextFill(Paint.valueOf("WHITE"));
        setVgrow(label, Priority.ALWAYS);
        label.setText(labelText);


        setStyle("-fx-background-color:  #4c7551;");
        getChildren().add(checkBox);
        getChildren().add(label);

    }


    public void addChangeListener(ChangeListener<Boolean> changeListener){
        checkBox.selectedProperty().addListener(changeListener);
    }







}
