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
        SceneManager.loadScene("policy", "com/carrental/ui/views/auth/Policy.fxml");
        SceneManager.loadScene("resetpassword", "com/carrental/ui/views/auth/ResetPassword.fxml");

        // Set full-screen mode
        primaryStage.setTitle("Harmony - Car Rental System");
        primaryStage.setMaximized(true); // Maximizes window on launch

        // Show the login screen by default
        SceneManager.showScene("login");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
