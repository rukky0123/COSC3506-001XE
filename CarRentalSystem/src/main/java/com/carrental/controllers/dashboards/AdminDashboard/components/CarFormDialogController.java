package com.carrental.controllers.dashboards.AdminDashboard.components;

import com.carrental.models.Car;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.function.Consumer;

public class CarFormDialogController {

    @FXML private Label formTitle;
    @FXML private TextField makeField;
    @FXML private TextField modelField;
    @FXML private TextField yearField;
    @FXML private TextField priceField;
    @FXML private ComboBox<String> availabilityBox;
    @FXML private Label errorLabel;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;

    private Car car; // Null = New Car
    private Consumer<Car> onSaveCallback;

    @FXML
    public void initialize() {
        availabilityBox.getItems().addAll("Yes", "No");

        cancelButton.setOnAction(e -> closeDialog());
        saveButton.setOnAction(e -> saveCar());
    }

    public void setCar(Car car, Consumer<Car> onSaveCallback) {
        this.car = car;
        this.onSaveCallback = onSaveCallback;

        if (car != null) {
            formTitle.setText("Edit Car");
            makeField.setText(car.getMake());
            modelField.setText(car.getModel());
            yearField.setText(String.valueOf(car.getYear()));
            priceField.setText(String.valueOf(car.getPricePerDay()));
            availabilityBox.setValue(car.isAvailability() ? "Yes" : "No");
        } else {
            formTitle.setText("Add Car");
        }
    }

    private void saveCar() {
        String make = makeField.getText().trim();
        String model = modelField.getText().trim();
        String yearStr = yearField.getText().trim();
        String priceStr = priceField.getText().trim();
        String availabilityStr = availabilityBox.getValue();

        if (make.isEmpty() || model.isEmpty() || yearStr.isEmpty() || priceStr.isEmpty() || availabilityStr == null) {
            errorLabel.setText("All fields are required.");
            return;
        }

        try {
            int year = Integer.parseInt(yearStr);
            double price = Double.parseDouble(priceStr);
            boolean available = availabilityStr.equalsIgnoreCase("Yes");

            if (car == null) {
                car = new Car(0, make, model, year, price, available);
            } else {
                car.setMake(make);
                car.setModel(model);
                car.setYear(year);
                car.setPricePerDay(price);
                car.setAvailability(available);
            }

            errorLabel.setText("");
            onSaveCallback.accept(car);
            closeDialog();

        } catch (NumberFormatException e) {
            errorLabel.setText("Year and Price must be valid numbers.");
        }
    }

    private void closeDialog() {
        ((Stage) saveButton.getScene().getWindow()).close();
    }
}
