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

// This class handles the Sync Stock screen
// It lets inventory officers sync or update stock quantities
public class SyncStockController
{
    // Text box to enter product ID
    @javafx.fxml.FXML
    private TextField idField;
    // Text box to enter product name
    @javafx.fxml.FXML
    private TextField nameField;
    // Text box to enter quantity
    @javafx.fxml.FXML
    private TextField qtyField;
    // Label to show status messages
    @javafx.fxml.FXML
    private Label statusLabel;

    // Create an inventory officer to process the product
    private InventoryOfficer officer = new InventoryOfficer(2430999, "Emdad", "1234", "Inventory", "Officer", 50000.0f);

    // This runs when the screen first loads (empty)
    @javafx.fxml.FXML
    public void initialize() {
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
            // Set the title (note: typo in original says "Dashbopard" but keeping for accuracy)
            currentStage.setTitle("Inventory Officer Dashbopard");
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

    // This runs when user clicks "Sync Stock" button
    // It saves the stock information
    @javafx.fxml.FXML
    public void syncStockButtonOnClick(ActionEvent actionEvent) {
        try {
            // Get the product ID from text box and convert to number
            int productId = Integer.parseInt(idField.getText());
            // Get the product name from text box
            String productName = nameField.getText();
            // Get the quantity from text box and convert to number
            int quantity = Integer.parseInt(qtyField.getText());

            // Check if name is empty or quantity is not positive
            if (productName.isEmpty() || quantity <= 0) {
                // Show error message in red
                statusLabel.setText("Error: Name cannot be empty and quantity must be > 0.");
                statusLabel.setStyle("-fx-text-fill: red;");
                return;
            }

            // Create a new product object
            Product newProduct = new Product(productId, "Raw Material", productName, 0.0f);

            // Process the product through the inventory officer
            boolean success = officer.processProduct(newProduct);

            // If processing succeeds
            if (success) {
                // Show success message in green
                statusLabel.setText("Success! " + quantity + " units of " + productName + " synced.");
                statusLabel.setStyle("-fx-text-fill: green;");
                // Clear all the text boxes for the next entry
                idField.clear();
                nameField.clear();
                qtyField.clear();
            } else {
                // Show failure message in red
                statusLabel.setText("Failed to sync: Product is expired or invalid.");
                statusLabel.setStyle("-fx-text-fill: red;");
            }
        } catch (NumberFormatException e) {
            // Show error if ID or quantity is not a valid number
            statusLabel.setText("Error: ID and Quantity must be numbers!");
            statusLabel.setStyle("-fx-text-fill: red;");
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