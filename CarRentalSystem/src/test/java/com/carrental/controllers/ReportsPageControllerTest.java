/**
 * @author - Deepasree Meena Padmanabhan 
 * @studentID - 239490480
 * @version - 1.0
 */

package com.carrental.controllers;

import com.carrental.controllers.dashboards.AdminDashboard.ReportsPageController;
import com.carrental.database.CarDAO;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReportsPageControllerTest {
	private ReportsPageController controller;
	private CarDAO mockCarDAO;

	@BeforeEach
	void setUp() {
		controller = new ReportsPageController();

		controller.customerCountLabel = new Label();
		controller.staffCountLabel = new Label();
		controller.adminCountLabel = new Label();
		controller.totalRidesLabel = new Label();
		controller.totalProfitLabel = new Label();
		controller.totalTaxLabel = new Label();
		controller.carsRentedLabel = new Label();
		controller.carsInMaintenanceLabel = new Label();
		controller.availableCarsLabel = new Label();

		controller.reportChoiceBox = new ChoiceBox<>();
		controller.reportTable = new TableView<>();
		controller.column1 = new TableColumn<>();
		controller.column2 = new TableColumn<>();
		controller.chartContainer = new StackPane();

		controller.barChartRadio = new RadioButton();
		controller.lineChartRadio = new RadioButton();
		controller.pieChartRadio = new RadioButton();

		controller.revenueReportLink = new Hyperlink();
		controller.rentalsReportLink = new Hyperlink();
		controller.usersReportLink = new Hyperlink();

		mockCarDAO = mock(CarDAO.class);
		controller.setCarDAO(mockCarDAO);
	}

	@Test
	void testPopulateDashboardCounts() throws SQLException {
		when(mockCarDAO.getUserCounts()).thenReturn(Map.of("Customer", 10, "Staff", 5, "Admin", 2));
		when(mockCarDAO.getRideStats()).thenReturn(Map.of("total_rides", 100, "total_profit", 5000, "total_tax", 800));
		when(mockCarDAO.getCarStatusCounts()).thenReturn(Map.of("rented", 10, "maintenance", 2, "available", 5));

		controller.populateDashboardCounts();

		assertEquals("Customers: 10", controller.customerCountLabel.getText());
		assertEquals("Staff: 5", controller.staffCountLabel.getText());
		assertEquals("Admins: 2", controller.adminCountLabel.getText());
		assertEquals("Total Rides Booked: 100", controller.totalRidesLabel.getText());
		assertEquals("Total Profit: 5000", controller.totalProfitLabel.getText());
		assertEquals("Total Taxes: 800", controller.totalTaxLabel.getText());
		assertEquals("Cars currently rented: 10", controller.carsRentedLabel.getText());
		assertEquals("Cars in Maintenance: 2", controller.carsInMaintenanceLabel.getText());
		assertEquals("Available Cars: 5", controller.availableCarsLabel.getText());
	}

	@Test
	void testDisplayTableData() {
		Map<String, Integer> data = Map.of("Jan", 100, "Feb", 200);
		controller.displayTableData(data);
		assertEquals(2, controller.reportTable.getItems().size());
	}
}
