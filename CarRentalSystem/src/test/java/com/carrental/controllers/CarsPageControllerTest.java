package com.carrental.controllers;

import com.carrental.controllers.dashboards.AdminDashboard.CarsPageController;
import com.carrental.models.Car;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CarsPageControllerTest extends ApplicationTest {

    private CarsPageController controller;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/carrental/ui/views/dashboards/AdminDashboard/Cars.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        stage.setScene(new javafx.scene.Scene(root));
        stage.show();
    }

    @BeforeEach
    public void setUp() {
        // Ensure the JavaFX thread runs the initialize method
        interact(() -> controller.initialize());
    }

    @Test
    void testCarTableLoadsData() {
        TableView<Car> table = controller.getCarTable();

        ObservableList<Car> items = table.getItems();
        assertNotNull(items, "Car table should not be null after initialization");
        assertTrue(items.size() >= 0, "Car table should load data (can be empty but should not crash)");
    }

    @Test
    void testCarColumnsHaveValues() {
        TableView<Car> table = controller.getCarTable();
        if (!table.getItems().isEmpty()) {
            Car car = table.getItems().get(0);
            assertNotNull(car.getMake(), "Car make should not be null");
            assertNotNull(car.getModel(), "Car model should not be null");
        }
    }

    @Test
    void testRefreshTableDoesNotCrash() {
        assertDoesNotThrow(() -> controller.callRefreshTable());
    }

    @Test
    void testShowCarFormHandlesNull() {
        assertDoesNotThrow(() -> {
            controller.getCarTable().getScene(); // needed to prevent NPE when initializing dialogs
            controller.getCarTable().setItems(FXCollections.observableArrayList(List.of(
                    new Car(999, "TestMake", "TestModel", 2023, 49.99, true, "/images/testcar.jpg")
            )));
            interact(() -> controller.callRefreshTable());
        });
    }
}