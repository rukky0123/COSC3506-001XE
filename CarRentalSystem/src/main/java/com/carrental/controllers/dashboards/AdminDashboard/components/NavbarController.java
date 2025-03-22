package com.carrental.controllers.dashboards.AdminDashboard.components;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class NavbarController implements Initializable {

    @FXML
    private ImageView logoImage;
    @FXML private Hyperlink dashboardLink;
    @FXML private Hyperlink usersLink;
    @FXML private Hyperlink carsLink;
    @FXML private Hyperlink bookingsLink;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image image = new Image(getClass().getResourceAsStream("/com/carrental/ui/assets/harmoney.jpg"));
        if (image.isError()) {
            System.err.println("Error loading logo image.");
        } else {
            logoImage.setImage(image);
        }

        // Optional: Add navigation logic here
        dashboardLink.setOnAction(e -> System.out.println("Go to Dashboard"));
        usersLink.setOnAction(e -> System.out.println("Go to Users"));
        carsLink.setOnAction(e -> System.out.println("Go to Cars"));
        bookingsLink.setOnAction(e -> System.out.println("Go to Bookings"));
    }
}
