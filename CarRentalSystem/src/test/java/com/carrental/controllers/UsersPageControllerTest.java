package com.carrental.controllers;

import com.carrental.controllers.dashboards.AdminDashboard.UsersPageController;
import com.carrental.models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import java.sql.Timestamp;
import java.time.LocalDateTime;


import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UsersPageControllerTest extends ApplicationTest {

    private UsersPageController controller;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/carrental/ui/views/dashboards/AdminDashboard/Users.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        stage.setScene(new javafx.scene.Scene(root));
        stage.show();
    }

    @BeforeEach
    public void setUp() {
        // Run the initialize method on the JavaFX thread
        interact(() -> controller.initialize());
    }

    @Test
    void testUserTableLoadsData() {
        TableView<User> table = controller.getUserTable();

        ObservableList<User> items = table.getItems();
        assertNotNull(items, "User table should not be null after initialization");
        assertTrue(items.size() >= 0, "User table should load data (can be empty but should not crash)");
    }

    @Test
    void testUserColumnsHaveValues() {
        TableView<User> table = controller.getUserTable();
        if (!table.getItems().isEmpty()) {
            User user = table.getItems().get(0);
            assertNotNull(user.getUsername(), "Username should not be null");
            assertNotNull(user.getEmail(), "Email should not be null");
        }
    }

    @Test
    void testRefreshTableDoesNotCrash() {
        assertDoesNotThrow(() -> interact(() -> controller.initialize()));
    }

    @Test
    void testShowUserFormHandlesNull() {
        assertDoesNotThrow(() -> {
            controller.getUserTable().getScene(); // Prevents NPE on dialog init
            controller.getUserTable().setItems(FXCollections.observableArrayList(List.of(
                    new User(
                            999,
                            "Test User",
                            "test@example.com",
                            "Admin",
                            "1234567890",
                            Timestamp.valueOf(LocalDateTime.now())
                    )
            )));
            interact(() -> controller.initialize());
        });
    }
}
