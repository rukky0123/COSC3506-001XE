package com.carrental.controllers.auth;

import com.carrental.SceneManager;
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
import java.sql.Timestamp;

import com.carrental.models.User;
import com.carrental.models.UserSession;

public class LoginController {
	@FXML
	public TextField usernameField;
	@FXML
	public PasswordField passwordField;
	@FXML
	public Label userErrorLabel;
	@FXML
	public Label passwordErrorLabel;
	@FXML
	public ImageView logoImage;

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

		User user = validateUser(username, password);

		if (user != null) {
			// Clear fields on success
			usernameField.clear();
			passwordField.clear();

			// Set the logged-in user in the session
			UserSession.setLoggedInUser(user);

			// Initialize scene based on role
			if (user.getRole().equals("Customer")) {
				SceneManager.initCustomerScenes();
				SceneManager.showScene("customerDashboard");
			} else if (user.getRole().equals("Admin")) {
				SceneManager.initAdminScenes();
				SceneManager.showScene("adminDashboard");
			} else if (user.getRole().equals("Staff")) {
				SceneManager.initStaffScenes();
				// SceneManager.showScene("customerDashboard");
			} else {
				showAlert("Role Error", "Role Error");
			}
		} else {
			userErrorLabel.setVisible(true);
			passwordErrorLabel.setVisible(true);
		}
	}

	private User validateUser(String username, String password) {
		String query = "SELECT user_id, username, password, role, email, created_at FROM CR_User WHERE username = ?";
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(query)) {

			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				String storedPassword = rs.getString("password");

				// Check password
				if (storedPassword.equals(hashPassword(password))) {
					// Return a User object upon successful validation
					int userId = rs.getInt("user_id");
					String role = rs.getString("role");
					String email = rs.getString("email");
					Timestamp createdAt = rs.getTimestamp("created_at");

					return new User(userId, username, storedPassword, role, email, createdAt);
				}
			}
			return null; // User not found or password mismatch
		} catch (SQLException e) {
			showAlert("Database Error", "Error accessing database: " + e.getMessage());
			return null;
		}
	}

	public String hashPassword(String password) {
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

	@FXML
	public void goToPolicy() {
		SceneManager.showScene("policy");
	}

	@FXML
	public void goToResetPassword() {
		SceneManager.showScene("resetpassword");
	}

}
