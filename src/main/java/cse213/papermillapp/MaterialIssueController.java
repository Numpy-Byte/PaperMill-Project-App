// This package contains all the classes for the paper mill application
package cse213.papermillapp;

// We need this to handle button clicks
import javafx.event.ActionEvent;
// This is needed to work with UI elements from the screen
import javafx.fxml.FXML;
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

// We need these to read and write files
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

// This class handles the material issue screen
// It gives materials to workers from the warehouse
public class MaterialIssueController
{
    // Text box to enter material ID
    @javafx.fxml.FXML
    private TextField materialIdTextField;
    // Label to show status messages
    @javafx.fxml.FXML
    private Label statusLabel;
    // Text box to enter quantity
    @javafx.fxml.FXML
    private TextField quantityTextField;
    // Text box to enter material name
    @javafx.fxml.FXML
    private TextField materialNameTextField;

    // List of valid material IDs that can be issued
    // This can grow or shrink in the future
    private ArrayList<String> validMaterialIDs = new ArrayList<>(Arrays.asList("MAT-01", "MAT-02", "MAT-03"));

    // This runs when user clicks Issue Material button
    @javafx.fxml.FXML
    public void issueMaterialOnClick(ActionEvent event) {
        // Get the text from the input boxes
        String materialId = materialIdTextField.getText().trim();
        String materialName = materialNameTextField.getText().trim();
        String quantityText = quantityTextField.getText().trim();

        // Validation: Check if any field is empty
        if (materialId.isEmpty() || materialName.isEmpty() || quantityText.isEmpty()) {
            statusLabel.setText("Validation Error: All fields must be filled!");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        try {
            // Convert the quantity text to a number
            int requestedQuantity = Integer.parseInt(quantityText);

            // Check if the material file exists
            File file = new File("Material.bin");
            if (!file.exists()) {
                statusLabel.setText("System Error: Material database not found!");
                statusLabel.setStyle("-fx-text-fill: red;");
                return;
            }
            // Create a list to hold materials
            ArrayList<Material> materialsList = new ArrayList<>();
            // Open the file for reading
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            // Variables to track what happened
            boolean materialFound = false;
            boolean stockDeducted = false;

            // Read all materials from the file
            while (true) {
                try {
                    Material m = (Material) ois.readObject();
                    materialsList.add(m);
                } catch (EOFException e) {
                    // Stop when we reach the end
                    break;
                }
            }

            // Close the file
            ois.close();
            
            // Go through the materials to find the one we want
            for (Material m : materialsList) {
                if (m.getId().equalsIgnoreCase(materialId)) {
                    materialFound = true;

                    // Check if we have enough stock
                    if (m.getCurrentStock() >= requestedQuantity) {
                        // Deduct the quantity from stock
                        m.setCurrentStock(m.getCurrentStock() - requestedQuantity);
                        stockDeducted = true;

                        // Show success message with remaining stock
                        statusLabel.setText("Success: " + requestedQuantity + " units issued. Remaining: " +
                                m.getCurrentStock());
                        statusLabel.setStyle("-fx-text-fill: green;");
                    }
                    break;
                }
            }
            
            // If material was not found, show error
            if (!materialFound) {
                statusLabel.setText("Error: Material ID '" + materialId + "' not found in warehouse.");
                statusLabel.setStyle("-fx-text-fill: red;");
                return;
            }
            
            // If stock was deducted, save the new values to file
            if (stockDeducted) {
                FileOutputStream fos = new FileOutputStream(file);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                // Write all materials back to the file
                for (Material m : materialsList) {
                    oos.writeObject(m);
                }
                oos.close();
            }
        } catch (NumberFormatException e) {
            // Show error if quantity is not a valid number
            statusLabel.setText("Validation Error: Quantity must be a valid number!");
            statusLabel.setStyle("-fx-text-fill: red;");
        } catch (Exception e) {
            // Show error if something else goes wrong
            statusLabel.setText("System Error: cold not process the material issue.");
            statusLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }

    // This runs when user clicks Logout button
    @javafx.fxml.FXML
    public void logoutButtonOnClick(ActionEvent actionEvent) {
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

            } catch ( Exception e) {
                // Show error if something goes wrong
                statusLabel.setText("Error returning to login Screen!");
                e.printStackTrace();
            }
        }
    }

    // This runs when user clicks Back button
    @FXML
    public void backToDashboardOnClick(ActionEvent actionEvent) {
        try {
            // Load the inventory navigation screen
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InventoryNavigationMenuView.fxml"));
            Scene dashboardScene = new Scene(fxmlLoader.load());

            // Get the current window
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            // Set the title
            stage.setTitle("Inventory Dashboard");
            // Show the dashboard
            stage.setScene(dashboardScene);
            stage.show();
            // Make window not resizable
            stage.setResizable(false);

        } catch (Exception e) {
            // Show error if something goes wrong
            statusLabel.setText("Error loading Dashboard! Check Console.");
            e.printStackTrace();
        }
    }
}