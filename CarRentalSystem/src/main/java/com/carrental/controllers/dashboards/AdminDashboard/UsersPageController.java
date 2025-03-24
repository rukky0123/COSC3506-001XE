package com.carrental.controllers.dashboards.AdminDashboard;

import com.carrental.controllers.dashboards.AdminDashboard.components.UserFormDialogController;
import com.carrental.database.UserDAO;
import com.carrental.models.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javafx.scene.layout.HBox;
import javafx.collections.FXCollections;

import java.util.List;

public class UsersPageController {

    @FXML TableView<User> userTable;
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
                    private final Button editBtn = new Button("Edit");
                    private final Button deleteBtn = new Button("Delete");

                    {
                        editBtn.setOnAction(e -> {
                            User user = getTableView().getItems().get(getIndex());
                            showUserForm(user); // open in edit mode
                        });

                        deleteBtn.setOnAction(e -> {
                            User user = getTableView().getItems().get(getIndex());
                            boolean confirmed = confirmDelete(user.getUsername());
                            if (confirmed) {
                                UserDAO.deleteUser(user.getUserId());
                                refreshTable();
                            }
                        });

                        editBtn.getStyleClass().add("button");
                        deleteBtn.getStyleClass().add("button");
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

    private boolean confirmDelete(String username) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Confirmation");
        alert.setHeaderText("Are you sure you want to delete user: " + username + "?");
        return alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK;
    }


    private void refreshTable() {
        List<User> users = UserDAO.getAllUsers();
        userTable.setItems(FXCollections.observableArrayList(users));
    }

    @FXML
    private void handleAddUser() {
        showUserForm(null); // null = new user
    }

    private void showUserForm(User existingUser) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/carrental/ui/views/dashboards/AdminDashboard/components/UserFormDialog.fxml"));
            DialogPane dialogPane = loader.load();

            UserFormDialogController controller = loader.getController();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.getDialogPane().getStylesheets().add(
                    getClass().getResource("/com/carrental/ui/css/dashboard.css").toExternalForm()
            );
            dialog.initOwner(userTable.getScene().getWindow());

            controller.setUser(existingUser, user -> {
                if (existingUser == null) {
                    UserDAO.insertUser(user);
                } else {
                    UserDAO.updateUser(user);
                }
                refreshTable();
            });

            dialog.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public TableView<User> getUserTable() {
        return userTable;
    }
}
