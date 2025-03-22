package com.carrental.controllers.dashboards.AdminDashboard.components;

import com.carrental.models.Booking;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.function.Consumer;

public class BookingFormDialogController {

    @FXML private Label formTitle;
    @FXML private Label usernameLabel;
    @FXML private Label carNameLabel;
    @FXML private DatePicker startDatePicker;
    @FXML private DatePicker endDatePicker;
    @FXML private ComboBox<String> statusBox;
    @FXML private Label errorLabel;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    private Booking booking;

    private Consumer<Booking> onSaveCallback;

    @FXML
    public void initialize() {
        statusBox.getItems().addAll("Pending", "Confirmed", "Cancelled");

        cancelButton.setOnAction(e -> closeDialog());
        saveButton.setOnAction(e -> saveBooking());
    }

    public void setBooking(Booking booking, Consumer<Booking> onSaveCallback) {
        this.booking = booking;
        this.onSaveCallback = onSaveCallback;

        if (booking != null) {
            formTitle.setText("Edit Booking");
            startDatePicker.setValue(booking.getStartDate().toLocalDateTime().toLocalDate());
            endDatePicker.setValue(booking.getEndDate().toLocalDateTime().toLocalDate());
            statusBox.setValue(booking.getStatus());
            usernameLabel.setText(booking.getUsername());
            carNameLabel.setText(String.format("%s %s (%s)", booking.getCarMake(), booking.getCarModel(), booking.getCarYear()));
        } else {
            formTitle.setText("Add Booking");
        }
    }

    public void setOnSaveCallback(Consumer<Booking> onSaveCallback) {
        this.onSaveCallback = onSaveCallback;
    }

    private void saveBooking() {
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        String status = statusBox.getValue();

        if (startDate == null || endDate == null || status == null) {
            errorLabel.setText("Start date, end date, and status are required.");
            return;
        }

        // ✅ No need to retrieve IDs from form fields anymore
        int userId = booking.getUserId();
        int carId = booking.getCarId();

        Timestamp startTimestamp = Timestamp.valueOf(startDate.atStartOfDay());
        Timestamp endTimestamp = Timestamp.valueOf(endDate.atStartOfDay());

        Booking updatedBooking = new Booking(
                booking.getBookingId(),
                userId,
                carId,
                startTimestamp,
                endTimestamp,
                status,
                booking.getUsername(),
                booking.getCarMake(),
                booking.getCarModel(),
                booking.getCarYear()
        );

        errorLabel.setText("");
        onSaveCallback.accept(updatedBooking);
        closeDialog();
    }

    private void closeDialog() {
        ((Stage) saveButton.getScene().getWindow()).close();
    }
}
