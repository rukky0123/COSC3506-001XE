package com.carrental.controllers.dashboards.AdminDashboard.components;

import com.carrental.models.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.function.Consumer;

public class UserFormDialogController {

    @FXML private Label formTitle;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private TextField emailField;
    @FXML private ComboBox<String> roleBox;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    @FXML private Label errorLabel;

    private User user; // null = new user
    private Consumer<User> onSaveCallback;

    @FXML
    public void initialize() {
        roleBox.getItems().addAll("admin", "staff", "customer");

        cancelButton.setOnAction(e -> closeDialog());
        saveButton.setOnAction(e -> saveUser());
    }

    public void setUser(User user, Consumer<User> onSaveCallback) {
        this.user = user;
        this.onSaveCallback = onSaveCallback;

        if (user != null) {
            formTitle.setText("Edit User");
            usernameField.setText(user.getUsername());
            // Leave password field empty on edit
            emailField.setText(user.getEmail());
            roleBox.setValue(user.getRole());
        } else {
            formTitle.setText("Add User");
        }
    }

    private void saveUser() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        String email = emailField.getText().trim();
        String role = roleBox.getValue();

        // ✅ Validate required fields
        if (username.isEmpty() || email.isEmpty() || role == null) {
            errorLabel.setText("Username, Email, and Role are required.");
            return;
        }

        // ✅ Email format validation
        if (!email.matches("^\\S+@\\S+\\.\\S+$")) {
            errorLabel.setText("Invalid email address.");
            return;
        }

        // ✅ For new user, password is mandatory
        if (user == null && password.isEmpty()) {
            errorLabel.setText("Password is required for new users.");
            return;
        }

        // ✅ Password length check if provided
        if (!password.isEmpty() && password.length() < 6) {
            errorLabel.setText("Password must be at least 6 characters.");
            return;
        }

        if (user == null) {
            String hashed = hashPassword(password);
            user = new User(0, username, hashed, role, email, null);
        } else {
            user.setUsername(username);
            if (!password.isEmpty()) {
                String hashed = hashPassword(password);
                user.setPassword(hashed);
            }
            user.setEmail(email);
            user.setRole(role);
        }

        errorLabel.setText(""); // Clear any previous errors
        onSaveCallback.accept(user);
        closeDialog();
    }

    private void closeDialog() {
        ((Stage) saveButton.getScene().getWindow()).close();
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return password; // Fallback (not ideal)
        }
    }
}
