package com.carrental.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.carrental.DatabaseConnection;

public class UserController {
    private static Connection connection = DatabaseConnection.getConnection();

    public boolean createUser(String username, String password, String role, String email) {
        String query = "INSERT INTO CR_User (username, password, role, email) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, role);
            stmt.setString(4, email);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String> getAllUsers() {
        List<String> users = new ArrayList<>();
        String query = "SELECT * FROM CR_User";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                users.add(rs.getString("username") + " - " + rs.getString("role"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public boolean deleteUser(int userId) {
        String query = "DELETE FROM CR_User WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}