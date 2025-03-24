package com.carrental.database;

import com.carrental.models.Car;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CarDAOTest {

    private Car testCar;
    private int insertedCarId = -1;

    @BeforeEach
    void setup() {
        // Create a unique test car
        testCar = new Car(
                0,
                "TestMake",
                "TestModel_" + System.currentTimeMillis(),
                2025,
                150.00,
                true,
                "test_image.jpg"
        );

        boolean inserted = CarDAO.insertCar(testCar);
        assertTrue(inserted, "Car should be inserted successfully.");

        // Fetch back the inserted car to get ID
        List<Car> cars = CarDAO.getAllCars();
        testCar = cars.stream()
                .filter(c -> c.getModel().equals(testCar.getModel()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Inserted car not found"));

        insertedCarId = testCar.getCarId();
    }

    @Test
    void testInsertCar() {
        assertNotEquals(0, testCar.getCarId(), "Inserted car should have a valid ID.");
    }

    @Test
    void testUpdateCar() {
        String updatedModel = testCar.getModel() + "_Updated";
        testCar.setModel(updatedModel);

        boolean updated = CarDAO.updateCar(testCar);
        assertTrue(updated, "Car should be updated.");

        Car fetched = CarDAO.getAllCars().stream()
                .filter(c -> c.getCarId() == testCar.getCarId())
                .findFirst()
                .orElse(null);

        assertNotNull(fetched);
        assertEquals(updatedModel, fetched.getModel(), "Updated car model should match.");
    }

    @Test
    void testGetAllCars() {
        List<Car> cars = CarDAO.getAllCars();
        assertNotNull(cars);
        assertTrue(cars.size() > 0);
    }

    @Test
    void testDeleteCar() {
        boolean deleted = CarDAO.deleteCar(insertedCarId);
        assertTrue(deleted, "Car should be deleted.");

        boolean stillExists = CarDAO.getAllCars().stream()
                .anyMatch(c -> c.getCarId() == insertedCarId);
        assertFalse(stillExists, "Car should no longer exist.");

        insertedCarId = -1;
    }

    @AfterEach
    void cleanup() {
        if (insertedCarId != -1) {
            CarDAO.deleteCar(insertedCarId);
        }
    }
}
