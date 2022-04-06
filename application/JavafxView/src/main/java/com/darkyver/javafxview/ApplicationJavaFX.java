package com.darkyver.javafxview;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class ApplicationJavaFX {
    public static class HelloApplication extends Application {
        public static void main() {
            launch();
        }

        @Override
        public void start(Stage stage) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("views/accounting-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Accounting");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }
    }
}