package com.carrental.database;

import com.carrental.DatabaseConnection;
import com.carrental.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public static List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        String query = "SELECT * FROM CR_User";  // Adjust this query if your table has a different name

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                users.add(new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role"),
                        rs.getString("email"),
                        rs.getTimestamp("created_at")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }
}
