package com.carrental.controllers.auth;

import com.carrental.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.carrental.DatabaseConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label userErrorLabel;
    @FXML private Label passwordErrorLabel;

    @FXML
    public void handleLogin() {
        // Reset error labels before checking login
        userErrorLabel.setVisible(false);
        passwordErrorLabel.setVisible(false);

        String username = usernameField.getText();
        String password = passwordField.getText();

        if (validateUser(username, password)) {
            showAlert("Login Successful", "Welcome " + username + "!");

            // Clear fields on success
            usernameField.clear();
            passwordField.clear();

            // Future Navigation: Go to Dashboard (not implemented yet)
            // SceneManager.showScene("dashboard");

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
        alert.showAndWait();
    }

    @FXML
    public void goToSignup() {
        SceneManager.showScene("signup");
    }
}
