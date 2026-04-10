// This package contains all the classes for the paper mill application
package cse213.papermillapp;

// We need this to handle button clicks
import javafx.event.ActionEvent;
// This is needed to load other screens
import javafx.fxml.FXMLLoader;
// This is needed to work with screen elements
import javafx.scene.Node;
// This is needed to draw the screen
import javafx.scene.Scene;
// This is for popup alerts
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
// This is for status messages
import javafx.scene.control.Label;
// This is for text input boxes
import javafx.scene.control.TextField;
// This is needed to open a new window
import javafx.stage.Stage;

// We need these to work with lists
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

// This class handles the Damaged Material screen
// It removes damaged materials from the inventory
public class DamagedMaterialController
{
    // Text box to enter material ID
    @javafx.fxml.FXML
    private TextField materialIdTextField;
    // Text box to enter how many are damaged
    @javafx.fxml.FXML
    private TextField damagedQuantityTextField;
    // Label to show status messages
    @javafx.fxml.FXML
    private Label statusLabel;

    // List of valid material IDs
    private ArrayList<String> validMatrials = new ArrayList<>(Arrays.asList("MAT-01", "MAT-02", "MAT-03"));

    // This runs when the screen first loads
    @javafx.fxml.FXML
    public void initialize() {
        // This can be used to set up the screen initially
    }

    // This runs when user clicks Logout button
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
                // Load the login screen
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TheView.fxml"));
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
            } catch (Exception e) {
                // Show error if something goes wrong
                statusLabel.setText("Error returning to login Screen!");
                e.printStackTrace();
            }
        }
    }

    // This runs when user clicks Remove Damaged Stock button
    @javafx.fxml.FXML
    public void removeDamagedStockOnClick(ActionEvent actionEvent) {
            // Get the text from input boxes
            String materialId = materialIdTextField.getText();
            String damagedQuantity = damagedQuantityTextField.getText();

            // Check if any field is empty
            if (materialId.isEmpty() || damagedQuantity.isEmpty()) {
                statusLabel.setText("Please fill in all the fields");
                statusLabel.setStyle("-fx-text-fill: red;");
                return;
            }

            // Check if the material ID is valid
            if (!validMatrials.contains(materialId)) {
                statusLabel.setText("Material ID is not valid");
                statusLabel.setStyle("-fx-text-fill: red;");
                return;
            }

            try {
                // Convert the quantity text to a number
                int DamagedQuantity = Integer.parseInt(damagedQuantity);

                // Get the current stock (this is hardcoded for demo)
                int currentStock = 500;

                // Check if damaged quantity is more than current stock
                if(DamagedQuantity > currentStock) {
                    statusLabel.setText("Error: Damaged Quantity Exceeds the Current Stock");
                    statusLabel.setStyle("-fx-text-fill: red;");
                } else {
                    // Calculate new stock after removing damaged items
                    int newStock = currentStock - DamagedQuantity;
                    // Show success message
                    statusLabel.setText("Success: Removed " + DamagedQuantity + " units. New Stock: " + newStock);
                    statusLabel.setStyle("-fx-text-fill: darkorange;");
                }
            } catch (NumberFormatException e) {
                // Show error if quantity is not a valid number
                statusLabel.setText("Error: Quantity must be a number.");
                statusLabel.setStyle("-fx-text-fill: red;");
            }
    }

    // This runs when user clicks Back button
    @javafx.fxml.FXML
    public void backToDashboardOnClick(ActionEvent actionEvent) {
       try {
           // Load the inventory navigation menu screen
           FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InventoryNavigationMenuView.fxml"));
           Scene dashboardScene = new Scene(fxmlLoader.load());

           // Get the current window
           Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
           // Set the title
           stage.setTitle("Dashboard Scene");
           // Show the dashboard
           stage.setScene(dashboardScene);
           stage.show();
           // Make window not resizable
           stage.setResizable(false);

       } catch (Exception e) {
           // Show error if something goes wrong
           statusLabel.setText("Error loading Dashboard Screen! Check Console");
           e.printStackTrace();
       }
    }
}