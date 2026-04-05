package cse213.papermillapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InventoryNavigationMenuView.fxml"));
            Scene dashboardScene = new Scene(fxmlLoader.load());

            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            currentStage.setTitle("Inventory Officer Dashboard");
            currentStage.setScene(dashboardScene);
            currentStage.show();
            currentStage.setResizable(false);

        } catch (IOException e) {
            statusLabel.setText("Error loading Dashboard! Check Console.");
            e.printStackTrace();
        }
    }

    @javafx.fxml.FXML
    public void logoutOnClick(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Log out Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to log out?");

        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == buttonTypeYes) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TheView.fxml"));
                Scene loginScene = new Scene(fxmlLoader.load());

                Stage currentStage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
                currentStage.setTitle("Paper Mill System - Login");
                currentStage.setScene(loginScene);
                currentStage.show();
                currentStage.setResizable(false);

            } catch ( Exception e) {
                statusLabel.setText("Error returning to login Screen!");
                e.printStackTrace();
            }
        }
    }
}