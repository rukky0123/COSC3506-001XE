package com.carrental.database;

import com.carrental.DatabaseConnection;
import com.carrental.models.Booking;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    public static List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();

        String query = """
        SELECT b.booking_id, b.user_id, b.car_id, b.start_date, b.end_date, b.status,
               u.username AS username, c.make AS car_make, c.model AS car_model, c.year AS car_year
        FROM CR_Booking b
        JOIN CR_User u ON b.user_id = u.user_id
        JOIN CR_Inventory c ON b.car_id = c.car_id
        ORDER BY b.booking_id DESC
    """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                bookings.add(new Booking(
                        rs.getInt("booking_id"),
                        rs.getInt("user_id"),
                        rs.getInt("car_id"),
                        rs.getTimestamp("start_date"),
                        rs.getTimestamp("end_date"),
                        rs.getString("status"),
                        rs.getString("username"),
                        rs.getString("car_make"),
                        rs.getString("car_model"),
                        rs.getString("car_year")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookings;
    }

    public static boolean cancelBooking(int bookingId) {
        String query = "UPDATE CR_Booking SET status = 'Cancelled' WHERE booking_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, bookingId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean insertBooking(Booking booking) {
        String sql = "INSERT INTO CR_Booking (user_id, car_id, start_date, end_date, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, booking.getUserId());
            stmt.setInt(2, booking.getCarId());
            stmt.setTimestamp(3, booking.getStartDate());
            stmt.setTimestamp(4, booking.getEndDate());
            stmt.setString(5, booking.getStatus());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateBooking(Booking booking) {
        String sql = "UPDATE CR_Booking SET user_id=?, car_id=?, start_date=?, end_date=?, status=? WHERE booking_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, booking.getUserId());
            stmt.setInt(2, booking.getCarId());
            stmt.setTimestamp(3, booking.getStartDate());
            stmt.setTimestamp(4, booking.getEndDate());
            stmt.setString(5, booking.getStatus());
            stmt.setInt(6, booking.getBookingId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


}
