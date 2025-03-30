package com.carrental.controllers;

import com.carrental.SceneManager;
import com.carrental.controllers.auth.PolicyController;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.mockito.MockedStatic;

import static org.mockito.Mockito.*;

public class PolicyControllerTest extends ApplicationTest {

	private PolicyController controller;

	@Override
	public void start(Stage stage) {
		// Needed to initialize JavaFX thread
	}

	@BeforeEach
	void setUp() {
		controller = new PolicyController();
		controller.logoImage = new ImageView();
	}

	@Test
	void testInitializeLogoImageLoads() {
		// Just run it to verify it doesn't throw
		controller.initialize();
	}

	@Test
	void testGoToLoginNavigatesToLoginScene() {
		try (MockedStatic<SceneManager> mockedSceneManager = mockStatic(SceneManager.class)) {
			controller.goToLogin();
			mockedSceneManager.verify(() -> SceneManager.showScene("login"));
		}
	}
}
