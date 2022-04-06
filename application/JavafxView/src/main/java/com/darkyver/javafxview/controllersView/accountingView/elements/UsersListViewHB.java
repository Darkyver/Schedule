package com.darkyver.javafxview.controllersView.accountingView.elements;

import com.darkyver.domain.entity.User;
import com.darkyver.javafxview.ApplicationJavaFX;
import com.darkyver.javafxview.controllersView.accountingView.OnButtonListener;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class UsersListViewHB extends HBox {

    private final User user;
    private final Button info;

    private Set<OnButtonListener<User>> infoListeners = new HashSet<>();

    public UsersListViewHB(User user) {
        this.user = user;
        Label label = new Label(this.user.getName());
        label.setMaxWidth(Double.MAX_VALUE);
        info = new Button("info");
        this.setMaxHeight(40);
        this.setHgrow(label, Priority.ALWAYS);

        getChildren().add(label);
        getChildren().add(info);


        info.setOnAction(event -> infoListeners.forEach(listener -> {
            try {
                listener.handle(user);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
    }

    public void addOnInfoBtnListener(OnButtonListener<User> listener){
        infoListeners.add(listener);
    }


}
