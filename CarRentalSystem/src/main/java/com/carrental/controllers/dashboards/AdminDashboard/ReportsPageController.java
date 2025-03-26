package com.carrental.controllers.dashboards.AdminDashboard;

import com.carrental.database.CarDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;

import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportsPageController {

    @FXML private ChoiceBox<String> reportChoiceBox;
    @FXML private RadioButton barChartRadio, lineChartRadio, pieChartRadio;
    @FXML private TableView<AbstractMap.SimpleEntry<String, ?>> reportTable;
    @FXML private TableColumn<AbstractMap.SimpleEntry<String, ?>, String> column1;
    @FXML private TableColumn<AbstractMap.SimpleEntry<String, ?>, Number> column2;
    @FXML private StackPane chartContainer;

    private CarDAO carDAO = new CarDAO();
    private ToggleGroup chartTypeToggleGroup;

    @FXML
    public void initialize() {
        chartTypeToggleGroup = new ToggleGroup();
        barChartRadio.setToggleGroup(chartTypeToggleGroup);
        lineChartRadio.setToggleGroup(chartTypeToggleGroup);
        pieChartRadio.setToggleGroup(chartTypeToggleGroup);

        barChartRadio.setSelected(true);

        reportChoiceBox.setItems(FXCollections.observableArrayList(
                "Car Usage Report", "Monthly Rentals Report", "Revenue Report",
                "Customer Booking Report", "Car Inventory Report"
        ));

        reportChoiceBox.setOnAction(event -> generateReport());
        chartTypeToggleGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> generateReport());
    }

    private void generateReport() {
        String selectedReport = reportChoiceBox.getValue();
        if (selectedReport == null) return;

        try {
            Map<String, ?> reportData = fetchReportData(selectedReport);
            displayTableData(reportData);
            displayChart(reportData, selectedReport);
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }

    private Map<String, ?> fetchReportData(String reportType) throws SQLException {
        switch (reportType) {
            case "Car Usage Report":
                return carDAO.getCarUsageReport();
            case "Monthly Rentals Report":
                return carDAO.getMonthlyRentalsReport();
            case "Revenue Report":
                return carDAO.getRevenueReport();
            case "Customer Booking Report":
                return carDAO.getCustomerBookingReport();
            case "Car Inventory Report":
                return carDAO.getCarInventoryReport();
            default:
                return null;
        }
    }

    private void displayTableData(Map<String, ?> data) {
        // Convert Map<String, ?> to ObservableList of AbstractMap.SimpleEntry
        ObservableList<AbstractMap.SimpleEntry<String, ?>> tableData = FXCollections.observableArrayList(
                data.entrySet().stream()
                        .map(entry -> new AbstractMap.SimpleEntry<>(entry.getKey(), entry.getValue()))
                        .collect(Collectors.toList())
        );

        column1.setCellValueFactory(new PropertyValueFactory<>("key"));   // ✅ Corrected Property Binding
        column2.setCellValueFactory(new PropertyValueFactory<>("value")); // ✅ Corrected Property Binding
        reportTable.setItems(tableData);
    }

    private void displayChart(Map<String, ?> data, String reportType) {
        chartContainer.getChildren().clear();

        if (barChartRadio.isSelected()) {
            displayBarChart(data, reportType);
        } else if (lineChartRadio.isSelected()) {
            displayLineChart(data, reportType);
        } else if (pieChartRadio.isSelected()) {
            displayPieChart(data);
        }
    }

    private void displayBarChart(Map<String, ?> data, String reportType) {
        BarChart<String, Number> barChart = new BarChart<>(new CategoryAxis(), new NumberAxis());
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(reportType);

        for (Map.Entry<String, ?> entry : data.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), (Number) entry.getValue()));
        }
        barChart.getData().add(series);
        chartContainer.getChildren().add(barChart);
    }

    private void displayLineChart(Map<String, ?> data, String reportType) {
        LineChart<String, Number> lineChart = new LineChart<>(new CategoryAxis(), new NumberAxis());
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(reportType);

        for (Map.Entry<String, ?> entry : data.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), (Number) entry.getValue()));
        }
        lineChart.getData().add(series);
        chartContainer.getChildren().add(lineChart);
    }

    private void displayPieChart(Map<String, ?> data) {
        PieChart pieChart = new PieChart();
        for (Map.Entry<String, ?> entry : data.entrySet()) {
            pieChart.getData().add(new PieChart.Data(entry.getKey(), ((Number) entry.getValue()).doubleValue()));
        }
        chartContainer.getChildren().add(pieChart);
    }
}
