package cse213.papermillapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class RecordDeliveryController
{
    @javafx.fxml.FXML
    private TextField invoiceIdField;
    @javafx.fxml.FXML
    private TextField supplierNameField;
    @javafx.fxml.FXML
    private TextField qtyField;
    @javafx.fxml.FXML
    private Label statusLabel;

    @javafx.fxml.FXML
    public void initialize() {
    }

    @javafx.fxml.FXML
    public void recordDeliveryButtonOnClick(ActionEvent actionEvent) {
        try {
            String supplierName = supplierNameField.getText().trim();
            String invoiceId = invoiceIdField.getText().trim();
            int quantity = Integer.parseInt(qtyField.getText());

            if (supplierName.isEmpty()) {
                statusLabel.setText("Error: Supplier Name cannot be empty");
                statusLabel.setStyle("-fx-text-fill: red");
                return;
            }

            if (invoiceId.isEmpty()) {
                statusLabel.setText("Error: Invoice ID is required");
                statusLabel.setStyle("-fx-text-fill: red");
                return;
            }

            if (quantity <= 0) {
                statusLabel.setText("Error: Delivery quantity must be greater than 0.");
                statusLabel.setStyle("-fx-text-fill: red");
                return;
            }

            boolean databaseUpdateSuccess = true;

            if (databaseUpdateSuccess) {
                statusLabel.setText("Success! " + quantity + " units recorded from " + supplierName + " (Invoice: " + invoiceId + ").");
                statusLabel.setStyle("-fx-text-fill: green");
                supplierNameField.clear();
                invoiceIdField.clear();
                qtyField.clear();
            }
        } catch (NumberFormatException e) {
            statusLabel.setText("Error: Quantity must be a valid number!");
            statusLabel.setStyle("-fx-text-fill: red");
        }
    }

    @javafx.fxml.FXML
    public void backButtonOnClick(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InventoryDashboardView.fxml"));
            Scene dashboardScene = new Scene(fxmlLoader.load());

            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            currentStage.setTitle("Inventory Officer Dashboard");
            currentStage.setScene(dashboardScene);
            currentStage.show();
        } catch (IOException e) {
            statusLabel.setText("Error loading Dashboard! Check Console.");
            e.printStackTrace();
        }
    }
}