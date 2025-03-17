package com.carrental.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.carrental.DatabaseConnection;

public class BookingController {
    private static Connection connection = DatabaseConnection.getConnection();

    public boolean createBooking(int userId, int carId, String startDate, String endDate) {
        String query = "INSERT INTO CR_Booking (user_id, car_id, start_date, end_date, status) VALUES (?, ?, ?, ?, 'Pending')";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, carId);
            stmt.setString(3, startDate);
            stmt.setString(4, endDate);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String> getAllBookings() {
        List<String> bookings = new ArrayList<>();
        String query = "SELECT * FROM CR_Booking";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                bookings.add("Booking ID: " + rs.getInt("booking_id") + ", User: " + rs.getInt("user_id") + ", Car: " + rs.getInt("car_id") + ", Status: " + rs.getString("status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    public boolean cancelBooking(int bookingId) {
        String query = "UPDATE CR_Booking SET status = 'Cancelled' WHERE booking_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, bookingId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}