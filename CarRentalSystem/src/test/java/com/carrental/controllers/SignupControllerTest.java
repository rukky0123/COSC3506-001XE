/**
 * @author - Deepasree Meena Padmanabhan 
 * @studentID - 239490480
 * @version - 1.0
 */

package com.carrental.controllers;

import com.carrental.SceneManager;
import com.carrental.controllers.auth.SignupController;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SignupControllerTest extends ApplicationTest {
	private SignupController controller;

	@Override
	public void start(Stage stage) {
	}

	@BeforeEach
	void setUp() {
		controller = new SignupController();

		controller.usernameField = new TextField("testuser");
		controller.firstNameField = new TextField("First");
		controller.lastNameField = new TextField("Last");
		controller.emailField = new TextField("test@example.com");
		controller.confirmEmailField = new TextField("test@example.com");
		controller.passwordField = new PasswordField();
		controller.passwordField.setText("Password@123");
		controller.confirmPasswordField = new PasswordField();
		controller.confirmPasswordField.setText("Password@123");
		controller.address1Field = new TextField("123 Street");
		controller.address2Field = new TextField("Apt 1");
		controller.cityField = new TextField("City");
		controller.provinceField = new TextField("State");
		controller.postalCodeField = new TextField("12345");
		controller.countryField = new TextField("Country");
		controller.cardNameField = new TextField("Card Holder");
		controller.cardNumberField = new TextField("4111111111111111");
		controller.expMonthField = new TextField("12");
		controller.expYearField = new TextField("2030");
		controller.securityCodeField = new TextField("123");
		controller.cardPostalField = new TextField("54321");
		controller.cardCountryField = new TextField("Country");
		controller.logoImage = new ImageView();

		controller.errorUsername = new Label();
		controller.errorFirstName = new Label();
		controller.errorLastName = new Label();
		controller.errorEmail = new Label();
		controller.errorConfirmEmail = new Label();
		controller.errorPassword = new Label();
		controller.errorConfirmPassword = new Label();
		controller.errorAddress1 = new Label();
		controller.errorCity = new Label();
		controller.errorProvince = new Label();
		controller.errorZip = new Label();
		controller.errorCountry = new Label();
		controller.errorCardName = new Label();
		controller.errorCardNumber = new Label();
		controller.errorExp = new Label();
		controller.errorCvv = new Label();
		controller.errorCardZip = new Label();
		controller.errorCardCountry = new Label();
	}

	@Test
	void testInitializeLogo() {
		controller.initialize();
	}

	@Test
	void testResetAndShowClearsFields() throws Exception {
		CountDownLatch latch = new CountDownLatch(1);
		Platform.runLater(() -> {
			controller.resetAndShow();
			assertEquals("", controller.usernameField.getText());
			assertEquals("", controller.emailField.getText());
			latch.countDown();
		});
		latch.await();
	}

	@Test
	void testHandleSignupFailsWithInvalidEmailFormat() throws Exception {
		controller.emailField.setText("invalid-email");
		controller.confirmEmailField.setText("invalid-email");

		CountDownLatch latch = new CountDownLatch(1);
		Platform.runLater(() -> {
			controller.handleSignup();
			assertEquals("Enter a valid email address.", controller.errorEmail.getText());
			latch.countDown();
		});
		latch.await();
	}

	@Test
	void testHandleSignupFailsWithMismatchedEmails() throws Exception {
		controller.confirmEmailField.setText("mismatch@example.com");

		CountDownLatch latch = new CountDownLatch(1);
		Platform.runLater(() -> {
			controller.handleSignup();
			assertEquals("Email addresses do not match.", controller.errorConfirmEmail.getText());
			latch.countDown();
		});
		latch.await();
	}

	@Test
	void testHandleSignupFailsWithMismatchedPasswords() throws Exception {
		controller.confirmPasswordField.setText("Different@123");

		CountDownLatch latch = new CountDownLatch(1);
		Platform.runLater(() -> {
			controller.handleSignup();
			assertEquals("Passwords do not match.", controller.errorConfirmPassword.getText());
			latch.countDown();
		});
		latch.await();
	}

	@Test
	void testGoToLoginNavigation() {
		try (MockedStatic<SceneManager> mockedScene = mockStatic(SceneManager.class)) {
			controller.goToLogin();
			mockedScene.verify(() -> SceneManager.showScene("login"));
		}
	}

	@Test
	void testHashPasswordIsConsistent() {
		String password = "Secure@123";
		assertEquals(controller.hashPassword(password), controller.hashPassword(password));
	}
}
