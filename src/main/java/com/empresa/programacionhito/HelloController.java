package com.empresa.programacionhito;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class HelloController {
    private MongoDatabase database;

    @FXML
    private Button createButton;
    @FXML
    private Button readButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;
    @FXML
    private TextField idField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField billingField;

    @FXML
    private TableView<Client> tableView;
    @FXML
    private TableColumn<Client, String> idColumn;
    @FXML
    private TableColumn<Client, String> nameColumn;
    @FXML
    private TableColumn<Client, String> billingColumn;

    private ObservableList<Client> clientList = FXCollections.observableArrayList();

    public void initialize() {
        try {
            this.database = MongoDBConnection.connect();
            showAlert("Éxito", "Has conectado a la base de datos de manera correcta.");

            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            billingColumn.setCellValueFactory(new PropertyValueFactory<>("billing"));

            tableView.setItems(clientList);
        } catch (Exception e) {
            showAlert("Error", "Conexión a la base de datos fallida: " + e.getMessage());
        }
    }

    @FXML
    private void handleCreate() {
        try {
            if (!validateInput()) {
                return;
            }
            Document document = new Document("_id", idField.getText())
                    .append("nombre", nameField.getText())
                    .append("facturacion", Double.parseDouble(billingField.getText()));
            createRecord(database, "clientes", document);
            showAlert("Exito", "Registro creado satisfactoriamente.");
        } catch (Exception e) {
            showAlert("Error", "Creación de registro fallido: " + e.getMessage());
        }
    }

    @FXML
    private void handleRead() {
        try {
            clientList.clear();
            for (Document doc : readRecords(database, "clientes")) {
                String id = doc.getString("_id");
                String name = doc.getString("nombre");
                String billing = String.valueOf(doc.getDouble("facturacion"));
                Client client = new Client(id, name, billing);
                clientList.add(client);
            }
            showAlert("Éxito", "El registro se ha leido satisfactoriamente.");
        } catch (Exception e) {
            showAlert("Error", "Lectura de registro fallido: " + e.getMessage());
        }
    }

    @FXML
    private void handleUpdate() {
        try {
            if (!validateInput()) {
                return;
            }
            Document query = new Document("_id", idField.getText());
            Document update = new Document("nombre", nameField.getText())
                    .append("facturacion", Double.parseDouble(billingField.getText()));
            updateRecord(database, "clientes", query, update);
            showAlert("Éxito", "Registro actualizado satisfactoriamente.");
        } catch (Exception e) {
            showAlert("Error", "Actualización de registro fallido: " + e.getMessage());
        }
    }

    @FXML
    private void handleDelete() {
        try {
            if (idField.getText().isEmpty()) {
                showAlert("Error", "El campo ID no puede estar vacio.");
                return;
            }
            Document query = new Document("_id", idField.getText());
            deleteRecord(database, "clientes", query);
            showAlert("Éxito", "Registros eliminados correctamente.");
        } catch (Exception e) {
            showAlert("Error", "Fallo al eliminar los registros: " + e.getMessage());
        }
    }

    public void createRecord(MongoDatabase database, String collectionName, Document document) {
        database.getCollection(collectionName).insertOne(document);
    }

    public Iterable<Document> readRecords(MongoDatabase database, String collectionName) {
        return database.getCollection(collectionName).find();
    }

    public void updateRecord(MongoDatabase database, String collectionName, Document query, Document update) {
        UpdateResult result = database.getCollection(collectionName).updateOne(query, new Document("$set", update));
        if (result.getMatchedCount() == 0) {
            throw new RuntimeException("No se han encontrado registros para actualizar.");
        }
    }

    public void deleteRecord(MongoDatabase database, String collectionName, Document query) {
        DeleteResult result = database.getCollection(collectionName).deleteOne(query);
        if (result.getDeletedCount() == 0) {
            throw new RuntimeException("No se han encontrado registros para eliminar.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean validateInput() {
        if (idField.getText().isEmpty()) {
            showAlert("Error", "El campo ID no puede estar vacio.");
            return false;
        }
        if (nameField.getText().isEmpty()) {
            showAlert("Error", "El campo nombre no puede estar vacio.");
            return false;
        }
        if (billingField.getText().isEmpty()) {
            showAlert("Error", "El campo facturación no puede estar vacio.");
            return false;
        }
        try {
            Double.parseDouble(billingField.getText());
        } catch (NumberFormatException e) {
            showAlert("Error", "El campo facturación debe ser un número valido.");
            return false;
        }
        return true;
    }
}
