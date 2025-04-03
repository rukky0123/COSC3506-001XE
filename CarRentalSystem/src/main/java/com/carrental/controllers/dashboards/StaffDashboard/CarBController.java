package com.carrental.controllers.dashboards.StaffDashboard;


import com.carrental.controllers.dashboards.StaffDashboard.components.CarBFormDialogController;
import com.carrental.database.CarDAO;
import com.carrental.models.Car;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.util.List;

public class CarBController {

    @FXML TableView<Car> carTable;
    @FXML private TableColumn<Car, String> makeCol;
    @FXML private TableColumn<Car, String> modelCol;
    @FXML private TableColumn<Car, Integer> yearCol;
    @FXML private TableColumn<Car, Double> priceCol;
    @FXML private TableColumn<Car, String> availabilityCol;
    @FXML private TableColumn<Car, Void> actionCol;

    @FXML
    public void initialize() {
        makeCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getMake()));
        modelCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getModel()));
        yearCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleObjectProperty<>(cell.getValue().getYear()));
        priceCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleObjectProperty<>(cell.getValue().getPricePerDay()));
        availabilityCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(
                cell.getValue().isAvailability() ? "Yes" : "No"
        ));

        addActionButtonsToTable();
        refreshTable();
    }

    private void refreshTable() {
        List<Car> cars = CarDAO.getAllCars();
        carTable.setItems(FXCollections.observableArrayList(cars));
    }

    private void addActionButtonsToTable() {
        actionCol.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Car, Void> call(final TableColumn<Car, Void> param) {
                return new TableCell<>() {
                    private final Button editBtn = new Button("Edit");
                    private final Button deleteBtn = new Button("Delete");

                    {
                        editBtn.getStyleClass().add("button");
                        deleteBtn.getStyleClass().add("button");

                        editBtn.setOnAction(e -> {
                            Car car = getTableView().getItems().get(getIndex());
                            showCarForm(car); // Edit mode
                        });

                        deleteBtn.setOnAction(e -> {
                            Car car = getTableView().getItems().get(getIndex());
                            boolean confirmed = confirmDelete(car.getMake() + " " + car.getModel());
                            if (confirmed) {
                                CarDAO.deleteCar(car.getCarId());
                                refreshTable();
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox box = new HBox(10, editBtn, deleteBtn);
                            setGraphic(box);
                        }
                    }
                };
            }
        });
    }

    private boolean confirmDelete(String carName) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Car");
        alert.setHeaderText("Are you sure you want to delete: " + carName + "?");
        return alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK;
    }

    @FXML
    private void handleAddCar() {
        showCarForm(null); // null = Add new
    }

    private void showCarForm(Car existingCar) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/carrental/ui/views/dashboards/StaffDashboard/components/CarBFormDialog.fxml"));
            DialogPane dialogPane = loader.load();

            CarBFormDialogController controller = loader.getController();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.initOwner(carTable.getScene().getWindow());

            controller.setCar(existingCar, car -> {
                if (existingCar == null) {
                    CarDAO.insertCar(car);
                } else {
                    CarDAO.updateCar(car);
                }
                refreshTable();
            });

            dialog.getDialogPane().getStylesheets().add(
                    getClass().getResource("/com/carrental/ui/css/dashboard.css").toExternalForm()
            );

            dialog.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public TableView<Car> getCarTable() {
        return carTable;
    }

    public void callRefreshTable() {
        refreshTable();
    }
}
