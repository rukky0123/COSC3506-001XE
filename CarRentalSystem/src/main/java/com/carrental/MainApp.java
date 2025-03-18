package com.carrental;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        SceneManager.setPrimaryStage(primaryStage);

        // Load screens at startup
        SceneManager.loadScene("login", "com/carrental/ui/views/auth/Login.fxml");
        SceneManager.loadScene("signup", "com/carrental/ui/views/auth/Signup.fxml");

        // Show the login screen by default
        SceneManager.showScene("login");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
