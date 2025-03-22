package com.carrental.controllers.dashboards.AdminDashboard.components;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class NavbarController implements Initializable {

    @FXML private ImageView logoImage;
    @FXML private Hyperlink dashboardLink;
    @FXML private Hyperlink usersLink;
    @FXML private Hyperlink carsLink;
    @FXML private Hyperlink bookingsLink;
    private Consumer<String> onNavigate;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image image = new Image(getClass().getResourceAsStream("/com/carrental/ui/assets/harmoney.jpg"));
        if (image.isError()) {
            System.err.println("Error loading logo image.");
        } else {
            logoImage.setImage(image);
        }

        dashboardLink.setOnAction(e -> {
            if (onNavigate != null) {
                onNavigate.accept("reports");
            }
        });
        usersLink.setOnAction(e -> {
            if (onNavigate != null) {
                onNavigate.accept("users");
            }
        });
        carsLink.setOnAction(e -> {
            if (onNavigate != null) {
                onNavigate.accept("cars");
            }
        });
        bookingsLink.setOnAction(e -> {
            if (onNavigate != null) {
                onNavigate.accept("bookings");
            }
        });
    }

    public void setOnNavigate(Consumer<String> onNavigate) {
        this.onNavigate = onNavigate;
    }
}
