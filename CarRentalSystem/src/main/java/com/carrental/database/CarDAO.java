
package com.carrental.database;

import com.carrental.DatabaseConnection;
import com.carrental.models.Car;

import java.sql.*;
import java.util.*;

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
                        rs.getBoolean("availability"),
                        rs.getString("image_path")
                );
                cars.add(car);
            }

        } catch (SQLException e) {
            System.err.println("Database error in getAllCars(): " + e.getMessage());
        }

        return cars;
    }

    public static boolean insertCar(Car car) {
        String query = "INSERT INTO CR_Inventory (make, model, year, price_per_day, availability, image_path) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, car.getMake());
            stmt.setString(2, car.getModel());
            stmt.setInt(3, car.getYear());
            stmt.setDouble(4, car.getPricePerDay());
            stmt.setBoolean(5, car.isAvailability());
            stmt.setString(6, car.getImagePath());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Database error in insertCar(): " + e.getMessage());
            return false;
        }
    }

    public static boolean updateCar(Car car) {
        String query = "UPDATE CR_Inventory SET make = ?, model = ?, year = ?, price_per_day = ?, availability = ?, image_path = ? WHERE car_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, car.getMake());
            stmt.setString(2, car.getModel());
            stmt.setInt(3, car.getYear());
            stmt.setDouble(4, car.getPricePerDay());
            stmt.setBoolean(5, car.isAvailability());
            stmt.setString(6, car.getImagePath());
            stmt.setInt(7, car.getCarId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Database error in updateCar(): " + e.getMessage());
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
            System.err.println("Database error in deleteCar(): " + e.getMessage());
            return false;
        }
    }
    
    public static Map<String, Integer> getCarUsageReport() {
        String query = "SELECT c.model, COUNT(*) AS rental_count " +
                       "FROM CR_Booking b " +  // Changed to CR_Booking
                       "JOIN CR_Inventory c ON b.car_id = c.car_id " +
                       "WHERE b.status IN ('Completed', 'Confirmed') " +
                       "GROUP BY c.model ORDER BY rental_count DESC";

        return executeReportQuery(query, "getCarUsageReport()");
    }

    public static Map<String, Integer> getMonthlyRentalsReport() {
        String query = "SELECT DATE_FORMAT(start_date, '%Y-%m') AS month, COUNT(*) AS total_rentals " +
                       "FROM CR_Booking " + // Changed to CR_Booking
                       "WHERE status IN ('Completed', 'Confirmed') " +
                       "GROUP BY month ORDER BY month";

        return executeReportQuery(query, "getMonthlyRentalsReport()");
    }

    public static Map<String, Double> getRevenueReport() {
        String query = "SELECT DATE_FORMAT(payment_date, '%Y-%m') AS month, SUM(amount) AS total_revenue " +
                       "FROM CR_Payment " +  // Changed to CR_Payment
                       "GROUP BY month ORDER BY month";

        return executeRevenueReportQuery(query, "getRevenueReport()");
    }

    public static Map<String, Integer> getCustomerBookingReport() {
        String query = "SELECT status, COUNT(*) AS total_bookings FROM CR_Booking GROUP BY status";

        return executeReportQuery(query, "getCustomerBookingReport()");
    }

    public static Map<String, Integer> getCarInventoryReport() {
        String query = "SELECT availability, COUNT(*) AS total_cars FROM CR_Inventory GROUP BY availability";

        Map<String, Integer> inventoryData = new LinkedHashMap<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String status = rs.getBoolean("availability") ? "Available" : "Rented";
                inventoryData.put(status, rs.getInt("total_cars"));
            }

        } catch (SQLException e) {
            System.err.println("Database error in getCarInventoryReport(): " + e.getMessage());
        }

        return inventoryData;
    }

    // Helper method to execute integer report queries
    private static Map<String, Integer> executeReportQuery(String query, String methodName) {
        Map<String, Integer> reportData = new LinkedHashMap<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                reportData.put(rs.getString(1), rs.getInt(2));
            }

        } catch (SQLException e) {
            System.err.println("Database error in " + methodName + ": " + e.getMessage());
        }

        return reportData;
    }

    // Helper method to execute revenue report queries
    private static Map<String, Double> executeRevenueReportQuery(String query, String methodName) {
        Map<String, Double> reportData = new LinkedHashMap<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                reportData.put(rs.getString(1), rs.getDouble(2));
            }

        } catch (SQLException e) {
            System.err.println("Database error in " + methodName + ": " + e.getMessage());
        }

        return reportData;
    }

}
