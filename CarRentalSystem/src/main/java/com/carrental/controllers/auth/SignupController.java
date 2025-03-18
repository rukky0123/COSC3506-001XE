package com.carrental.controllers.auth;

import com.carrental.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.carrental.DatabaseConnection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SignupController {
    @FXML private TextField usernameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorUsername;
    @FXML private Label errorEmail;
    @FXML private Label errorPassword;
    @FXML private ImageView logoImage;

    @FXML
    public void initialize() {
        // Load logo image
        try {
            Image image = new Image(getClass().getResource("/com/carrental/ui/assets/harmoney.jpg").toExternalForm());
            logoImage.setImage(image);
        } catch (Exception e) {
            System.err.println("Error loading logo image.");
        }
    }

    @FXML
    public void handleSignup() {
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

        // Clear previous errors
        errorUsername.setText("");
        errorEmail.setText("");
        errorPassword.setText("");

        boolean valid = true;

        if (username.isEmpty()) {
            errorUsername.setText("Enter a valid User Name.");
            valid = false;
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            errorEmail.setText("Enter a valid email address.");
            valid = false;
        }

        if (password.length() < 8 || !password.matches(".*[A-Z].*") || !password.matches(".*[!@#$%^&*].*")) {
            errorPassword.setText("Password must be 8+ chars, 1 uppercase, 1 special char.");
            valid = false;
        }

        if (!valid) return;

        if (registerUser(email, password)) {
            showAlert("Success", "Account created successfully!");
            // Clear fields on success
            usernameField.clear();
            emailField.clear();
            passwordField.clear();
            SceneManager.showScene("login");
        } else {
            showAlert("Error", "Signup failed. Try again.");
        }
    }

    private boolean registerUser(String email, String password) {
        String query = "INSERT INTO CR_User (username, password, role, email) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, hashPassword(password));
            stmt.setString(3, "Customer");
            stmt.setString(4, email);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            showAlert("Database Error", "Error registering user: " + e.getMessage());
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
    public void goToLogin() {
        SceneManager.showScene("login");
    }
}
