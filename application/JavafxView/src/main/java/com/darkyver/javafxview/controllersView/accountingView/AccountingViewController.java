package com.darkyver.javafxview.controllersView.accountingView;

import com.darkyver.domain.entity.Record;
import com.darkyver.domain.entity.User;
import com.darkyver.javafxview.ApplicationJavaFX;
import com.darkyver.javafxview.controllersView.AbstractController;
import com.darkyver.javafxview.controllersView.AddLessonController;
import com.darkyver.javafxview.controllersView.AddPaymentController;
import com.darkyver.javafxview.controllersView.accountingView.elements.RecordViewHBox;
import com.darkyver.javafxview.controllersView.accountingView.elements.UserListView;
import com.darkyver.javafxview.controllersView.userInfo.UserInfoController;
import com.darkyver.javafxview.controllersView.utils.AlertBuilder;
import com.darkyver.javafxview.controllersView.utils.UserComparatorById;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private final TreeMap<Integer, User> users = new TreeMap<>();


    private Optional<User> currentUser = Optional.empty();
    @FXML
    private void addLessonRelease() throws IOException {
        if (currentUser.isEmpty()) return;

        Optional<ButtonType> res = AlertBuilder.builder()
                .setTitle("Добавление новой записи")
                .setHeaderText("Новое занятие проведено?")
                .setType(Alert.AlertType.CONFIRMATION)
                .build().showAndWait();

        if(res.isEmpty() || !res.get().equals(ButtonType.OK)) return;

        User user = currentUser.get();
        FXMLLoader loader = new FXMLLoader(ApplicationJavaFX.class.getResource("views/add-lesson-view.fxml"));
        loader.setController(new AddLessonController(user));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) splitPane.getScene().getWindow();
        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.initModality(Modality.WINDOW_MODAL);
        newStage.initOwner(stage);
        newStage.setResizable(false);
        newStage.show();
    }
    @FXML
    private void addPaymentRelease() throws IOException {
        if (currentUser.isEmpty()) return;

        Optional<ButtonType> res = AlertBuilder.builder()
                .setTitle("Добавление новой записи")
                .setHeaderText("Новая оплата получена?")
                .setType(Alert.AlertType.CONFIRMATION)
                .build().showAndWait();

        if(res.isEmpty() || !res.get().equals(ButtonType.OK)) return;

        User user = currentUser.get();
        FXMLLoader loader = new FXMLLoader(ApplicationJavaFX.class.getResource("views/add-payment-view.fxml"));
        loader.setController(new AddPaymentController(user));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) splitPane.getScene().getWindow();
        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.initModality(Modality.WINDOW_MODAL);
        newStage.initOwner(stage);
        newStage.setResizable(false);
        newStage.show();
    }
    @FXML
    private void addNewUserRelease() {
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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UserListViewCellFactory cellFactory = new UserListViewCellFactory();
        cellFactory.addCellActionListener(user -> {
            FXMLLoader loader = new FXMLLoader(ApplicationJavaFX.class.getResource("views/user-info.fxml"));
            loader.setController(new UserInfoController(user));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) splitPane.getScene().getWindow();
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.initModality(Modality.WINDOW_MODAL);
            newStage.initOwner(stage);
            newStage.setResizable(false);
            newStage.show();
        });
        usersListView.setCellFactory(cellFactory);
        getConfig().connectInBackground();
        recordsVBox.getChildren().clear();
        btnAddPayment.setDisable(true);
        btnAddLesson.setDisable(true);
        btnAddUser.setDisable(true);

        usersListView.getSelectionModel().selectedItemProperty().addListener((obs, old, current) -> {
            if (current == null) return;
            User user = current.getUser();
            showAllRecordsByUser(user);
            currentUser = Optional.of(user);
        });

        getConfig().onUserAdd(user ->{
            Platform.runLater(() -> usersListView.getItems().add(new UserListView(user)));
        });
        getConfig().onUserRemove(user ->{
            Platform.runLater(() -> usersListView.getItems().remove(new UserListView(user)));
        });
        getConfig().onUserUpdate(user ->{
            if (currentUser.isEmpty()) {
                return;
            }
            if(currentUser.get().getId() == user.getId()) {
                Platform.runLater(() -> showAllRecordsByUser(user));
                currentUser = Optional.of(user);
            }
            Platform.runLater(()->{
                int index = usersListView.getItems().indexOf(new UserListView(user));
                usersListView.getItems().set(index, new UserListView(user));
            });


        });
        getConfig().onConnect(()->btnAddUser.setDisable(false));
        getConfig().onDisconnect(()->btnAddUser.setDisable(true));
    }

    private void showAllRecordsByUser(User user) {
        if(user == null) return;
        recordsVBox.getChildren().clear();
        TreeMap<Integer, Record> recordMap = (TreeMap<Integer, Record>) user.getRecordMap();
        recordMap.descendingMap().forEach((k,v) -> addRecordViewHB(v));
        btnAddPayment.setDisable(false);
        btnAddLesson.setDisable(false);
    }

    private void addRecordViewHB(Record record) {
        recordsVBox.getChildren().add(new RecordViewHBox(record));
    }
}
