// This package contains all the classes for the paper mill application
package cse213.papermillapp;

// We need this to handle button clicks
import javafx.event.ActionEvent;
// This is needed to load screen layouts from FXML files
import javafx.fxml.FXMLLoader;
// This is needed to handle screen elements
import javafx.scene.Node;
// This is needed to draw the screen
import javafx.scene.Scene;
// These are UI controls
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
// This is needed to open a new window
import javafx.stage.Stage;

// We need these to handle screen loading errors
import java.io.IOException;
// We need this to wait for user response on popup
import java.util.Optional;

// This class handles the Record Delivery screen
// It lets inventory officers record deliveries from suppliers
public class RecordDeliveryController
{
    // Text box to enter invoice ID
    @javafx.fxml.FXML
    private TextField invoiceIdField;
    // Text box to enter supplier name
    @javafx.fxml.FXML
    private TextField supplierNameField;
    // Text box to enter quantity delivered
    @javafx.fxml.FXML
    private TextField qtyField;
    // Label to show status messages
    @javafx.fxml.FXML
    private Label statusLabel;

    // This runs when the screen first loads (empty)
    @javafx.fxml.FXML
    public void initialize() {
    }

    // This runs when user clicks "Record Delivery" button
    // It saves the delivery details
    @javafx.fxml.FXML
    public void recordDeliveryButtonOnClick(ActionEvent actionEvent) {
        try {
            // Get the supplier name from text box
            String supplierName = supplierNameField.getText().trim();
            // Get the invoice ID from text box
            String invoiceId = invoiceIdField.getText().trim();
            // Get the quantity from text box and convert to number
            int quantity = Integer.parseInt(qtyField.getText());

            // Check if supplier name is empty
            if (supplierName.isEmpty()) {
                // Show error message in red
                statusLabel.setText("Error: Supplier Name cannot be empty");
                statusLabel.setStyle("-fx-text-fill: red");
                return;
            }

            // Check if invoice ID is empty
            if (invoiceId.isEmpty()) {
                // Show error message in red
                statusLabel.setText("Error: Invoice ID is required");
                statusLabel.setStyle("-fx-text-fill: red");
                return;
            }

            // Check if quantity is positive
            if (quantity <= 0) {
                // Show error message in red
                statusLabel.setText("Error: Delivery quantity must be greater than 0.");
                statusLabel.setStyle("-fx-text-fill: red");
                return;
            }

            // Try to update the database (simulated here)
            boolean databaseUpdateSuccess = true;

            // If database update succeeds
            if (databaseUpdateSuccess) {
                // Show success message in green
                statusLabel.setText("Success! " + quantity + " units recorded from " + supplierName + " (Invoice: " + invoiceId + ").");
                statusLabel.setStyle("-fx-text-fill: green");
                // Clear all the text boxes for the next entry
                supplierNameField.clear();
                invoiceIdField.clear();
                qtyField.clear();
            }
        } catch (NumberFormatException e) {
            // Show error if quantity is not a valid number
            statusLabel.setText("Error: Quantity must be a valid number!");
            statusLabel.setStyle("-fx-text-fill: red");
        }
    }

    // This runs when user clicks Back button
    // It returns to the inventory menu
    @javafx.fxml.FXML
    public void backButtonOnClick(ActionEvent actionEvent) {
        try {
            // Load the inventory navigation menu screen from FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InventoryNavigationMenuView.fxml"));
            // Create a new scene from the FXML
            Scene dashboardScene = new Scene(fxmlLoader.load());

            // Get the current window
            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            // Set the title
            currentStage.setTitle("Inventory Officer Dashboard");
            // Show the dashboard screen
            currentStage.setScene(dashboardScene);
            currentStage.show();
            // Make window not resizable
            currentStage.setResizable(false);

        } catch (IOException e) {
            // Show error if screen cannot load
            statusLabel.setText("Error loading Dashboard! Check Console.");
            e.printStackTrace();
        }
    }

    // This runs when user clicks Logout button
    // It shows confirmation and returns to login screen
    @javafx.fxml.FXML
    public void logoutOnClick(ActionEvent actionEvent) {
        // Create a confirmation popup
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Log out Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to log out?");

        // Add Yes and No buttons
        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        // Wait for user to click a button
        Optional<ButtonType> result = alert.showAndWait();

        // If user clicked Yes, go to login screen
        if (result.isPresent() && result.get() == buttonTypeYes) {
            try {
                // Load the login screen from FXML file
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TheView.fxml"));
                // Create a new scene from the FXML
                Scene loginScene = new Scene(fxmlLoader.load());

                // Get the current window
                Stage currentStage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
                // Set the title
                currentStage.setTitle("Paper Mill System - Login");
                // Show the login screen
                currentStage.setScene(loginScene);
                currentStage.show();
                // Make window not resizable
                currentStage.setResizable(false);

            } catch ( Exception e) {
                // Show error if screen cannot load
                statusLabel.setText("Error returning to login Screen!");
                e.printStackTrace();
            }
        }
    }
}