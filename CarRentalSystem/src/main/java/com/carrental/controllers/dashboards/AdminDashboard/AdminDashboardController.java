package com.carrental.controllers.dashboards.AdminDashboard;

import com.carrental.controllers.dashboards.AdminDashboard.components.NavbarController;
import javafx.fxml.FXML;
import com.carrental.SceneManager;
import com.carrental.models.UserSession;
import com.carrental.models.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminDashboardController {
    @FXML private ImageView logoImage;
    @FXML private HBox navbarContainer;
    @FXML private StackPane contentArea;

    @FXML
    public void initialize() {
        try {
            // Load Navbar FXML manually
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/carrental/ui/views/dashboards/AdminDashboard/components/Navbar.fxml"));
            HBox navbar = loader.load();

            // Set to the container in the VBox
            HBox.setHgrow(navbar, Priority.ALWAYS);
            navbar.getStyleClass().add("navbar");
            navbar.getStylesheets().add(getClass().getResource("/com/carrental/ui/css/dashboard.css").toExternalForm());
            navbarContainer.getChildren().add(navbar);

            // Get controller and setup navigation
            NavbarController navbarController = loader.getController();
            navbarController.setOnNavigate(this::handleNavigation);
            navbarController.setOnLogout(this::handleLogout);

            User user = UserSession.getLoggedInUser();
            if (user != null) {
                navbarController.setUsername(user.getUsername());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Default page load
        loadPage("reports");
    }

    @FXML
    public void handleLogout() {
        // Logout logic: clear the session and navigate to the login page
        UserSession.logout();
        SceneManager.showScene("login");
    }

    private void handleNavigation(String page) {
        switch (page) {
            case "users":
                loadPage("users");
                break;
            case "reports":
                loadPage("reports");
                break;
            case "bookings":
                loadPage("bookings");
                break;
            case "cars":
                loadPage("cars");
                break;
        }
    }

    private void loadPage(String pageName) {
        try {
            String fxmlPath = switch (pageName) {
                case "users" -> "/com/carrental/ui/views/dashboards/AdminDashboard/Users.fxml";
                case "cars" -> "/com/carrental/ui/views/dashboards/AdminDashboard/Cars.fxml";
                case "bookings" -> "/com/carrental/ui/views/dashboards/AdminDashboard/Bookings.fxml";
                case "reports" -> "/com/carrental/ui/views/dashboards/AdminDashboard/Reports.fxml";
                default -> "/com/carrental/ui/views/dashboards/AdminDashboard/Reports.fxml";
            };

            Node view = FXMLLoader.load(getClass().getResource(fxmlPath));
            contentArea.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
