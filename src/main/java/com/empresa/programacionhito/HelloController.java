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
            showAlert("Success", "Connected to the database successfully.");

            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            billingColumn.setCellValueFactory(new PropertyValueFactory<>("billing"));

            tableView.setItems(clientList);
        } catch (Exception e) {
            showAlert("Error", "Failed to connect to the database: " + e.getMessage());
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
            showAlert("Success", "Record created successfully.");
        } catch (Exception e) {
            showAlert("Error", "Failed to create record: " + e.getMessage());
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
            showAlert("Success", "Records read successfully.");
        } catch (Exception e) {
            showAlert("Error", "Failed to read records: " + e.getMessage());
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
            showAlert("Success", "Record updated successfully.");
        } catch (Exception e) {
            showAlert("Error", "Failed to update record: " + e.getMessage());
        }
    }

    @FXML
    private void handleDelete() {
        try {
            if (idField.getText().isEmpty()) {
                showAlert("Error", "ID field cannot be empty for delete operation.");
                return;
            }
            Document query = new Document("_id", idField.getText());
            deleteRecord(database, "clientes", query);
            showAlert("Success", "Record deleted successfully.");
        } catch (Exception e) {
            showAlert("Error", "Failed to delete record: " + e.getMessage());
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
            throw new RuntimeException("No matching record found to update.");
        }
    }

    public void deleteRecord(MongoDatabase database, String collectionName, Document query) {
        DeleteResult result = database.getCollection(collectionName).deleteOne(query);
        if (result.getDeletedCount() == 0) {
            throw new RuntimeException("No matching record found to delete.");
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
            showAlert("Error", "ID field cannot be empty.");
            return false;
        }
        if (nameField.getText().isEmpty()) {
            showAlert("Error", "Name field cannot be empty.");
            return false;
        }
        if (billingField.getText().isEmpty()) {
            showAlert("Error", "Billing field cannot be empty.");
            return false;
        }
        try {
            Double.parseDouble(billingField.getText());
        } catch (NumberFormatException e) {
            showAlert("Error", "Billing field must be a valid number.");
            return false;
        }
        return true;
    }
}
