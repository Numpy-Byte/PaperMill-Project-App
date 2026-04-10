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

// We need these to write files
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Optional;

// This class handles the Add Supplier screen
// It saves new supplier information to the file
public class AddSupplierController {
    // Text box to enter supplier ID
    @javafx.fxml.FXML
    private TextField idTextField;
    // Text box to enter material supplied
    @javafx.fxml.FXML
    private TextField materialTextField;
    // Text box to enter company name
    @javafx.fxml.FXML
    private TextField nameTextField;
    // Text box to enter contact number
    @javafx.fxml.FXML
    private TextField contactTextField;
    // Label to show status messages
    @javafx.fxml.FXML
    private Label statusLabel;

    // This runs when the screen first loads
    @javafx.fxml.FXML
    public void initialize() {
        // This can be used to set up the screen initially
    }

    // This runs when user clicks Save Supplier button
    @javafx.fxml.FXML
    public void saveSupplierOnClick(ActionEvent actionEvent) {
        // Get the text from all input boxes
        String id = idTextField.getText().trim();
        String name = nameTextField.getText().trim();
        String contact = contactTextField.getText().trim();
        String material = materialTextField.getText().trim();

        // Check if any field is empty
        if (id.isEmpty() || name.isEmpty() || contact.isEmpty() || material.isEmpty()) {
            statusLabel.setText("Error: All field must be fillerd out!");
            statusLabel.setStyle("-fx-text-fill: red");
            return;
        }

        // Create a new Supplier object with the input data
        Supplier newSupplier = new Supplier(id, name, contact, material);

        // The file where we save suppliers
        File f = new File("Supplier.bin");

        try {
            // Open the file for writing (true means add to existing file)
            FileOutputStream fos = new FileOutputStream(f, true);
            ObjectOutputStream oos;

            // If file exists and has data, use special writer that can append
            if (f.exists() && f.length() > 0) {
                oos = new AppendableObjectOutputStream(fos);
            } else {
                // Otherwise use normal writer
                oos = new ObjectOutputStream(fos);
            }

            // Write the supplier to the file
            oos.writeObject(newSupplier);
            // Close the file
            oos.close();

            // Show success message
            statusLabel.setText("Supplier saved successfully!");
            statusLabel.setStyle("-fx-text-fill: green");

            // Clear all the input boxes for the next entry
            idTextField.clear();
            nameTextField.clear();
            contactTextField.clear();
            materialTextField.clear();

        } catch (Exception e) {
            // Show error if something goes wrong
            statusLabel.setText("Error saving supplier! Check console");
            statusLabel.setStyle("-fx-text-fill: red");
            e.printStackTrace();
        }
    }

    // This runs when user clicks Back button
    @javafx.fxml.FXML
    public void backtoDashboardOnClick(ActionEvent actionEvent) {
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
                // Print error if something goes wrong
                System.out.println("Error returning to login Screen!");
                e.printStackTrace();
            }
        }
    }
}