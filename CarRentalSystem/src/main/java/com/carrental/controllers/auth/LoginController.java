package com.carrental.controllers.auth;

import com.carrental.SceneManager;
import com.carrental.controllers.dashboards.CustomerDashboardController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.carrental.DatabaseConnection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label userErrorLabel;
    @FXML private Label passwordErrorLabel;
    @FXML private ImageView logoImage;

    @FXML
    public void initialize() {
        // Load the logo image
        Image image = new Image(getClass().getResourceAsStream("/com/carrental/ui/assets/harmoney.jpg"));
        if (image.isError()) {
            System.err.println("Error loading logo image.");
        } else {
            logoImage.setImage(image);
        }
    }

    @FXML
    public void handleLogin() {
        // Reset error labels before checking login
        userErrorLabel.setVisible(false);
        passwordErrorLabel.setVisible(false);

        String username = usernameField.getText();
        String password = passwordField.getText();

        if (validateUser(username, password)) {
            // Clear fields on success
            usernameField.clear();
            passwordField.clear();

            CustomerDashboardController.setLoggedInUser(username);
            showAlert("Login Successful", "Welcome " + username + "!");
            SceneManager.initCustomerScenes();
            SceneManager.showScene("customerDashboard");
        } else {
            userErrorLabel.setVisible(true);
            passwordErrorLabel.setVisible(true);
        }
    }

    private boolean validateUser(String username, String password) {
        String query = "SELECT password FROM CR_User WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedPassword = rs.getString("password");
                return storedPassword.equals(hashPassword(password));
            }
            return false;
        } catch (SQLException e) {
            showAlert("Database Error", "Error accessing database: " + e.getMessage());
            return false;
        }
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return password;
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
    }

    @FXML
    public void goToSignup() {
        SceneManager.showScene("signup");
    }
}
