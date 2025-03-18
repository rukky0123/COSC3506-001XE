package com.carrental;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.Objects;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/carrental/ui/views/Login/Login.fxml"));
            VBox root = loader.load();

            // Load Logo Image
            ImageView logoImage = (ImageView) loader.getNamespace().get("logoImage");
            logoImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/carrental/ui/assets/harmoney.jpg"))));

            Scene scene = new Scene(root);

            // Apply CSS
            String css = Objects.requireNonNull(getClass().getResource("/com/carrental/ui/css/style.css")).toExternalForm();
            scene.getStylesheets().add(css);

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
