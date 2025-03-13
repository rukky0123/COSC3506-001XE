package com.carrental;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/carrental/ui/Login.fxml"));
            VBox root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Car Rental System");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1); // Force exit if FXML fails to load
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
