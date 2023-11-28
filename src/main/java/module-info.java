module com.reine.dragcanvas {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.reine.dragcanvas to javafx.fxml;
    opens com.reine.dragcanvas.controller to javafx.fxml;
    exports com.reine.dragcanvas;
    exports com.reine.dragcanvas.controller to javafx.fxml;
}