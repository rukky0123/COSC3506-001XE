package com.carrental.controllers.dashboards.StaffDashboard.components;


import com.carrental.models.Car;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import java.util.function.Consumer;

public class CarBFormDialogController {

    @FXML private Label formTitle;
    @FXML private TextField makeField;
    @FXML private TextField modelField;
    @FXML private TextField yearField;
    @FXML private TextField priceField;
    @FXML private ComboBox<String> availabilityBox;
    @FXML private Label errorLabel;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    @FXML private ImageView carImageView;
    @FXML private Button uploadImageButton;

    private String imageFilename = null;
    private final String imageSaveDir = "images";


    private Car car; // Null = New Car
    private Consumer<Car> onSaveCallback;

    @FXML
    public void initialize() {
        availabilityBox.getItems().addAll("Yes", "No");

        cancelButton.setOnAction(e -> closeDialog());
        saveButton.setOnAction(e -> saveCar());
        uploadImageButton.setOnAction(e -> chooseImage());
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
            if (car.getImagePath() != null && !car.getImagePath().isEmpty()) {
                File imgFile = new File(imageSaveDir, car.getImagePath());
                if (imgFile.exists()) {
                    carImageView.setImage(new Image(imgFile.toURI().toString()));
                    imageFilename = car.getImagePath(); // retain original path
                }
            }
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
                car = new Car(0, make, model, year, price, available, imageFilename);
            } else {
                car.setMake(make);
                car.setModel(model);
                car.setYear(year);
                car.setPricePerDay(price);
                car.setAvailability(available);
                car.setImagePath(imageFilename);
            }

            errorLabel.setText("");
            onSaveCallback.accept(car);
            closeDialog();

        } catch (NumberFormatException e) {
            errorLabel.setText("Year and Price must be valid numbers.");
        }
    }

    private void chooseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Car Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        File selectedFile = fileChooser.showOpenDialog(uploadImageButton.getScene().getWindow());

        if (selectedFile != null) {
            try {
                // Create image folder if it doesn’t exist
                File dir = new File(imageSaveDir);
                if (!dir.exists()) dir.mkdirs();

                // Save image to /images directory
                String uniqueName = System.currentTimeMillis() + "_" + selectedFile.getName();
                File dest = new File(dir, uniqueName);
                Files.copy(selectedFile.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);

                imageFilename = uniqueName;

                // Preview in image view
                carImageView.setImage(new Image(dest.toURI().toString()));

            } catch (IOException e) {
                e.printStackTrace();
                errorLabel.setText("Failed to upload image.");
            }
        }
    }


    private void closeDialog() {
        ((Stage) saveButton.getScene().getWindow()).close();
    }
}
