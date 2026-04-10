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

// We need these for file operations
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Optional;

// This class handles the View Machine History screen
// It shows the maintenance history of selected machines
public class ViewMachineHistoryController {
    // Column to show maintenance type
    @javafx.fxml.FXML
    private TableColumn<MaintenanceLog, String> typeCol;
    // Table to show maintenance history
    @javafx.fxml.FXML
    private TableView<MaintenanceLog> historyTable;
    // Column to show date
    @javafx.fxml.FXML
    private TableColumn<MaintenanceLog, String> dateCol;
    // Dropdown to select a machine
    @javafx.fxml.FXML
    private ComboBox<String> machineComboBox;
    // Column to show machine ID
    @javafx.fxml.FXML
    private TableColumn<MaintenanceLog, String> machineIdCol;
    // Label to show status messages
    @javafx.fxml.FXML
    private Label statusLabel;
    // Column to show remarks
    @javafx.fxml.FXML
    private TableColumn<MaintenanceLog, String> remarksCol;

    // This runs when the screen first loads
    @javafx.fxml.FXML
    public void initialize() {
        // Link table columns to the MaintenanceLog class properties
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        machineIdCol.setCellValueFactory(new PropertyValueFactory<>("machineId"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("maintenanceType"));
        remarksCol.setCellValueFactory(new PropertyValueFactory<>("remarks"));

        // Load machines into the dropdown
        loadMachineDropdown();
    }

    // This runs when user clicks "View History" button
    // It loads the maintenance history for the selected machine
    @javafx.fxml.FXML
    public void ViewHistoryOnClick(ActionEvent actionEvent) {
        // Get the selected machine from dropdown
        String selectedMachine = machineComboBox.getValue();
        // Check if a machine was selected
        if (selectedMachine == null) {
            // Show message
            statusLabel.setText("Please select a machine.");
            return;
        }

        // Create a list to hold the maintenance logs
        ArrayList<MaintenanceLog> logs = new ArrayList<>();
        // Get the maintenance log file
        File file = new File("MaintenanceLog.bin");
        // If file does not exist, show message
        if(!file.exists()) {
            statusLabel.setText("No log file found.");
            return;
        }

        // Read maintenance logs from the file
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            // Read all logs from the file
            while (true) {
                try {
                    // Read one maintenance log
                    MaintenanceLog log = (MaintenanceLog) ois.readObject();
                    // Check if this log is for the selected machine
                    if (log.getMachineId().equals(selectedMachine)) {
                        // Add to the list
                        logs.add(log);
                    }
                } catch (EOFException e) {
                    // Stop when we reach the end
                    break;
                }
            }
        } catch (Exception e) {
            // Print error if something goes wrong
            e.printStackTrace();
        }

        // Show the logs in the table
        historyTable.setItems(FXCollections.observableArrayList(logs));
    }

    // This loads machines from file into the dropdown
    private void loadMachineDropdown() {
        // Create a list to hold machine IDs
        ObservableList<String> ids = FXCollections.observableArrayList();
        // Try to read from Machine.bin file
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Machine.bin"))) {
            // Read all machines from the file
            while (true) {
                try {
                    // Read one machine
                    Machine m = (Machine) ois.readObject();
                    // Add machine ID to the list
                    ids.add(m.getId());
                } catch (EOFException e) {
                    // Stop when we reach the end
                    break;
                }
            }
        } catch (Exception e) {
            // Print error if something goes wrong
            e.printStackTrace();
        }

        // Set up the dropdown
        machineComboBox.setItems(ids);
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
            Scene scene = new Scene(fxmlLoader.load());
            // Get the current window
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            // Set the title
            stage.setTitle("Paper Mill - Production Supervisor Dashboard");
            // Show the dashboard screen
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            // Print error if screen cannot load
            e.printStackTrace();
        }
    }
}