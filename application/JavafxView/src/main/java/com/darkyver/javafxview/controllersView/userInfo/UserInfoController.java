package com.darkyver.javafxview.controllersView.userInfo;

import com.darkyver.domain.entity.User;
import com.darkyver.javafxview.controllersView.AbstractController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class UserInfoController extends AbstractController implements Initializable {
    private final User user;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfPrice;
    @FXML
    private TextArea taNote;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnCancel;

    @FXML
    private void onBtnCancel(){
        ((Stage) btnCancel.getScene().getWindow()).close();
    }

    @FXML
    private void onBtnSaveChanges(){
        String name = tfName.getText();
        int price;
        try {
            price = Integer.parseInt(tfPrice.getText());
        } catch (NumberFormatException e) {
            return;
        }
        String note = taNote.getText();
        user.setName(name);
        user.setPrice(price);
        user.setNote(note);
        getConfig().updateUser(user.getId(), user);
        onBtnCancel();
    }

    @FXML
    private void onBtnDelete(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("ВНИМАНИЕ!!!");
        alert.setHeaderText("Вы действительно хотите удалить пользователя " + user.getName());
        alert.setContentText("Вместе с ним удалятся все записи о про ведённых с ним занятиях и об оплатах!");
        Optional<ButtonType> buttonType = alert.showAndWait();
        if(buttonType.isEmpty() || !buttonType.get().equals(ButtonType.OK)) return;

        getConfig().deleteUser(user.getId());
        onBtnCancel();
    }

    public UserInfoController(User user) {
        this.user = user;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tfName.setText(user.getName());
        tfPrice.setText(String.valueOf(user.getPrice()));
        taNote.setText(user.getNote());

        Platform.runLater(() -> btnCancel.requestFocus());
    }
}
