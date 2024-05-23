module com.empresa.programacionhito {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.empresa.programacionhito to javafx.fxml;
    exports com.empresa.programacionhito;
}