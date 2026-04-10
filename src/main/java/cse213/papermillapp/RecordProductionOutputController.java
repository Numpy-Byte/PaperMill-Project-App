// This package contains all the classes for the paper mill application
package cse213.papermillapp;

// We need this to work with lists that can update automatically in the UI
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
// We need this to handle button clicks
import javafx.event.ActionEvent;
// This is needed to connect the controller to the screen
import javafx.fxml.FXML;
// This is needed to load screen layouts from FXML files
import javafx.fxml.FXMLLoader;
// This is needed to handle screen elements
import javafx.scene.Node;
// This is needed to draw the screen
import javafx.scene.Scene;
// These are UI controls
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
// This is needed to open a new window
import javafx.stage.Stage;

// We need these to read and write files
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.EOFException;
// We need this to work with lists
import java.util.List;
import java.util.ArrayList;
// We need this to wait for user response on popups
import java.util.Optional;

// This class handles the Record Production Output screen
// It lets users record how much was produced and save it to a file
public class RecordProductionOutputController {
    // Text box to enter machine ID
    @javafx.fxml.FXML
    private TextField machineIdTextField;
    // Text box to enter quantity produced
    @javafx.fxml.FXML
    private TextField quantityTextField;
    // Text box to enter batch number
    @javafx.fxml.FXML
    private TextField batchNumberTextField;
    // Table to show production records
    @javafx.fxml.FXML
    private TableView<ProductionRecord> productionTable;
    // Column for machine ID
    @javafx.fxml.FXML
    private TableColumn<ProductionRecord, String> machineIdCol;
    // Column for quantity
    @javafx.fxml.FXML
    private TableColumn<ProductionRecord, Integer> quantityCol;
    // Column for batch number
    @javafx.fxml.FXML
    private TableColumn<ProductionRecord, String> batchNumberCol;

    // Observable list to hold production records for the table
    private ObservableList<ProductionRecord> productionRecords = FXCollections.observableArrayList();

    // This runs when the screen first loads (empty)
    @javafx.fxml.FXML
    public void initialize() {
        // Set up the machine ID column to show machine ID from the production record
        machineIdCol.setCellValueFactory(new PropertyValueFactory<>("machineId"));
        // Set up the quantity column to show quantity from the production record
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("producedQuantity"));
        // Set up the batch number column to show batch number from the production record
        batchNumberCol.setCellValueFactory(new PropertyValueFactory<>("batchNumber"));

        // Set the table items to the observable list
        productionTable.setItems(productionRecords);

