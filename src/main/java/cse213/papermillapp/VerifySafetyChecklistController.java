// This package contains all the classes for the paper mill application
package cse213.papermillapp;

// We need this to create observable lists that update the UI automatically
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
import javafx.stage.Stage;

// We need these for file operations
import java.io.*;
import java.util.ArrayList;
import java.util.Optional;

// This class handles the Verify Safety Checklist screen
// It lets supervisors verify safety checks before approving machine operation
public class VerifySafetyChecklistController {

    // Dropdown to select a machine
    @FXML private ComboBox<String> machineComboBox;
    // Checkbox for safety question 1
    @FXML private CheckBox q1CheckBox;
    // Checkbox for safety question 2
    @FXML private CheckBox q2CheckBox;
    // Checkbox for safety question 3
    @FXML private CheckBox q3CheckBox;
    // Button to approve the operation
    @FXML private Button approveBtn;

    // This is the list to store safety logs
    private ArrayList<SafetyLog> logList = new ArrayList<>();
    // Label to show status messages
    @FXML
    private Label statusLabel;

    // This runs when the screen first loads
    @FXML
    public void initialize() {
        // Load machines into the dropdown
        loadMachineDropdown();
        // Load existing logs from file (so we don't overwrite them)
        loadExistingLogs();
    }

    // This runs when user clicks "Approve Operation" button
    // It verifies all safety checks and approves the machine for operation
    @FXML
    public void approveOperationOnClick(ActionEvent actionEvent) {
        // Get the selected machine from dropdown
        String selectedMachine = machineComboBox.getValue();

        // 1. Validate Machine Selection
        // Check if a machine was selected
        if (selectedMachine == null) {
            // Show error message in red
            statusLabel.setText("Error: Please select a machine first.");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        // 2. Validate Checkboxes
        // Check if all safety checkboxes are checked
        if (q1CheckBox.isSelected() && q2CheckBox.isSelected() && q3CheckBox.isSelected()) {

            // Create a new safety log for the approved machine
            SafetyLog newLog = new SafetyLog(selectedMachine, true);
            // Add to the list
            logList.add(newLog);
            // Save to file
            saveLogsToFile();

            // Show success message in green
            statusLabel.setText("Success: " + selectedMachine + " is APPROVED for operation.");
            statusLabel.setStyle("-fx-text-fill: green;");

            // Clear the form for the next machine
            machineComboBox.setValue(null);
            q1CheckBox.setSelected(false);
            q2CheckBox.setSelected(false);
            q3CheckBox.setSelected(false);

        } else {
            // Show error if not all checkboxes are checked
            statusLabel.setText("Error: All safety checks must be verified before approval.");
            statusLabel.setStyle("-fx-text-fill: red;");
        }
    }

    // This loads machines from file into the dropdown
    private void loadMachineDropdown() {
        // Create a list to hold machine IDs
        ObservableList<String> machineIds = FXCollections.observableArrayList();
        // Get the machine file
        File file = new File("Machine.bin");
        // If file exists, read the data
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                // Read all machines from the file
                while (true) {
                    try {
                        // Read one machine
                        Machine m = (Machine) ois.readObject();
                        // Add machine ID to the list
                        machineIds.add(m.getId());
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
        // Set up the dropdown with machine IDs
        machineComboBox.setItems(machineIds);
    }

    // This loads existing safety logs from file
    private void loadExistingLogs() {
        // Clear the list first
        logList.clear();
        // Get the safety log file
        File file = new File("SafetyLog.bin");
        // If file exists, read the data
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                // Read all logs from the file
                while (true) {
                    try {
                        // Add each log to the list
                        logList.add((SafetyLog) ois.readObject());
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
    }

    // This saves safety logs to file
    private void saveLogsToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("SafetyLog.bin"))) {
            // Write each log to the file
            for (SafetyLog log : logList) {
                oos.writeObject(log);
            }
        } catch (IOException e) {
            // Print error if something goes wrong
            e.printStackTrace();
        }
    }

    // This runs when user clicks Back button
    // It returns to the production dashboard
    @FXML
    public void backToDashboardOnClick(ActionEvent event) {
        try {
            // Load the production dashboard screen from FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ProductionDashboardView.fxml"));
            // Create a new scene from the FXML
            Scene scene = new Scene(fxmlLoader.load());
            // Get the current window
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            // Set the title
            stage.setTitle("Production Supervisor Dashboard");
            // Show the dashboard screen
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            // Print error if screen cannot load
            e.printStackTrace();
        }
    }

    // This runs when user clicks Logout button
    // It shows confirmation and returns to login screen
    @FXML
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
}