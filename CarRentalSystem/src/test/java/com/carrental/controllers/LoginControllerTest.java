package com.carrental.controllers;

import com.carrental.DatabaseConnection;
import com.carrental.SceneManager;
import com.carrental.controllers.auth.LoginController;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.testfx.framework.junit5.ApplicationTest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LoginControllerTest extends ApplicationTest {
    private LoginController controller;

    @Override
    public void start(Stage stage) {
        // Required for JavaFX thread initialization
    }

    @BeforeEach
    void setUp() {
        controller = new LoginController();
        controller.usernameField = new TextField();
        controller.passwordField = new PasswordField();
        controller.userErrorLabel = new Label();
        controller.passwordErrorLabel = new Label();
        controller.logoImage = new ImageView();
        controller.userErrorLabel.setVisible(false);
        controller.passwordErrorLabel.setVisible(false);
    }

    @Test
    void testInitializeLogoImage() {
        Platform.runLater(() -> controller.initialize());
    }

    @Test
    void testHandleLoginWithInvalidCredentials() throws Exception {
        controller.usernameField.setText("wronguser");
        controller.passwordField.setText("wrongpass");

        try (MockedStatic<DatabaseConnection> mockedDB = mockStatic(DatabaseConnection.class)) {
            Connection mockConn = mock(Connection.class);
            PreparedStatement mockStmt = mock(PreparedStatement.class);
            ResultSet mockRs = mock(ResultSet.class);

            when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);
            when(mockStmt.executeQuery()).thenReturn(mockRs);
            when(mockRs.next()).thenReturn(false);
            mockedDB.when(DatabaseConnection::getConnection).thenReturn(mockConn);

            CountDownLatch latch = new CountDownLatch(1);
            Platform.runLater(() -> {
                controller.handleLogin();
                latch.countDown();
            });
            latch.await();

            assertTrue(controller.userErrorLabel.isVisible());
            assertTrue(controller.passwordErrorLabel.isVisible());
        }
    }

    

    @Test
    void testHandleLoginWithUnknownRole() throws Exception {
        controller.usernameField.setText("validuser");
        controller.passwordField.setText("validpass");

        try (
            MockedStatic<DatabaseConnection> mockedDB = mockStatic(DatabaseConnection.class)
        ) {
            Connection mockConn = mock(Connection.class);
            PreparedStatement mockStmt = mock(PreparedStatement.class);
            ResultSet mockRs = mock(ResultSet.class);

            when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);
            when(mockStmt.executeQuery()).thenReturn(mockRs);
            when(mockRs.next()).thenReturn(true);
            when(mockRs.getString("password")).thenReturn(controller.hashPassword("validpass"));
            when(mockRs.getInt("user_id")).thenReturn(2);
            when(mockRs.getString("role")).thenReturn("Alien");
            when(mockRs.getString("email")).thenReturn("test@example.com");
            when(mockRs.getTimestamp("created_at")).thenReturn(new Timestamp(System.currentTimeMillis()));

            mockedDB.when(DatabaseConnection::getConnection).thenReturn(mockConn);

            CountDownLatch latch = new CountDownLatch(1);
            Platform.runLater(() -> {
                controller.handleLogin();
                latch.countDown();
            });
            latch.await();

            assertTrue(controller.userErrorLabel.isVisible());
        }
    }

    @Test
    void testGoToResetPasswordNavigation() {
        try (MockedStatic<SceneManager> mockedScene = mockStatic(SceneManager.class)) {
            controller.goToResetPassword();
            mockedScene.verify(() -> SceneManager.showScene("resetpassword"));
        }
    }

    @Test
    void testGoToSignupNavigation() {
        try (MockedStatic<SceneManager> mockedScene = mockStatic(SceneManager.class)) {
            controller.goToSignup();
            mockedScene.verify(() -> SceneManager.showScene("signup"));
        }
    }

    @Test
    void testGoToPolicyNavigation() {
        try (MockedStatic<SceneManager> mockedScene = mockStatic(SceneManager.class)) {
            controller.goToPolicy();
            mockedScene.verify(() -> SceneManager.showScene("policy"));
        }
    }

    @Test
    void testHashPasswordConsistency() {
        String password = "MySecurePassword@123";
        String hashed = controller.hashPassword(password);
        assertEquals(hashed, controller.hashPassword(password));
    }
}
