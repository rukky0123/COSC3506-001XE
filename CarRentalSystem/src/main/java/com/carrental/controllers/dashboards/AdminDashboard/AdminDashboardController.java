package com.carrental.controllers.dashboards.AdminDashboard;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import com.carrental.SceneManager;
import com.carrental.models.UserSession;
import com.carrental.models.User;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminDashboardController {
    @FXML
    private ImageView logoImage;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        Image image = new Image(getClass().getResource("/com/carrental/assets/logo.png").toExternalForm());
        logoImage.setImage(image);
        // Get the logged-in user from the session
        User loggedInUser = UserSession.getLoggedInUser();
    }

    @FXML
    public void handleLogout() {
        // Logout logic: clear the session and navigate to the login page
        UserSession.logout();
        SceneManager.showScene("login");
    }
}
