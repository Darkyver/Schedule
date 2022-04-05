package com.darkyver.javafxview.controllersView.createUserView;

import com.darkyver.domain.entity.User;
import com.darkyver.javafxview.controllersView.AbstractController;
import com.darkyver.javafxview.controllersView.utils.AlertBuilder;
import javafx.beans.binding.ListBinding;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CreateUserController extends AbstractController implements Initializable {

    @FXML
    private TextField tfName;
    @FXML
    private TextField tfPrice;
    @FXML
    private TextArea taNote;

    @FXML
    private Button btnApply;
    @FXML
    private Button btnCancel;

    @FXML
    private void onTfNameTextChange(){

    }

    @FXML
    private void onTfPriceTextChange(KeyEvent keyEvent){

    }

    private boolean checkData(String name, String id, String note){
        if (name == null || id == null || note == null) {
            return false;
        }
        if (name.isEmpty() || id.isEmpty()) {
            return false;
        }
        if(usersListForCheckName.stream().map(User::getName).anyMatch(otherName -> otherName.equals(name))){
            return false;
        }
        try {
            Integer.parseInt(id);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    @FXML
    private void onBtnApply(){
        String name = tfName.getText();
        String note = taNote.getText();
        String priceStr = tfPrice.getText().trim();
        if(!checkData(name,priceStr, note)){
            AlertBuilder.builder()
                    .setType(Alert.AlertType.WARNING)
                    .setHeaderText("Error")
                    .setTitle("")
                    .build().showAndWait();
            return;
        }
        int price = Integer.parseInt(priceStr);
        new Thread(()-> getConfig().createUser(name, price, note)).start();

        ((Stage) btnApply.getScene().getWindow()).close();
    }

    @FXML
    private void onBtnCancel(){
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    private ObservableList<User> usersListForCheckName = FXCollections.observableList(new ArrayList<>());

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usersListForCheckName.addListener(new ListChangeListener<User>() {
            @Override
            public void onChanged(Change<? extends User> change) {
                switchColorOnTfName(tfName.isFocused());
            }
        });
        new Thread(()-> usersListForCheckName.setAll(getConfig().getAllUsers())).start();


        tfPrice.textProperty().addListener((abs, old, newValue)->{
            if (!newValue.matches("\\d*")) {
                tfPrice.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });


        tfName.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            switchColorOnTfName(isNowFocused);
        });
    }

    private void switchColorOnTfName(boolean isNowFocused){
        if(usersListForCheckName == null) return;
        if(!isNowFocused && !tfName.getText().isEmpty()){
            if (usersListForCheckName.stream().map(user -> user.getName()).anyMatch(name -> name.equals(tfName.getText()))) {
                tfName.setStyle("-fx-border-color: #a10000; -fx-background-color: #e0a2a2;");
            }else{
                tfName.setStyle("-fx-border-color: #00a118; -fx-background-color: #beebc5;");
            }
        }
        if(isNowFocused && !tfName.getText().isEmpty()){
            tfName.setStyle(null);
        }

    }
}
