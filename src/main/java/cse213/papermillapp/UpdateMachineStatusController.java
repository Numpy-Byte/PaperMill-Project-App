// This package contains all the classes for the paper mill application
package cse213.papermillapp;

// We need this to create observable lists that update the UI automatically
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
// We need this to handle button clicks
import javafx.event.ActionEvent;
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

// This import is unused but kept for accuracy
import javax.crypto.Mac;
// We need these for file operations
import java.io.*;
import java.util.ArrayList;
import java.util.Optional;

// This class handles the Update Machine Status screen
// It lets supervisors update the status of machines in the factory
public class UpdateMachineStatusController
{
    // Column to show machine status in table
    @javafx.fxml.FXML
    private TableColumn<Machine, String> statusCol;
    // Dropdown to select new status
    @javafx.fxml.FXML
    private ComboBox<String> statusComboBox;
    // Column to show machine name in table
    @javafx.fxml.FXML
    private TableColumn<Machine, String> machineNameCol;
    // Table to show all machines
    @javafx.fxml.FXML
    private TableView<Machine> machineTable;
    // Column to show machine ID in table
    @javafx.fxml.FXML
    private TableColumn<Machine, String> machineIdCol;
    // Dropdown to select machine ID
    @javafx.fxml.FXML
    private ComboBox<String> machineIdComboBox;
    // Label to show status messages
    @javafx.fxml.FXML
    private Label statusLabel;

    // This is the list to store machines
    private ArrayList<Machine> machineList = new ArrayList<>();

    // This runs when the screen first loads
    @javafx.fxml.FXML
    public void initialize() {
        // Link table columns to the Machine class properties
        machineIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        machineNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Set up the status dropdown options
        ObservableList<String> statusOptions = FXCollections.observableArrayList("Running", "Maintenance", "Broken");
        statusComboBox.setItems(statusOptions);

        // Load machine data from file
        loadMachineData();
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
                Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                // Set the title
                currentStage.setTitle("Paper Mill System - Login");
                // Show the login screen
                currentStage.setScene(loginScene);
                currentStage.show();
            } catch (Exception e) {
                // Print error if screen cannot load
                e.printStackTrace();
            }
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
            Scene dashboardScene = new Scene(fxmlLoader.load());
            // Get the current window
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            // Set the title
            stage.setTitle("Paper Mill - Production Supervisor Dashboard");
            // Show the dashboard screen
            stage.setScene(dashboardScene);
            stage.show();
        } catch (IOException e) {
            // Print error if screen cannot load
            e.printStackTrace();
        }
    }

    // This runs when user clicks "Update Status" button
    // It updates the machine status
    @javafx.fxml.FXML
    public void updateStatusOnClick(ActionEvent actionEvent) {
        // Get the selected machine ID from dropdown
        String selectedMachineId = machineIdComboBox.getValue();
        // Get the new status from dropdown
        String newStatus = statusComboBox.getValue();

        // Check if both are selected
        if (selectedMachineId == null || newStatus == null) {
            // Show error message in red
            statusLabel.setText("Error: Please select both Machine ID and Status.");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        // Try to find and update the machine
        boolean updated = false;
        for (Machine m : machineList) {
            // Check if this is the machine we want to update
            if (m.getId().equals(selectedMachineId)) {
                // Set the new status
                m.setStatus(newStatus);
                // Mark as updated
                updated = true;
                // Stop searching
                break;
            }
        }

        // If machine was found and updated
        if (updated) {
            // Save the updated data to file
            saveMachineData();

            // Refresh the table
            machineTable.setItems(FXCollections.observableArrayList(machineList));
            machineTable.refresh();

            // Show success message in green
            statusLabel.setText("Success: " + selectedMachineId + " is now marked as " + newStatus + ".");
            statusLabel.setStyle("-fx-text-fill: green;");

            // Clear the dropdowns for the next entry
            machineIdComboBox.setValue(null);
            statusComboBox.setValue(null);
        } else {
            // Show error if machine was not found
            statusLabel.setText("System Error: Could not update status");
            statusLabel.setStyle("-fx-text-fill: red;");
        }
    }

    // This loads machine data from the file
    private void loadMachineData() {
        // Clear the list first
        machineList.clear();
        // Get the machine file
        File file = new File("Machine.bin");
        // If file does not exist, show error
        if (!file.exists()) {
            statusLabel.setText("Error: Machine.bin not found!");
            return;
        }

        try {
            // Open the file for reading
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            // Read all machines from the file
            while (true) {
                try {
                    // Read one machine
                    Machine m = (Machine) ois.readObject();
                    // Add to the list
                    machineList.add(m);
                } catch (EOFException e) {
                    // Stop when we reach the end
                    break;
                } catch (ClassNotFoundException e) {
                    // Stop if class not found
                    break;
                }
            }
            // Close the file
            ois.close();
        } catch (IOException e) {
            // Print error if something goes wrong
            e.printStackTrace();
        }

        // Show machines in the table
        machineTable.setItems(FXCollections.observableArrayList(machineList));
        // Create a list of machine IDs for the dropdown
        ObservableList<String> machineIds = FXCollections.observableArrayList();
        for (Machine m : machineList) {
            machineIds.add(m.getId());
        }
        // Set up the machine ID dropdown
        machineIdComboBox.setItems(machineIds);
    }

    // This saves machine data to the file
    private void saveMachineData() {
        try {
            // Open the file for writing
            FileOutputStream fos = new FileOutputStream("Machine.bin");
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            // Write each machine to the file
            for (Machine m: machineList) {
                oos.writeObject(m);
            }
        } catch (Exception e) {
            // Print error if something goes wrong
            e.printStackTrace();
        }
    }
}