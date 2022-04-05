package com.darkyver.javafxview.controllersView.accountingView;

import com.darkyver.domain.entity.Record;
import com.darkyver.domain.entity.User;
import com.darkyver.javafxview.ApplicationJavaFX;
import com.darkyver.javafxview.controllersView.AbstractController;
import com.darkyver.javafxview.controllersView.accountingView.elements.RecordViewHBox;
import com.darkyver.javafxview.controllersView.accountingView.elements.UserListView;
import com.darkyver.javafxview.controllersView.userInfo.UserInfoController;
import com.darkyver.javafxview.controllersView.utils.AlertBuilder;
import com.darkyver.javafxview.controllersView.utils.UserComparatorById;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class AccountingViewController extends AbstractController implements Initializable {

    @FXML
    private SplitPane splitPane;
    @FXML
    private VBox recordsVBox;
    @FXML
    private Button btnAddLesson;
    @FXML
    private Button btnAddUser;
    @FXML
    private Button btnAddPayment;
    @FXML
    private ListView<UserListView> usersListView;
    private SelectedChangeRecordViewListener changeRecordViewListener;


    private Optional<User> currentUser = Optional.empty();


    @FXML
    private void addLessonPressed() {
        btnAddLesson.setStyle("-fx-background-color: #878eb0;");
    }

    @FXML
    private void addPaymentPressed() {
        btnAddPayment.setStyle("-fx-background-color: #878eb0;");
    }


    @FXML
    private void addLessonRelease() {
        btnAddLesson.setStyle("-fx-background-color: #454a63;");
        if (currentUser.isEmpty()) return;

        Optional<ButtonType> res = AlertBuilder.builder()
                .setTitle("Добавление новой записи")
                .setHeaderText("Новое занятие проведено?")
                .setType(Alert.AlertType.CONFIRMATION)
                .build().showAndWait();

        User user = currentUser.get();
        Record record = new Record();
        record.setLessonDoneTime(System.currentTimeMillis());
        Optional<Record> optionalRecord = getConfig().createNewRecord(user.getId(), record);
        if (optionalRecord.isEmpty()) {
            warningAlert();
            return;
        }

        if (res.isPresent() && res.get().equals(ButtonType.OK)) {
            user.addRecord(optionalRecord.get());
            showAllRecordsByUser(user);
        }
    }


    @FXML
    private void addNewUserPressed() {
        btnAddUser.setStyle("-fx-background-color: #878eb0;");
    }

    @FXML
    private void addNewUserRelease() {
        btnAddUser.setStyle("-fx-background-color: #454a63;");
        try {

            FXMLLoader loader = new FXMLLoader(ApplicationJavaFX.class.getResource("views/create-user-view.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) splitPane.getScene().getWindow();
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.initModality(Modality.WINDOW_MODAL);
            newStage.initOwner(stage);
            newStage.setX(stage.getX() + stage.getWidth() / 2);
            newStage.setY(stage.getY());
            newStage.setResizable(false);
            newStage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void addPaymentRelease() {
        btnAddPayment.setStyle("-fx-background-color: #454a63;");
        if (currentUser.isEmpty()) return;

        Optional<ButtonType> res = AlertBuilder.builder()
                .setTitle("Добавление новой записи")
                .setHeaderText("Новая оплата получена?")
                .setType(Alert.AlertType.CONFIRMATION)
                .build().showAndWait();

        User user = currentUser.get();
        Record record = new Record();
        record.setPaidTime(System.currentTimeMillis());
        Optional<Record> optionalRecord = getConfig().createNewRecord(user.getId(), record);
        if (optionalRecord.isEmpty()) {
            warningAlert();
            return;
        }

        if (res.isPresent() && res.get().equals(ButtonType.OK)) {
            user.addRecord(optionalRecord.get());
            showAllRecordsByUser(user);
        }
    }

    private void warningAlert() {
        AlertBuilder.builder()
                .setType(Alert.AlertType.WARNING)
                .setTitle("Ошибка :(")
                .setHeaderText("Что-то пошло не так при создании новой записи")
                .build().showAndWait();
    }

    private TreeSet<User> users = new TreeSet<>(new UserComparatorById());

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UserListViewCellFactory cellFactory = new UserListViewCellFactory();
        cellFactory.addCellActionListener(new OnButtonListener<User>() {
            @Override
            public void handle(User user) {
                try {
                    FXMLLoader loader = new FXMLLoader(ApplicationJavaFX.class.getResource("views/user-info.fxml"));
                    loader.setController(new UserInfoController(user));
                    Scene scene = new Scene(loader.load());

                    Stage stage = (Stage) splitPane.getScene().getWindow();
                    Stage newStage = new Stage();
                    newStage.setScene(scene);
                    newStage.initModality(Modality.WINDOW_MODAL);
                    newStage.initOwner(stage);
                    newStage.setX(stage.getX() + stage.getWidth() / 2);
                    newStage.setY(stage.getY());
                    newStage.setResizable(false);
                    newStage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        usersListView.setCellFactory(cellFactory);
        getConfig().connectInBackground();
        recordsVBox.getChildren().clear();
        btnAddPayment.setDisable(true);
        btnAddLesson.setDisable(true);
        btnAddUser.setDisable(true);
        changeRecordViewListener = new SelectedChangeRecordViewListener();

        usersListView.getSelectionModel().selectedItemProperty().addListener((obs, old, current) -> {
            if (current == null) return;
            User user = current.getUser();
            showAllRecordsByUser(user);
            currentUser = Optional.of(user);
        });

        getConfig().addUserListChangeListener(list -> {
            if (!users.isEmpty()) return;
            users.addAll(list);
            Platform.runLater(() -> {
                usersListView.getItems().clear();
                for (User user : users) {
                    addNewUserToListView(user);
                }
                btnAddUser.setDisable(false);
            });
        });


        getConfig().addUserListChangeListener(list -> {
            if (users.isEmpty()) return;
            if (list.size() == users.size()) return;
            if (list.size() > users.size()) users.addAll(list);
            if (list.size() < users.size()) users.retainAll(list);
            Platform.runLater(() -> {
                usersListView.getItems().clear();
                users.forEach(user -> addNewUserToListView(user));
            });
        });

    }

    private void showAllRecordsByUser(User user) {
        recordsVBox.getChildren().clear();

        Collection<Record> records = user.getRecordMap().values();
        records.stream().sorted((r1, r2) -> (int) (r2.getId() - r1.getId())).forEach(record -> addRecordViewHB(user, record));

        btnAddPayment.setDisable(false);
        btnAddLesson.setDisable(false);
    }

    private void addRecordViewHB(User user, Record record) {
        RecordViewHBox recordView = new RecordViewHBox(user.getId(), record);
        recordView.setOnRecordChangedListener(changeRecordViewListener);
        recordsVBox.getChildren().add(recordView);
    }

    private void addNewUserToListView(User user) {
        usersListView.getItems().add(new UserListView(user));
    }

    class SelectedChangeRecordViewListener implements ChangeRecordListener {
        @Override
        public void accept(int userId, Record record) {
            boolean result = getConfig().updateRecord(userId, record);

            if (!result) {
                AlertBuilder.builder()
                        .setHeaderText("Что-то пошло не так :(")
                        .setTitle("Warning alert").build()
                        .showAndWait();
            }

        }
    }
}
