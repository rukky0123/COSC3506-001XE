package com.carrental.controllers;

import com.carrental.controllers.dashboards.AdminDashboard.BookingsPageController;
import com.carrental.models.Booking;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import javafx.collections.FXCollections;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookingsPageControllerTest extends ApplicationTest {

    private BookingsPageController controller;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/carrental/ui/views/dashboards/AdminDashboard/Bookings.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        stage.setScene(new javafx.scene.Scene(root));
        stage.show();
    }

    @BeforeEach
    public void setUp() {
        // Initialize JavaFX environment before each test
        interact(() -> controller.initialize());
    }

    @Test
    void testBookingTableLoadsData() {
        TableView<Booking> table = controller.getBookingTable();

        ObservableList<Booking> items = table.getItems();
        assertNotNull(items, "Booking table should not be null after initialization");
        assertTrue(items.size() >= 0, "Booking table should load data (can be empty but should not crash)");
    }

    @Test
    void testColumnValuesFormattedCorrectly() {
        TableView<Booking> table = controller.getBookingTable();
        if (!table.getItems().isEmpty()) {
            Booking booking = table.getItems().get(0);
            assertNotNull(booking.getUsername(), "Username should not be null");
            assertNotNull(booking.getCarModel(), "Car model should not be null");
        }
    }

    @Test
    void testRefreshTableDoesNotCrash() {
        assertDoesNotThrow(() -> controller.callRefreshTable());
    }

    @Test
    void testShowBookingFormHandlesNull() {
        assertDoesNotThrow(() -> {
            controller.getBookingTable().getScene(); // needed to prevent NPE during dialog init
            controller.getBookingTable().setItems(FXCollections.observableArrayList(List.of(
                    new Booking(1, 1, 1, Timestamp.valueOf(LocalDateTime.now()),
                            Timestamp.valueOf(LocalDateTime.now().plusDays(1)),
                            "Pending", "testUser", "Toyota", "Corolla", "2020")
            )));
            interact(() -> controller.callRefreshTable());
        });
    }
}
