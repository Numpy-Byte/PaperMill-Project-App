package cse213.papermillapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class MaterialIssueController
{
    @javafx.fxml.FXML
    private TextField materialIdTextField;
    @javafx.fxml.FXML
    private Label statusLabel;
    @javafx.fxml.FXML
    private TextField quantityTextField;
    @javafx.fxml.FXML
    private TextField materialNameTextField;

    // Using ArrayList so it can grow or shrink in the future!
    private ArrayList<String> validMaterialIDs = new ArrayList<>(Arrays.asList("MAT-01", "MAT-02", "MAT-03"));

    @javafx.fxml.FXML
    public void issueMaterialOnClick(ActionEvent event) {
        String materialId = materialIdTextField.getText();
        String materialName = materialNameTextField.getText();
        String quantityText = quantityTextField.getText();

        // Event-3: Validation (Did they leave it blank?)
        if (materialId.isEmpty() || materialName.isEmpty() || quantityText.isEmpty()) {
            statusLabel.setText("Validation Error: All fields must be filled!");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        try {
            int requestedQuantity = Integer.parseInt(quantityText);

            // Event-4: Verify Material Exists using ArrayList's .contains() method
            if (!validMaterialIDs.contains(materialId)) {
                statusLabel.setText("Verification Error: Material ID not found in the warehouse.");
                statusLabel.setStyle("-fx-text-fill: red;");
                return;
            }

            // Event-4 & 5: Verify stock availability and Deduct
            // (Using our dummy starting stock of 500 for testing)
            int currentStock = 500;

            if (requestedQuantity > currentStock) {
                statusLabel.setText("Error: Insufficient stock! Only " + currentStock + " units available.");
                statusLabel.setStyle("-fx-text-fill: red;");
            } else {
                // Deduct the stock
                currentStock -= requestedQuantity;

                // Event-6: Display issue confirmation
                statusLabel.setText("Success! " + requestedQuantity + " units of " + materialName + " sent to Production. Remaining: " + currentStock);
                statusLabel.setStyle("-fx-text-fill: green;");
            }

        } catch (NumberFormatException e) {
            statusLabel.setText("Validation Error: Quantity must be a valid number!");
            statusLabel.setStyle("-fx-text-fill: red;");
        } catch (Exception e) {
            statusLabel.setText("System Error: Could not process the material issue.");
            statusLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }

    @javafx.fxml.FXML
    public void logoutButtonOnClick(ActionEvent actionEvent) {
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

            } catch ( Exception e) {
                statusLabel.setText("Error returning to login Screen!");
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void backToDashboardOnClick(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InventoryDashboardView.fxml"));
            Scene dashboardScene = new Scene(fxmlLoader.load());

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Inventory Dashboard");
            stage.setScene(dashboardScene);
            stage.show();
        } catch (Exception e) {
            statusLabel.setText("Error loading Dashboard! Check Console.");
            e.printStackTrace();
        
        }
    }
}