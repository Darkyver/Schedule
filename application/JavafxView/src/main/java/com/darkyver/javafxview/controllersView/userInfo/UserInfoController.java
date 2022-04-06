package com.darkyver.javafxview.controllersView.userInfo;

import com.darkyver.domain.entity.User;
import com.darkyver.javafxview.controllersView.AbstractController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
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
    private void onBtnSavePressed(){

    }
    @FXML
    private void onBtnSaveRelease(){

    }

    @FXML
    private void onBtnCancel(){
        ((Stage) btnCancel.getScene().getWindow()).close();
    }

    @FXML
    private void onBtnSaveChanges(){

    }

    @FXML
    private void onBtnDelete(){

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
