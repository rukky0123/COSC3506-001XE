package com.carrental.database;

import com.carrental.DatabaseConnection;
import com.carrental.models.Booking;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookingDAOTest {

    private static Booking testBooking;
    private static int insertedBookingId = -1;

    @BeforeAll
    static void setup() {
        // Use existing user_id and car_id dynamically
        List<Booking> bookings = BookingDAO.getAllBookings();
        int userId = bookings.stream().mapToInt(Booking::getUserId).findFirst().orElse(1);
        int carId = bookings.stream().mapToInt(Booking::getCarId).findFirst().orElse(1);

        testBooking = new Booking(
                0,
                userId,
                carId,
                Timestamp.valueOf(LocalDateTime.now().plusDays(1)),
                Timestamp.valueOf(LocalDateTime.now().plusDays(2)),
                "Pending",
                "", "", "", ""
        );
    }

    @Test
    @Order(1)
    void testInsertBooking() {
        boolean inserted = BookingDAO.insertBooking(testBooking);
        assertTrue(inserted, "Booking should be inserted");

        // Retrieve inserted booking to get its ID
        insertedBookingId = BookingDAO.getAllBookings().stream()
                .filter(b -> b.getUserId() == testBooking.getUserId()
                        && b.getCarId() == testBooking.getCarId()
                        && b.getStatus().equals("Pending"))
                .map(Booking::getBookingId)
                .findFirst()
                .orElse(-1);

        assertNotEquals(-1, insertedBookingId, "Inserted booking ID should be found");
    }

    @Test
    @Order(2)
    void testUpdateBooking() {
        assertNotEquals(-1, insertedBookingId, "Booking ID should be valid");

        Booking booking = BookingDAO.getAllBookings().stream()
                .filter(b -> b.getBookingId() == insertedBookingId)
                .findFirst()
                .orElse(null);

        assertNotNull(booking);

        booking.setStatus("Confirmed");
        boolean updated = BookingDAO.updateBooking(booking);
        assertTrue(updated, "Booking should be updated");

        Booking updatedBooking = BookingDAO.getAllBookings().stream()
                .filter(b -> b.getBookingId() == insertedBookingId)
                .findFirst()
                .orElse(null);

        assertNotNull(updatedBooking);
        assertEquals("Confirmed", updatedBooking.getStatus());
    }

    @Test
    @Order(3)
    void testCancelBooking() {
        boolean cancelled = BookingDAO.cancelBooking(insertedBookingId);
        assertTrue(cancelled, "Booking should be marked as cancelled");

        Booking cancelledBooking = BookingDAO.getAllBookings().stream()
                .filter(b -> b.getBookingId() == insertedBookingId)
                .findFirst()
                .orElse(null);

        assertNotNull(cancelledBooking);
        assertEquals("Cancelled", cancelledBooking.getStatus());
    }

    @AfterAll
    static void cleanup() {
        if (insertedBookingId != -1) {
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("DELETE FROM CR_Booking WHERE booking_id = ?")) {
                stmt.setInt(1, insertedBookingId);
                stmt.executeUpdate();
                System.out.println("🧹 Cleaned up test booking with ID: " + insertedBookingId);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}