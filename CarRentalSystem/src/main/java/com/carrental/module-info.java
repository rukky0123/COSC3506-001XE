module com.carrental {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.carrental to javafx.fxml;
    opens com.carrental.controllers to javafx.fxml;
    
    exports com.carrental;
    exports com.carrental.controllers;
}
