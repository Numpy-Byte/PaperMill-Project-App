// This package contains all the classes for the paper mill application
package cse213.papermillapp;

// We need this to create observable lists that update the UI automatically
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
// We need this to handle button clicks
import javafx.event.ActionEvent;
// This is needed to load screen layouts from FXML files
import javafx.fxml.FXML;
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
import java.io.*;
import java.util.ArrayList;
import java.util.Optional;

// This class handles the Assign Worker screen
// It lets supervisors assign workers to machines
public class AssignWorkerController {
    // Dropdown to select a worker
    @FXML
    private ComboBox<String> workerComboBox;
    // Dropdown to select a machine
    @FXML
    private ComboBox<String> machineComboBox;
    // Table to show worker assignments
    @FXML
    private TableView<Worker> assignmentTable;
    // Column to show worker ID
    @FXML
    private TableColumn<Worker, String> workerIdCol;
    // Column to show machine ID
    @FXML
    private TableColumn<Worker, String> machineIdCol;
    // Label to show status messages
    @FXML
    private Label statusLabel;

    // This is the list to store workers
    private ArrayList<Worker> workerList = new ArrayList<>();

    // This runs when the screen first loads
    @FXML
    public void initialize() {
        // Link table columns to the Worker class properties
        workerIdCol.setCellValueFactory(new PropertyValueFactory<>("workerId"));
        machineIdCol.setCellValueFactory(new PropertyValueFactory<>("assignedMachine"));

        // Load machines and workers
        loadMachineDropdown();
        loadWorkerData();
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


    // This runs when user clicks "Assign Worker" button
    // It assigns the selected worker to the selected machine
    @FXML
    public void assignWorkerOnClick(ActionEvent actionEvent) {
        // Get the selected worker ID from dropdown
        String selectedWorkerId = workerComboBox.getValue();
        // Get the selected machine ID from dropdown
        String selectedMachineId = machineComboBox.getValue();

        // Check if both are selected
        if (selectedWorkerId == null || selectedMachineId == null) {
            // Show error message in red
            statusLabel.setText("Error: Please select both a Worker and a Machine.");
            statusLabel.setStyle("-fx-text-fill: red");
            return;
        }

        // Try to find and update the worker
        boolean updated = false;
        for (Worker w : workerList) {
            // Check if this is the worker we want to assign
            if (w.getWorkerId().equals(selectedWorkerId)) {
                // Set the assigned machine
                w.setAssignedMachine(selectedMachineId);
                // Mark as updated
                updated = true;
                // Stop searching
                break;
            }
        }

        // If worker was found and updated
        if (updated) {
            // Save the updated data to file
            saveWorkerData();

            // Refresh the table
            assignmentTable.setItems(FXCollections.observableArrayList(workerList));
            assignmentTable.refresh();

            // Show success message in green
            statusLabel.setText("Success: Worker assigned!");
            statusLabel.setStyle("-fx-text-fill: green");

            // Clear the dropdowns for the next entry
            workerComboBox.setValue(null);
            machineComboBox.setValue(null);
        }
    }

    // This loads worker data from file
    private void loadWorkerData() {
        // Clear the list first
        workerList.clear();
        // Get the worker file
        File file = new File("Worker.bin");
        // If file does not exist, show error
        if (!file.exists()) {
            statusLabel.setText("Worker.bin not found! Run DummyDataGenerator.");
            return;
        }

        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            // Read all workers from the file
            while (true) {
                try {
                    // Read one worker
                    Worker w = (Worker) ois.readObject();
                    // Add to the list
                    workerList.add(w);
                } catch (EOFException | ClassNotFoundException e) {
                    // Stop when we reach the end or class not found
                    break;
                }
            }
        } catch (IOException e) {
            // Print error if something goes wrong
            e.printStackTrace();
        }

        // Show workers in the table
        assignmentTable.setItems(FXCollections.observableArrayList(workerList));

        // Create a list of worker IDs for the dropdown
        ObservableList<String> workerIds = FXCollections.observableArrayList();
        for (Worker w: workerList) {
            workerIds.add(w.getWorkerId());
        }
        // Set up the worker dropdown
        workerComboBox.setItems(workerIds);
    }

    // This loads machines from file into the dropdown
    private void loadMachineDropdown() {
        // Create a list to hold machine IDs
        ObservableList<String> machineIds = FXCollections.observableArrayList();
        // Get the machine file
        File machineFile = new File("Machine.bin");
        // If file exists, read the data
        if (machineFile.exists()) {
            try (FileInputStream fis = new FileInputStream(machineFile);
                 ObjectInputStream ois = new ObjectInputStream(fis)) {

                // Read all machines from the file
                while (true) {
                    try {
                        // Read one machine
                        Machine m = (Machine) ois.readObject();
                        // Add machine ID to the list
                        machineIds.add(m.getId());
                    } catch (EOFException | ClassNotFoundException e) {
                        // Stop when we reach the end or class not found
                        break;
                    }
                }
            } catch (IOException e) {
                // Print error if something goes wrong
                e.printStackTrace();
            }
        }

        // Set up the machine dropdown
        machineComboBox.setItems(machineIds);
    }

    // This saves worker data to file
    private void saveWorkerData() {
        try (FileOutputStream fos = new FileOutputStream("Worker.bin");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            // Write each worker to the file
            for (Worker w: workerList) {
                oos.writeObject(w);
            }
        } catch (IOException e) {
            // Print error if something goes wrong
            e.printStackTrace();
        }
    }

    // This runs when user clicks Back button
    // It returns to the production dashboard
    @FXML
    public void backToDashboardOnClick(ActionEvent actionEvent) {
        try {
            // Load the production dashboard screen from FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ProductionDashboardView.fxml"));
            // Create a new scene from the FXML
            Scene scene = new Scene(fxmlLoader.load());
            // Get the current window
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
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
}