package com.carrental.controllers.dashboards.CustomerDashboard;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import com.carrental.SceneManager;

public class CustomerDashboardController {
    @FXML private Label welcomeLabel;

    private static String loggedInUser;

    // This method will be called from LoginController to set the user's name
    public static void setLoggedInUser(String username) {
        loggedInUser = username;
    }

    @FXML
    public void initialize() {
        welcomeLabel.setText("Welcome, " + loggedInUser + "!");
    }

    @FXML
    public void handleLogout() {
        SceneManager.showScene("login");
    }
}
