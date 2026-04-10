// This package contains all the classes for the paper mill application
package cse213.papermillapp;

// We need this to work with lists that can update automatically in the UI
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
// We need this to handle button clicks
import javafx.event.ActionEvent;
// This is needed to load the screen layouts
import javafx.fxml.FXMLLoader;
// This is needed to work with screen elements
import javafx.scene.Node;
// This is needed to draw the screen
import javafx.scene.Scene;
// These are UI controls like buttons and labels
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
// This is needed to open a new window
import javafx.stage.Stage;

// We need these to read and write files
import java.io.*;
import java.util.ArrayList;
import java.util.Optional;

// This class handles the waste calculation screen
// It calculates how much material was wasted during production
public class CalculateWasteController {
    // Column to show machine ID in the table
    @javafx.fxml.FXML
    private TableColumn<WasteReport, String> machineCol;
    // Column to show waste percentage in the table
    @javafx.fxml.FXML
    private TableColumn<WasteReport, Double> wasteCol;
    // The table that shows all waste reports
    @javafx.fxml.FXML
    private TableView<WasteReport> wasteTable;
    // Column to show how much was produced
    @javafx.fxml.FXML
    private TableColumn<WasteReport, Integer> producedCol;
    // Button to calculate waste
    @javafx.fxml.FXML
    private Button CalculateBtn;
    // Label to show status messages
    @javafx.fxml.FXML
    private Label statusLabel;
    // Column to show raw material used
    @javafx.fxml.FXML
    private TableColumn<WasteReport, Integer> rawCol;

    // This runs when the screen first loads
    @javafx.fxml.FXML
    public void initialize() {
        // Set up the machine ID column to get data from WasteReport
        machineCol.setCellValueFactory(new PropertyValueFactory<>("machineId"));
        // Set up the raw material column to get data from WasteReport
        rawCol.setCellValueFactory(new PropertyValueFactory<>("rawMaterialUsed"));
        // Set up the produced column to get data from WasteReport
        producedCol.setCellValueFactory(new PropertyValueFactory<>("paperProduced"));
        // Set up the waste column to get data from WasteReport
        wasteCol.setCellValueFactory(new PropertyValueFactory<>("wastePercentage"));
    }

    // This runs when user clicks the Calculate Waste button
    @javafx.fxml.FXML
    public void CalculateWasteOnClick(ActionEvent actionEvent) {
        // Create empty lists to hold the data
        ArrayList<RawMaterialUsage> rawData = new ArrayList<>();
        ArrayList<ProductionRecord> prodData = new ArrayList<>();
        // Create a list that can show in the table
        ObservableList<WasteReport> reportList = FXCollections.observableArrayList();

        // Check if the raw material file exists
        File rawFile = new File("RawMaterialUsage.bin");
        if (rawFile.exists()) {
            // Try to read the file
            try {
                // Open the file for reading
                FileInputStream fis = new FileInputStream(rawFile);
                ObjectInputStream ois = new ObjectInputStream(fis);

                // Read all records from the file
                while (true) {
                    try {
                        // Read one record
                        rawData.add((RawMaterialUsage) ois.readObject());
                    } catch (EOFException | ClassNotFoundException e) {
                        // Stop when we reach the end
                        break;
                    }
                }
            } catch (IOException e) {
                // Print error if something goes wrong
                e.printStackTrace();
            }
        } else {
            // Show error if file does not exist
            statusLabel.setText("ERROR: Raw material data missing. Run Dummy Generator.");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        // Check if the production record file exists
        File prodFile = new File("ProductionRecord.bin");
        if (prodFile.exists()) {
            // Try to read the file
            try {
                // Open the file for reading
                FileInputStream fis = new FileInputStream(prodFile);
                ObjectInputStream ois = new ObjectInputStream(fis);

                // Read all records from the file
                while (true) {
                    try {
                        // Read one record
                        prodData.add((ProductionRecord) ois.readObject());
                    } catch (EOFException | ClassNotFoundException e) {
                        // Stop when we reach the end
                        break;
                    }
                }
            } catch (IOException e) {
                // Print error if something goes wrong
                e.printStackTrace();
            }
        }

        // Create a list to hold unique machines and their total raw material usage
        ArrayList<String> uniqueMachines = new ArrayList<>();
        ArrayList<Integer> totalRawUsage = new ArrayList<>();

        // Go through each raw material record and aggregate by machine
        // Since raw material usage is tracked per production batch, we need to sum usage per machine
        for (RawMaterialUsage raw : rawData) {
            String currentMachine = raw.getMachineId();
            int rawUsed = raw.getAmountUsed();

            // Check if this machine is already in our list
            boolean found = false;
            for (int i = 0; i < uniqueMachines.size(); i++) {
                if (uniqueMachines.get(i).equalsIgnoreCase(currentMachine)) {
                    // Machine already exists, add to its total
                    totalRawUsage.set(i, totalRawUsage.get(i) + rawUsed);
                    found = true;
                    break;
                }
            }

            // If machine not found, add it to the list
            if (!found) {
                uniqueMachines.add(currentMachine);
                totalRawUsage.add(rawUsed);
            }
        }

        // Now calculate waste for each machine that has raw material usage data
        for (int i = 0; i < uniqueMachines.size(); i++) {
            String machineId = uniqueMachines.get(i);
            int totalRawUsed = totalRawUsage.get(i);
            int totalProduced = 0;

            // Find all production records for this machine
            for (ProductionRecord prod : prodData) {
                if (prod.getMachineId().equalsIgnoreCase(machineId)) {
                    // Add up the production
                    totalProduced += prod.getProducedQuantity();
                }
            }

            // Calculate the waste percentage
            double wastePct = 0.0;
            if (totalRawUsed > 0 && totalRawUsed >= totalProduced) {
                // Calculate how much was wasted
                int wasteAmount = totalRawUsed - totalProduced;
                // Calculate percentage
                wastePct = ((double) wasteAmount / totalRawUsed) * 100;
                // Round to 2 decimal places
                wastePct = Math.round(wastePct * 100.0) / 100.0;
            }

            // Add this to the report list
            reportList.add(new WasteReport(machineId, totalRawUsed, totalProduced, wastePct));
        }

        // Show the reports in the table
        wasteTable.setItems(reportList);
        // Show success message
        statusLabel.setText("Success: Waste report generated!");
        // Make the text green
        statusLabel.setStyle("-fx-text-fill: green;");
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

    // This runs when user clicks Back button
    @javafx.fxml.FXML
    public void backToDashboardOnClick(ActionEvent actionEvent) {
        try {
            // Load the production dashboard screen
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ProductionDashboardView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            // Get the current window
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            // Set the title
            stage.setTitle("Production Supervisor Dashboard");
            // Show the dashboard
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}