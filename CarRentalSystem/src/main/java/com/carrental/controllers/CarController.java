package com.carrental.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.carrental.DatabaseConnection;

public class CarController {
    private static Connection connection = DatabaseConnection.getConnection();

    public boolean addCar(String make, String model, int year, double pricePerDay) {
        String query = "INSERT INTO CR_Inventory (make, model, year, price_per_day, availability) VALUES (?, ?, ?, ?, TRUE)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, make);
            stmt.setString(2, model);
            stmt.setInt(3, year);
            stmt.setDouble(4, pricePerDay);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String> getAvailableCars() {
        List<String> cars = new ArrayList<>();
        String query = "SELECT * FROM CR_Inventory WHERE availability = TRUE";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                cars.add(rs.getString("make") + " " + rs.getString("model") + " - $" + rs.getDouble("price_per_day") + "/day");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    public boolean removeCar(int carId) {
        String query = "DELETE FROM CR_Inventory WHERE car_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, carId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}