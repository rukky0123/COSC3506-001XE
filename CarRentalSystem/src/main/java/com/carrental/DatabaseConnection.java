package com.carrental;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://34.130.26.167:3306/carrental";
    private static final String USER = "app_user";
    private static final String PASSWORD = "algoma123";
    private static Connection connection;

    private DatabaseConnection() {}

    public static Connection getConnection() {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                return DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                return null;
            }
    }
}
