package com.carrental.controllers.dashboards.AdminDashboard;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import com.carrental.SceneManager;
import com.carrental.models.UserSession;
import com.carrental.models.User;

public class AdminDashboardController {

    @FXML private Label welcomeLabel;
    @FXML private Label customersLabel;
    @FXML private Label staffLabel;
    @FXML private Label adminsLabel;
    @FXML private Label totalRevenueLabel;
    @FXML private Label totalRidesLabel;
    @FXML private Label totalProfitLabel;
    @FXML private Label totalTaxesLabel;
    @FXML private Label carsRentedLabel;
    @FXML private Label carsMaintenanceLabel;
    @FXML private Label carsAvailableLabel;

    @FXML
    public void initialize() {
        // Get the logged-in user from the session
        User loggedInUser = UserSession.getLoggedInUser();

        if (loggedInUser != null) {
            // Display the username of the logged-in user
            welcomeLabel.setText("Welcome, " + loggedInUser.getUsername() + "!");
        } else {
            welcomeLabel.setText("Welcome, Admin!");
        }

        // Placeholder data for the dashboard stats (replace with actual data)
        customersLabel.setText("88");
        staffLabel.setText("10");
        adminsLabel.setText("2");

        totalRevenueLabel.setText("65,000");
        totalRidesLabel.setText("396");
        totalProfitLabel.setText("44,000");
        totalTaxesLabel.setText("$11,000");

        carsRentedLabel.setText("24");
        carsMaintenanceLabel.setText("4");
        carsAvailableLabel.setText("4");
    }

    @FXML
    public void handleLogout() {
        // Logout logic: clear the session and navigate to the login page
        UserSession.logout();
        SceneManager.showScene("login");
    }
}
