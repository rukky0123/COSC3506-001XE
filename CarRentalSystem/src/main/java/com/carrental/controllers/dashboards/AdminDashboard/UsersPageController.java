package com.carrental.controllers.dashboards.AdminDashboard;

import com.carrental.database.UserDAO;
import com.carrental.models.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javafx.scene.layout.HBox;
import javafx.collections.FXCollections;

import java.util.List;

public class UsersPageController {

    @FXML private TableView<User> userTable;
    @FXML private TableColumn<User, String> nameCol;
    @FXML private TableColumn<User, String> emailCol;
    @FXML private TableColumn<User, String> addressCol; // Using 'role'
    @FXML private TableColumn<User, String> phoneCol;   // Using 'createdAt'
    @FXML private TableColumn<User, Void> actionCol;

    @FXML
    public void initialize() {
        // Set value factories from User model
        nameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("role"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

        addButtonToTable();

        // Fetch and bind user data
        List<User> users = UserDAO.getAllUsers();
        userTable.setItems(FXCollections.observableArrayList(users));
    }

    private void addButtonToTable() {
        actionCol.setCellFactory(new Callback<>() {
            @Override
            public TableCell<User, Void> call(final TableColumn<User, Void> param) {
                return new TableCell<>() {
                    private final Button btn = new Button("Change User Type");

                    {
                        btn.setOnAction(e -> {
                            User user = getTableView().getItems().get(getIndex());
                            System.out.println("Change user type: " + user.getUsername());
                        });
                        btn.getStyleClass().add("button");
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        setGraphic(empty ? null : new HBox(btn));
                    }
                };
            }
        });
    }
}
