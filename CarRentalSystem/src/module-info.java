module com.carrental {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.carrental to javafx.fxml;
    opens com.carrental.controllers to javafx.fxml;
}
