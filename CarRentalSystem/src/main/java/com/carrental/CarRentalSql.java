package com.carrental;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CarRentalSql {
    private static Connection connection = DatabaseConnection.getConnection();

    public static void initializeDatabase() {
        String[] queries = {
            "CREATE DATABASE IF NOT EXISTS carrental;",
            "USE carrental;",
            "CREATE TABLE IF NOT EXISTS CR_User (" +
            "user_id INT AUTO_INCREMENT PRIMARY KEY," +
            "username VARCHAR(50) NOT NULL UNIQUE," +
            "password VARCHAR(255) NOT NULL," +
            "role ENUM('Admin', 'Staff', 'Customer') NOT NULL," +
            "email VARCHAR(100) NOT NULL UNIQUE," +
            "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP);",

            "CREATE TABLE IF NOT EXISTS CR_Inventory (" +
            "car_id INT AUTO_INCREMENT PRIMARY KEY," +
            "make VARCHAR(50) NOT NULL," +
            "model VARCHAR(50) NOT NULL," +
            "year INT NOT NULL," +
            "price_per_day DECIMAL(10,2) NOT NULL," +
            "availability BOOLEAN DEFAULT TRUE);",

            "CREATE TABLE IF NOT EXISTS CR_Booking (" +
            "booking_id INT AUTO_INCREMENT PRIMARY KEY," +
            "user_id INT," +
            "car_id INT," +
            "start_date DATE NOT NULL," +
            "end_date DATE NOT NULL," +
            "status ENUM('Pending', 'Confirmed', 'Cancelled', 'Completed') DEFAULT 'Pending'," +
            "FOREIGN KEY (user_id) REFERENCES CR_User(user_id) ON DELETE CASCADE," +
            "FOREIGN KEY (car_id) REFERENCES CR_Inventory(car_id) ON DELETE CASCADE);",

            "CREATE TABLE IF NOT EXISTS CR_Payment (" +
            "payment_id INT AUTO_INCREMENT PRIMARY KEY," +
            "booking_id INT," +
            "amount DECIMAL(10,2) NOT NULL," +
            "payment_status ENUM('Paid', 'Pending', 'Failed') DEFAULT 'Pending'," +
            "payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
            "FOREIGN KEY (booking_id) REFERENCES CR_Booking(booking_id) ON DELETE CASCADE);",

            "CREATE TABLE IF NOT EXISTS CR_Maintenance (" +
            "maintenance_id INT AUTO_INCREMENT PRIMARY KEY," +
            "car_id INT," +
            "maintenance_date DATE NOT NULL," +
            "details TEXT NOT NULL," +
            "FOREIGN KEY (car_id) REFERENCES CR_Inventory(car_id) ON DELETE CASCADE);"
        };

        try {
            for (String query : queries) {
                try (PreparedStatement stmt = connection.prepareStatement(query)) {
                    stmt.execute();
                }
            }
            System.out.println("Database initialized successfully.");
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }
}
