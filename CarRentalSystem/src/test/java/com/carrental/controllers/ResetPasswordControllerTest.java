package com.carrental.controllers;

import com.carrental.DatabaseConnection;
import com.carrental.SceneManager;
import com.carrental.controllers.auth.ResetPasswordController;
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
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ResetPasswordControllerTest extends ApplicationTest {
    private ResetPasswordController controller;

    @Override
    public void start(Stage stage) {}

    @BeforeEach
    void setUp() {
        controller = new ResetPasswordController();

        controller.logoImage = new ImageView();
        controller.emailField = new TextField();
        controller.otpField = new TextField();
        controller.passwordField = new PasswordField();
        controller.confirmPasswordField = new PasswordField();

        controller.emailError = new Label();
        controller.otpInfo = new Label();
        controller.otpError = new Label();
        controller.passwordError = new Label();
        controller.confirmPasswordError = new Label();
    }

    @Test
    void testInitialize() {
        Platform.runLater(() -> controller.initialize());
    }

    @Test
    void testResetAndShowClearsFields() throws Exception {
        controller.emailField.setText("a");
        controller.otpField.setText("b");
        controller.passwordField.setText("c");
        controller.confirmPasswordField.setText("d");

        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            controller.resetAndShow();
            assertEquals("", controller.emailField.getText());
            assertEquals("", controller.otpField.getText());
            assertEquals("", controller.passwordField.getText());
            assertEquals("", controller.confirmPasswordField.getText());
            latch.countDown();
        });
        latch.await();
    }

    @Test
    void testHandleSendOtpWithInvalidEmailFormat() throws Exception {
        controller.emailField.setText("invalid");
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            controller.handleSendOtp();
            assertEquals("Please enter a valid email address.", controller.emailError.getText());
            latch.countDown();
        });
        latch.await();
    }

    @Test
    void testHandleSendOtpWithNonexistentEmail() throws Exception {
        controller.emailField.setText("notfound@example.com");

        Connection mockConn = mock(Connection.class);
        PreparedStatement mockStmt = mock(PreparedStatement.class);
        ResultSet mockRs = mock(ResultSet.class);

        when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);
        when(mockStmt.executeQuery()).thenReturn(mockRs);
        when(mockRs.next()).thenReturn(false);

        try (MockedStatic<DatabaseConnection> mockedDB = mockStatic(DatabaseConnection.class)) {
            mockedDB.when(DatabaseConnection::getConnection).thenReturn(mockConn);
            CountDownLatch latch = new CountDownLatch(1);
            Platform.runLater(() -> {
                controller.handleSendOtp();
                assertEquals("Email not found in our records.", controller.emailError.getText());
                latch.countDown();
            });
            latch.await();
        }
    }

    @Test
    void testHandleSendOtpSuccess() throws Exception {
        controller.emailField.setText("valid@example.com");

        Connection mockConn = mock(Connection.class);
        PreparedStatement mockStmt = mock(PreparedStatement.class);
        ResultSet mockRs = mock(ResultSet.class);

        when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);
        when(mockStmt.executeQuery()).thenReturn(mockRs);
        when(mockRs.next()).thenReturn(true);

        ResetPasswordController spyController = spy(controller);
        doNothing().when(spyController).showOtpDialog(anyString());

        try (MockedStatic<DatabaseConnection> mockedDB = mockStatic(DatabaseConnection.class)) {
            mockedDB.when(DatabaseConnection::getConnection).thenReturn(mockConn);
            CountDownLatch latch = new CountDownLatch(1);
            Platform.runLater(() -> {
                spyController.handleSendOtp();
                latch.countDown();
            });
            latch.await();
        }
    }

    @Test
    void testHandleResetPasswordWithInvalidOtp() throws Exception {
        controller.generatedOtp = "1234";
        controller.otpField.setText("0000");

        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            controller.handleResetPassword();
            assertEquals("Invalid or expired OTP. Please try again.", controller.otpError.getText());
            latch.countDown();
        });
        latch.await();
    }

    @Test
    void testHandleResetPasswordWithWeakPassword() throws Exception {
        controller.generatedOtp = "1234";
        controller.otpField.setText("1234");
        controller.passwordField.setText("weak");
        controller.confirmPasswordField.setText("weak");

        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            controller.handleResetPassword();
            assertTrue(controller.passwordError.getText().contains("The password must be over 8 characters"));
            latch.countDown();
        });
        latch.await();
    }

    @Test
    void testHandleResetPasswordWithMismatchedPasswords() throws Exception {
        controller.generatedOtp = "1234";
        controller.otpField.setText("1234");
        controller.passwordField.setText("Password@1");
        controller.confirmPasswordField.setText("Password@2");

        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            controller.handleResetPassword();
            assertEquals("Password do not match. Please re-enter.", controller.confirmPasswordError.getText());
            latch.countDown();
        });
        latch.await();
    }

    

    @Test
    void testGoToLoginScene() {
        try (MockedStatic<SceneManager> mockedScene = mockStatic(SceneManager.class)) {
            controller.goToLogin();
            mockedScene.verify(() -> SceneManager.showScene("login"));
        }
    }

    @Test
    void testHashPasswordConsistency() {
        String password = "MySecure@Password";
        assertEquals(controller.hashPassword(password), controller.hashPassword(password));
    }
}
