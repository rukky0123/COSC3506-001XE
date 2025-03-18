package com.carrental;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SceneManager {
    private static Stage primaryStage;
    private static final Map<String, Scene> scenes = new HashMap<>();
    private static final Map<String, Object> controllers = new HashMap<>();
    private static final String CSS_PATH = "com/carrental/ui/css/style.css";

    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    public static void loadScene(String name, String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getClassLoader().getResource(fxmlPath));
            if (loader.getLocation() == null) {
                System.err.println("FXML file not found: " + fxmlPath);
                return;
            }
            Parent root = loader.load();
            scenes.put(name, new Scene(root));
            controllers.put(name, loader.getController());
        } catch (IOException e) {
            System.err.println("Error loading scene: " + fxmlPath);
            e.printStackTrace();
        }
    }

    public static void showScene(String name) {
        if (primaryStage != null && scenes.containsKey(name)) {
            Scene scene = scenes.get(name);

            // Ensure CSS is applied to all screens
            String css = SceneManager.class.getClassLoader().getResource(CSS_PATH).toExternalForm();
            scene.getStylesheets().clear(); // Clear previous styles
            scene.getStylesheets().add(css);

            primaryStage.setScene(scene);
            primaryStage.show();

            System.out.println("Applied CSS from: " + css + " to scene: " + name); // Debugging
        } else {
            System.err.println("Scene not found: " + name);
        }
    }


    public static Object getController(String name) {
        return controllers.get(name);
    }
}
