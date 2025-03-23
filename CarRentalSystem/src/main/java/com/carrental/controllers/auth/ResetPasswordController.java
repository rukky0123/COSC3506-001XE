// ResetPasswordController.java
package com.carrental.controllers.auth;

import com.carrental.DatabaseConnection;
import com.carrental.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.application.Platform;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

public class ResetPasswordController {

    @FXML private ImageView logoImage;
    @FXML private TextField emailField, otpField;
    @FXML private PasswordField passwordField, confirmPasswordField;
    @FXML private Label emailError, otpInfo, otpError, passwordError, confirmPasswordError;

    private String generatedOtp;
    private Timer otpTimer;

    @FXML
    public void initialize() {
        try {
            Image image = new Image(getClass().getResource("/com/carrental/ui/assets/harmoney.jpg").toExternalForm());
            logoImage.setImage(image);
        } catch (Exception e) {
            System.err.println("Error loading logo image.");
        }
    }

    @FXML
    public void handleSendOtp() {
        clearErrors();
        String email = emailField.getText();
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            emailError.setText("Please enter a valid email address.");
            return;
        }

        if (!emailExists(email)) {
            emailError.setText("Email not found in our records.");
            return;
        }

        generatedOtp = String.format("%04d", new SecureRandom().nextInt(10000));
        showOtpDialog(generatedOtp);
        otpInfo.setText("We have sent an OTP to " + email + "\nPasscode will expire in 30 seconds");

        if (otpTimer != null) otpTimer.cancel();
        otpTimer = new Timer();
        otpTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                generatedOtp = null;
                Platform.runLater(() -> otpError.setText("OTP expired. Please request again."));
            }
        }, 30000);
    }

    @FXML
    public void handleResetPassword() {
        clearErrors();
        String code = otpField.getText();
        String newPass = passwordField.getText();
        String confirmPass = confirmPasswordField.getText();

        if (generatedOtp == null || !generatedOtp.equals(code)) {
            otpError.setText("Invalid or expired OTP. Please try again.");
            return;
        }

        if (!isValidPassword(newPass)) {
            passwordError.setText("The password must be over 8 characters long and include\n" +
                    "an uppercase letter, a lowercase letter, and a special character.");
            return;
        }
        if (!newPass.equals(confirmPass)) {
            confirmPasswordError.setText("Password do not match. Please re-enter.");
            return;
        }

        updatePassword(emailField.getText(), newPass);
        showAlert("Success", "Password updated successfully!");
        SceneManager.showScene("login");
    }

    private boolean emailExists(String email) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT user_id FROM CR_User WHERE email = ?")) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            emailError.setText("Database error. Please try again.");
            return false;
        }
    }

    private void updatePassword(String email, String password) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE CR_User SET password = ? WHERE email = ?")) {
            stmt.setString(1, hashPassword(password));
            stmt.setString(2, email);
            stmt.executeUpdate();
        } catch (SQLException e) {
            showAlert("Error", "Database error: " + e.getMessage());
        }
    }

    private String hashPassword(String password) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            return password;
        }
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 8 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[a-z].*") &&
                password.matches(".*[!@#$%^&*].*");
    }

    private void clearErrors() {
        emailError.setText(""); otpError.setText(""); passwordError.setText(""); confirmPasswordError.setText("");
    }

    private void showOtpDialog(String otp) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("OTP Code");
        alert.setHeaderText("Your OTP Code");
        alert.setContentText("Use this code to reset your password: " + otp);
        alert.showAndWait();
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