        // Load existing production records into the table
        refreshProductionTable();
    }

    // This runs when user clicks the Save button
    // It reads the input and saves the production record
    @javafx.fxml.FXML
    public void saveProductionOnClick(ActionEvent actionEvent) {
        // Get the machine ID from text box
        String machineId = machineIdTextField.getText().trim().toUpperCase();
        // Get the quantity from text box
        String quantityStr = quantityTextField.getText().trim();
        // Get the batch number from text box
        String batchNumber = batchNumberTextField.getText().trim();

        // Check if all fields have data
        if(machineId.isEmpty() || quantityStr.isEmpty() || batchNumber.isEmpty()) {
            // Show error if any field is empty
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("All fields are required!");
            alert.showAndWait();
            return;
        }

        // Try to convert quantity to a number
        int quantity;
        try {
            quantity = Integer.parseInt(quantityStr);
        } catch (NumberFormatException e) {
            // Show error if quantity is not a valid number
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Invalid quantity!Please enter a valid number.");
            alert.showAndWait();
            return;
        }

        // Check if quantity is positive
        if(quantity <= 0) {
            // Show error if quantity is zero or negative
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Quantity must be greater than zero!");
            alert.showAndWait();
            return;
        }

        // Estimate raw material usage for this production (production + 10% waste)
        // This simulates tracking raw material consumption during production
        int estimatedRawUsage = (int) Math.ceil(quantity * 1.1); // 10% waste factor

        // Create raw material usage record for this production batch
        RawMaterialUsage rawUsage = new RawMaterialUsage(machineId, estimatedRawUsage);

        // Create a new production record with the entered data
        ProductionRecord record = new ProductionRecord(machineId, quantity, batchNumber);

        try {
            // Read existing production records
            List<ProductionRecord> existingProd = new ArrayList<>();
            File prodFile = new File("ProductionRecord.bin");
            if (prodFile.exists()) {
                try (FileInputStream fis = new FileInputStream(prodFile);
                     ObjectInputStream ois = new ObjectInputStream(fis)) {
                    while (true) {
                        try {
                            existingProd.add((ProductionRecord) ois.readObject());
                        } catch (EOFException | ClassNotFoundException e) {
                            break;
                        }
                    }
                } catch (IOException e) {
                    // File might be corrupted, start fresh
                    existingProd.clear();
                }
            }
            existingProd.add(record);

            // Write all production records back to file
            try (FileOutputStream fos = new FileOutputStream(prodFile);
                 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                for (ProductionRecord r : existingProd) {
                    oos.writeObject(r);
                }
            }

            // Read existing raw material usage records
            List<RawMaterialUsage> existingRaw = new ArrayList<>();
            File rawFile = new File("RawMaterialUsage.bin");
            if (rawFile.exists()) {
                try (FileInputStream fis = new FileInputStream(rawFile);
                     ObjectInputStream ois = new ObjectInputStream(fis)) {
                    while (true) {
                        try {
                            existingRaw.add((RawMaterialUsage) ois.readObject());
                        } catch (EOFException | ClassNotFoundException e) {
                            break;
                        }
                    }
                } catch (IOException e) {
                    // File might be corrupted, start fresh
                    existingRaw.clear();
                }
            }
            existingRaw.add(rawUsage);

            // Write all raw material usage records back to file
            try (FileOutputStream fos = new FileOutputStream(rawFile);
                 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                for (RawMaterialUsage r : existingRaw) {
                    oos.writeObject(r);
                }
            }

            // Show success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Production record saved successfully!");
            alert.showAndWait();

            // Clear all the text boxes for the next entry
            machineIdTextField.clear();
            quantityTextField.clear();
            batchNumberTextField.clear();

            // Add the new record to the table
            productionRecords.add(record);
        } catch (IOException e) {
            // Show error if file cannot be saved
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error saving production record!");
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    // This method refreshes the production records table
    private void refreshProductionTable() {
        try {
            // Clear the current table data
            productionRecords.clear();

            // Read all production records from the file
            File prodFile = new File("ProductionRecord.bin");
            if (prodFile.exists()) {
                FileInputStream prodFis = new FileInputStream(prodFile);
                ObjectInputStream prodOis = new ObjectInputStream(prodFis);
                try {
                    while (true) {
                        // Read each production record object
                        ProductionRecord record = (ProductionRecord) prodOis.readObject();
                        // Add it to the observable list
                        productionRecords.add(record);
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    // End of file reached
                } finally {
                    prodOis.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // This runs when user clicks Back button
    // It returns to the production dashboard
    @javafx.fxml.FXML
    public void backToDashboardOnClick(ActionEvent actionEvent) {
        try {
            // Load the production dashboard screen from FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ProductionDashboardView.fxml"));
            // Create a new scene from the FXML
            Scene scene = new Scene(fxmlLoader.load());
            // Get the current window
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            // Set the title
            stage.setTitle("Paper Mill - Production Dashboard");
            // Show the dashboard screen
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            // Print error if screen cannot load
            System.out.println("Error loading Production Dashboard! Check Console");
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
            } catch (Exception e) {
                // Print error if screen cannot load
                System.out.println("Error loading login Screen!");
                e.printStackTrace();
            }
        }
    }
}
