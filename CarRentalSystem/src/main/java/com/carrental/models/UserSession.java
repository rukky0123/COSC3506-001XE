package com.carrental.models;

import com.carrental.models.User;

public class UserSession {
    // A private static instance of the User object
    private static User loggedInUser = null;

    // Private constructor to prevent instantiation
    private UserSession() {}

    // Method to set the logged-in user
    public static void setLoggedInUser(User user) {
        loggedInUser = user;
    }

    // Method to get the logged-in user
    public static User getLoggedInUser() {
        return loggedInUser;
    }

    // Method to check if a user is logged in
    public static boolean isLoggedIn() {
        return loggedInUser != null;
    }

    // Method to log out the user (reset the logged-in user)
    public static void logout() {
        loggedInUser = null;
    }
}
