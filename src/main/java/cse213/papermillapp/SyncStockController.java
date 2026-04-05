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

public class SyncStockController
{
    @javafx.fxml.FXML
    private TextField idField;
    @javafx.fxml.FXML
    private TextField nameField;
    @javafx.fxml.FXML
    private TextField qtyField;
    @javafx.fxml.FXML
    private Label statusLabel;

    private InventoryOfficer officer = new InventoryOfficer(2430999, "Emdad", "1234", "Inventory", "Officer", 50000.0f);

    @javafx.fxml.FXML
    public void initialize() {
    }

    @javafx.fxml.FXML
    public void backButtonOnClick(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InventoryNavigationMenuView.fxml"));
            Scene dashboardScene = new Scene(fxmlLoader.load());

            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            currentStage.setTitle("Inventory Officer Dashbopard");
            currentStage.setScene(dashboardScene);
            currentStage.show();
            currentStage.setResizable(false);

        } catch (IOException e) {
            statusLabel.setText("Error loading Dashboard! Check Console.");
            e.printStackTrace();
        }

    }

    @javafx.fxml.FXML
    public void syncStockButtonOnClick(ActionEvent actionEvent) {
        try {
            int productId = Integer.parseInt(idField.getText());
            String productName = nameField.getText();
            int quantity = Integer.parseInt(qtyField.getText());

            if (productName.isEmpty() || quantity <= 0) {
                statusLabel.setText("Error: Name cannot be empty and quantity must be > 0.");
                statusLabel.setStyle("-fx-text-fill: red;");
                return;
            }

            Product newProduct = new Product(productId, "Raw Material", productName, 0.0f);

            boolean success = officer.processProduct(newProduct);

            if (success) {
                statusLabel.setText("Success! " + quantity + " units of " + productName + " synced.");
                statusLabel.setStyle("-fx-text-fill: green;");
                idField.clear();
                nameField.clear();
                qtyField.clear();
            } else {
                statusLabel.setText("Failed to sync: Product is expired or invalid.");
                statusLabel.setStyle("-fx-text-fill: red;");
            }
        } catch (NumberFormatException e) {
            statusLabel.setText("Error: ID and Quantity must be numbers!");
            statusLabel.setStyle("-fx-text-fill: red;");
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