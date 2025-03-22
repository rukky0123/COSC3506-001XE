package com.carrental.controllers.dashboards.AdminDashboard;

import com.carrental.controllers.dashboards.AdminDashboard.components.BookingFormDialogController;
import com.carrental.database.BookingDAO;
import com.carrental.models.Booking;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class BookingsPageController {

    @FXML private TableView<Booking> bookingTable;
    @FXML private TableColumn<Booking, Integer> bookingIdCol;
    @FXML private TableColumn<Booking, String> usernameCol;
    @FXML private TableColumn<Booking, String> carNameCol;
    @FXML private TableColumn<Booking, String> startDateCol;
    @FXML private TableColumn<Booking, String> endDateCol;
    @FXML private TableColumn<Booking, String> statusCol;
    @FXML private TableColumn<Booking, Void> actionCol;


    @FXML
    public void initialize() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy 'at' hh:mm a");
        bookingIdCol.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getBookingId()));
        usernameCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getUsername()));
        carNameCol.setCellValueFactory(cell -> {
            Booking b = cell.getValue();
            String fullCarName = String.format("%s %s (%s)", b.getCarMake(), b.getCarModel(), b.getCarYear());
            return new SimpleStringProperty(fullCarName);
        });
        startDateCol.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getStartDate().toLocalDateTime().format(dateTimeFormatter)));

        endDateCol.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getEndDate().toLocalDateTime().format(dateTimeFormatter)));

        statusCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getStatus()));

        addButtonToTable();

        List<Booking> bookings = BookingDAO.getAllBookings();
        bookingTable.setItems(FXCollections.observableArrayList(bookings));
    }

    private void addButtonToTable() {
        actionCol.setCellFactory(param -> new TableCell<>() {
            private final Button editBtn = new Button("Edit");

            {
                editBtn.setOnAction(e -> {
                    Booking booking = getTableView().getItems().get(getIndex());
                    showBookingForm(booking); // Always editable
                });

                editBtn.getStyleClass().add("button");
            }

            @Override
            public void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(new HBox(editBtn));
                }
            }
        });
    }

    private void showBookingForm(Booking existingBooking) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/carrental/ui/views/dashboards/AdminDashboard/components/BookingFormDialog.fxml"));
            DialogPane dialogPane = loader.load();

            BookingFormDialogController controller = loader.getController();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.getDialogPane().getStylesheets().add(
                    getClass().getResource("/com/carrental/ui/css/dashboard.css").toExternalForm()
            );
            dialog.initOwner(bookingTable.getScene().getWindow());


            controller.setBooking(existingBooking, booking -> {
                if (existingBooking == null) {
                    BookingDAO.insertBooking(booking);
                } else {
                    booking.setBookingId(existingBooking.getBookingId()); // set ID for update
                    BookingDAO.updateBooking(booking);
                }
                refreshTable();
            });

            dialog.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refreshTable() {
        List<Booking> bookings = BookingDAO.getAllBookings();
        bookingTable.setItems(FXCollections.observableArrayList(bookings));
    }

}
