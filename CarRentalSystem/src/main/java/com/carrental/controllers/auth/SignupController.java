package com.carrental.controllers.auth;

import com.carrental.SceneManager;
import com.carrental.DatabaseConnection;
import com.carrental.models.User;
import com.carrental.models.UserSession;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class SignupController {

    @FXML private TextField usernameField;
    @FXML private TextField firstNameField, lastNameField, emailField, confirmEmailField;
    @FXML private PasswordField passwordField, confirmPasswordField;
    @FXML private TextField address1Field, address2Field, cityField, provinceField, postalCodeField, countryField;
    @FXML private TextField cardNameField, cardNumberField, expMonthField, expYearField, securityCodeField, cardPostalField, cardCountryField;

    @FXML private Label errorUsername, errorFirstName, errorLastName, errorEmail, errorConfirmEmail;
    @FXML private Label errorPassword, errorConfirmPassword, errorAddress1, errorCity;
    @FXML private Label errorProvince, errorZip, errorCountry;
    @FXML private Label errorCardName, errorCardNumber, errorExp, errorCvv, errorCardZip, errorCardCountry;

    @FXML private ImageView logoImage;

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
    public void resetAndShow() {
        clearFields();
    }
    
    @FXML
    private void clearFields() {
        usernameField.clear();
        firstNameField.clear();
        lastNameField.clear();
        emailField.clear();
        confirmEmailField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
        address1Field.clear();
        address2Field.clear();
        cityField.clear();
        provinceField.clear();
        postalCodeField.clear();
        countryField.clear();
        cardNameField.clear();
        cardNumberField.clear();
        expMonthField.clear();
        expYearField.clear();
        securityCodeField.clear();
        cardPostalField.clear();
        cardCountryField.clear();
    }

    @FXML
    public void handleSignup() {
        boolean valid = true;
        clearErrors();

        if (usernameField.getText().isEmpty()) {
            errorUsername.setText("Enter a valid username.");
            valid = false;
        }
        if (firstNameField.getText().isEmpty()) {
            errorFirstName.setText("Enter a valid first name.");
            valid = false;
        }
        if (lastNameField.getText().isEmpty()) {
            errorLastName.setText("Enter a valid last name.");
            valid = false;
        }
        if (!emailField.getText().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            errorEmail.setText("Enter a valid email address.");
            valid = false;
        } else if (!emailField.getText().equals(confirmEmailField.getText())) {
            errorConfirmEmail.setText("Email addresses do not match.");
            valid = false;
        }
        if (!isValidPassword(passwordField.getText())) {
            errorPassword.setText("Password must be 8+ chars with uppercase, lowercase, and special char.");
            valid = false;
        } else if (!passwordField.getText().equals(confirmPasswordField.getText())) {
            errorConfirmPassword.setText("Passwords do not match.");
            valid = false;
        }
        if (address1Field.getText().isEmpty()) {
            errorAddress1.setText("Enter a valid Address Line 1.");
            valid = false;
        }
        if (cityField.getText().isEmpty()) {
            errorCity.setText("Enter a valid City.");
            valid = false;
        }
        if (provinceField.getText().isEmpty()) {
            errorProvince.setText("Enter a valid Province/State.");
            valid = false;
        }
        if (postalCodeField.getText().isEmpty()) {
            errorZip.setText("Enter a valid Postal/Zip Code.");
            valid = false;
        }
        if (countryField.getText().isEmpty()) {
            errorCountry.setText("Enter a valid Country.");
            valid = false;
        }
        if (cardNameField.getText().isEmpty()) {
            errorCardName.setText("Enter Card Name.");
            valid = false;
        }
        if (!cardNumberField.getText().matches("\\d{12,19}")) {
            errorCardNumber.setText("Enter Card Number.");
            valid = false;
        }
        if (expMonthField.getText().isEmpty() || expYearField.getText().isEmpty()) {
            errorExp.setText("Select Month/Year.");
            valid = false;
        }
        if (!securityCodeField.getText().matches("\\d{3,4}")) {
            errorCvv.setText("Enter Security code.");
            valid = false;
        }
        if (cardPostalField.getText().isEmpty()) {
            errorCardZip.setText("Enter Postal/Zip code.");
            valid = false;
        }
        if (cardCountryField.getText().isEmpty()) {
            errorCardCountry.setText("Enter Country.");
            valid = false;
        }

        if (!valid) return;

        if (registerUser(usernameField.getText(), emailField.getText(), passwordField.getText())) {
            showAlert("Success", "Account created successfully!");
            SceneManager.showScene("login");
        } else {
            showAlert("Error", "Signup failed. Try again.");
        }
    }

    private void clearErrors() {
        errorUsername.setText("");
        errorFirstName.setText("");
        errorLastName.setText("");
        errorEmail.setText("");
        errorConfirmEmail.setText("");
        errorPassword.setText("");
        errorConfirmPassword.setText("");
        errorAddress1.setText("");
        errorCity.setText("");
        errorProvince.setText("");
        errorZip.setText("");
        errorCountry.setText("");
        errorCardName.setText("");
        errorCardNumber.setText("");
        errorExp.setText("");
        errorCvv.setText("");
        errorCardZip.setText("");
        errorCardCountry.setText("");
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 8 && password.matches(".*[A-Z].*") && password.matches(".*[a-z].*") && password.matches(".*[!@#$%^&*].*");
    }

    private boolean registerUser(String username, String email, String password) {
        String query = "INSERT INTO CR_User (username, password, role, email, first_name, last_name, address1, address2, city, province, postal_code, country, card_name, card_number, exp_month, exp_year, security_code, card_postal, card_country) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, hashPassword(password));
            stmt.setString(3, "Customer");
            stmt.setString(4, email);

            stmt.setString(5, firstNameField.getText());
            stmt.setString(6, lastNameField.getText());
            stmt.setString(7, address1Field.getText());
            stmt.setString(8, address2Field.getText());
            stmt.setString(9, cityField.getText());
            stmt.setString(10, provinceField.getText());
            stmt.setString(11, postalCodeField.getText());
            stmt.setString(12, countryField.getText());
            stmt.setString(13, cardNameField.getText());
            stmt.setString(14, cardNumberField.getText());
            stmt.setString(15, expMonthField.getText());
            stmt.setString(16, expYearField.getText());
            stmt.setString(17, securityCodeField.getText());
            stmt.setString(18, cardPostalField.getText());
            stmt.setString(19, cardCountryField.getText());

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