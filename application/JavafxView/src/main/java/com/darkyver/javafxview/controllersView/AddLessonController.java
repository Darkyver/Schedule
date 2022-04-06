package com.darkyver.javafxview.controllersView;

import com.darkyver.domain.entity.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ResourceBundle;

public class AddLessonController extends AbstractController implements Initializable {
    @FXML
    private Label label;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Button btnApply;
    @FXML
    private Button btnCancel;
    @FXML
    private ComboBox<String> cbAmount;

    private final User user;

    public AddLessonController(User user) {
        this.user = user;
    }


    public void btnCancel(ActionEvent event) {
        ((Stage) btnCancel.getScene().getWindow()).close();
    }

    public void btnApply(ActionEvent event) {
        LocalDate valueDate = datePicker.getValue();
        if(valueDate == null) return;
        String amountValue = cbAmount.getValue();
        int amount;
        try {
            amount = Integer.parseInt(amountValue);
        }catch (Exception e){
            System.out.println("что-то с числом...");
            return;
        }


        long time = valueDate.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli();
        boolean res = getConfig().addNewLessonsToUser(user.getId(), amount, time);
        if(!res) System.out.println("error when tried to add lesson to user");
        btnCancel(null);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            btnCancel.requestFocus();
            label.setText(label.getText() + " " + user.getName());
            datePicker.setValue(LocalDate.now());
        });
        datePicker.setDayCellFactory(getDayCellFactory());
//        datePicker.focusedProperty().addListener((obs, old, newValue) -> {
//            System.out.println("focused date picker changed");
////            if(!newValue & )datePicker.setValue(LocalDate.now());
//        });

    }

    // Factory to create Cell of DatePicker
    private Callback<DatePicker, DateCell> getDayCellFactory() {
        return new Callback<>() {
            @Override
            public DateCell call(final DatePicker datePicker1) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isAfter(LocalDate.now())) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }
                    }
                };
            }
        };
    }
}
