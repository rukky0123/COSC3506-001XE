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

public class PolicyController {

	@FXML public ImageView logoImage;

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
	public void goToLogin() {
		SceneManager.showScene("login");
	}
}
