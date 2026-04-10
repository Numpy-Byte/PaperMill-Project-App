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
import javafx.scene.control.cell.PropertyValueFactory;
// This is needed to open a new window
import javafx.stage.Stage;

// We need these for file operations
import java.io.*;

// This class handles the Team Efficiency Report screen
// It shows how efficiently workers and machines are performing
public class TeamEfficiencyReportController {

    // Button to generate the efficiency report
    @FXML private Button generateReportBtn;
    // Label to show status messages
    @FXML private Label statusLabel;

    // Table to show efficiency reports
    @FXML private TableView<EfficiencyReport> efficiencyTable;
    // Column to show worker ID
    @FXML private TableColumn<EfficiencyReport, String> workerCol;
    // Column to show machine assigned
    @FXML private TableColumn<EfficiencyReport, String> machineCol;
    // Column to show tasks handled (output)
    @FXML private TableColumn<EfficiencyReport, Integer> outputCol;
    // Column to show efficiency rating
    @FXML private TableColumn<EfficiencyReport, String> ratingCol;

    // This runs when the screen first loads
    @FXML
    public void initialize() {
        // Link table columns to the EfficiencyReport class properties
        workerCol.setCellValueFactory(new PropertyValueFactory<>("workerId"));
        machineCol.setCellValueFactory(new PropertyValueFactory<>("machineAssigned"));
        outputCol.setCellValueFactory(new PropertyValueFactory<>("tasksHandled"));
        ratingCol.setCellValueFactory(new PropertyValueFactory<>("efficiencyRating"));
    }

    // This runs when user clicks "Generate Report" button
    // It loads efficiency data from file and displays in table
    @FXML
    public void generateReportOnClick(ActionEvent actionEvent) {
        // Create an observable list to hold efficiency reports
        ObservableList<EfficiencyReport> reportList = FXCollections.observableArrayList();
        // Get the efficiency data file
        File file = new File("EfficiencyData.bin");

        // If file exists, read the data
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                // Read all efficiency reports from the file
                while (true) {
                    try {
                        // Add each report to the list
                        reportList.add((EfficiencyReport) ois.readObject());
                    } catch (EOFException | ClassNotFoundException e) {
                        // Stop when we reach the end
                        break;
                    }
                }
            } catch (IOException e) {
                // Print error if something goes wrong
                e.printStackTrace();
            }

            // Show the reports in the table
            efficiencyTable.setItems(reportList);
            // Show success message in green
            statusLabel.setText("Success: Team efficiency report generated!");
            statusLabel.setStyle("-fx-text-fill: green;");
        } else {
            // Show error if file doesn't exist
            statusLabel.setText("Error: Efficiency data missing. Run Dummy Generator.");
            statusLabel.setStyle("-fx-text-fill: red;");
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
        java.util.Optional<ButtonType> result = alert.showAndWait();

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