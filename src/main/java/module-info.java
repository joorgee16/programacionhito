module com.empresa.programacionhito {
    requires javafx.controls;
    requires javafx.fxml;
    requires mongo.java.driver;

    opens com.empresa.programacionhito to javafx.fxml;
    exports com.empresa.programacionhito;
}