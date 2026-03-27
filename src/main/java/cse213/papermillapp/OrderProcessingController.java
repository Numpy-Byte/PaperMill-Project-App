package cse213.papermillapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class OrderProcessingController
{
    @javafx.fxml.FXML
    private TextField customerIdTextField;
    @javafx.fxml.FXML
    private Label statusLabel;
    @javafx.fxml.FXML
    private TextField quantityTextField;
    @javafx.fxml.FXML
    private TextField productTypeTextField;

    private ArrayList<String> validCustomers = new ArrayList<>(Arrays.asList("CUST01", "CUST02", "CUST03"));

    private int currentInveroryStock = 500;


    @javafx.fxml.FXML
    public void initialize() {
    }

    @javafx.fxml.FXML
    public void placeOrderOnClick(ActionEvent actionEvent) {

        String customerId = customerIdTextField.getText().trim();
        String productType = productTypeTextField.getText().trim();
        String quantityText = quantityTextField.getText().trim();

        try {
            if (customerId.isEmpty() || productType.isEmpty() || quantityText.isEmpty()) {
                statusLabel.setText("Validation Error: All fields must be filled");
                statusLabel.setStyle("-fx-text-fill: red");
                return;
            }

            if (!quantityText.matches("\\d+")) {
                statusLabel.setText("Validation Error: Quantity must be a valid number!");
                statusLabel.setStyle("-fx-text-fill: red");
                return;
            }

            int requestedQuantity = Integer.parseInt(quantityText);

            if (requestedQuantity <= 0) {
                statusLabel.setText("Validation Error: Quantity must be greater than 0");
                statusLabel.setStyle("-fx-text-fill: red");
                return;
            }

            if (!validCustomers.contains(customerId)) {
                statusLabel.setText("Verification Error: Customer ID not found in the system");
                statusLabel.setStyle("-fx-text-fill: red");
                return;
            }

            if (requestedQuantity > currentInveroryStock) {

                int deficit = requestedQuantity - currentInveroryStock;
                statusLabel.setText("Stock Low! Sent Production Request for " + deficit + " more units of " + productType + "." );
                statusLabel.setStyle("-fx-text-fill: red");
                return;
            } else {
                currentInveroryStock -= requestedQuantity;
                statusLabel.setText("Success! Order Confirmed. Remaining Stock: " + currentInveroryStock);
                statusLabel.setStyle("-fx-text-fill: green; -fx-font-weight: bold");

                customerIdTextField.clear();
                productTypeTextField.clear();
                quantityTextField.clear();
            }


        } catch (NumberFormatException e) {
            statusLabel.setText("System Error: The quantity entered is invalid or too large!");
            statusLabel.setStyle("-fx-text-fill: red");
            e.printStackTrace();

        } catch (Exception e) {
            statusLabel.setText("System Error: Could not process the order");
            statusLabel.setStyle("-fx-text-fill: red");
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
}