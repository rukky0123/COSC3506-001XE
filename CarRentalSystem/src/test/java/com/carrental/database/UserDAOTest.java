package com.carrental.database;

import com.carrental.models.User;
import org.junit.jupiter.api.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {

    private User testUser;

    @BeforeEach
    void setup() {
        // Unique username per test run
        String uniqueUsername = "test_user_" + System.currentTimeMillis();

        testUser = new User(
                0,
                uniqueUsername,
                "test_password_hash", // Simulate hashed password
                "customer",
                uniqueUsername + "@example.com",
                Timestamp.from(Instant.now())
        );

        boolean inserted = UserDAO.insertUser(testUser);
        assertTrue(inserted, "User should be inserted");

        // Get generated ID from DB
        testUser = UserDAO.getAllUsers().stream()
                .filter(u -> u.getUsername().equals(testUser.getUsername()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Inserted test user not found"));
    }

    @Test
    void testInsertUser() {
        assertNotEquals(0, testUser.getUserId(), "Inserted user should have a valid ID");
    }

    @Test
    void testUpdateUser() {
        String updatedEmail = "updated_" + testUser.getEmail();
        testUser.setEmail(updatedEmail);

        boolean updated = UserDAO.updateUser(testUser);
        assertTrue(updated, "User should be updated");

        User updatedUser = UserDAO.getAllUsers().stream()
                .filter(u -> u.getUserId() == testUser.getUserId())
                .findFirst()
                .orElse(null);

        assertNotNull(updatedUser);
        assertEquals(updatedEmail, updatedUser.getEmail(), "Email should be updated");
    }

    @Test
    void testGetAllUsers() {
        List<User> users = UserDAO.getAllUsers();
        assertNotNull(users, "User list should not be null");
        assertTrue(users.size() > 0, "There should be at least one user");
    }

    @Test
    void testDeleteUser() {
        boolean deleted = UserDAO.deleteUser(testUser.getUserId());
        assertTrue(deleted, "User should be deleted");

        boolean stillExists = UserDAO.getAllUsers().stream()
                .anyMatch(u -> u.getUserId() == testUser.getUserId());

        assertFalse(stillExists, "Deleted user should not exist anymore");
    }

    @AfterEach
    void cleanup() {
        // Ensure no leftover user entry
        UserDAO.deleteUser(testUser.getUserId());
    }
}