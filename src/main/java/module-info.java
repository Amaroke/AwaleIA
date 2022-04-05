module awale.awaleia {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens awale.awaleia to javafx.fxml;
    exports awale.awaleia;
}