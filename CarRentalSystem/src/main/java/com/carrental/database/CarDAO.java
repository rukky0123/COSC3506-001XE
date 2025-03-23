package com.carrental.database;

import com.carrental.DatabaseConnection;
import com.carrental.models.Car;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarDAO {

    public static List<Car> getAllCars() {
        List<Car> cars = new ArrayList<>();
        String query = "SELECT * FROM CR_Inventory ORDER BY car_id DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Car car = new Car(
                        rs.getInt("car_id"),
                        rs.getString("make"),
                        rs.getString("model"),
                        rs.getInt("year"),
                        rs.getDouble("price_per_day"),
                        rs.getBoolean("availability")
                );
                cars.add(car);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cars;
    }

    public static boolean insertCar(Car car) {
        String query = "INSERT INTO CR_Inventory (make, model, year, price_per_day, availability) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, car.getMake());
            stmt.setString(2, car.getModel());
            stmt.setInt(3, car.getYear());
            stmt.setDouble(4, car.getPricePerDay());
            stmt.setBoolean(5, car.isAvailability());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateCar(Car car) {
        String query = "UPDATE CR_Inventory SET make = ?, model = ?, year = ?, price_per_day = ?, availability = ? WHERE car_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, car.getMake());
            stmt.setString(2, car.getModel());
            stmt.setInt(3, car.getYear());
            stmt.setDouble(4, car.getPricePerDay());
            stmt.setBoolean(5, car.isAvailability());
            stmt.setInt(6, car.getCarId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteCar(int carId) {
        String query = "DELETE FROM CR_Inventory WHERE car_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, carId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}