// This package contains all the classes for the paper mill application
package cse213.papermillapp;

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
// These are needed to create popup dialogs
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
// This is needed to open a new window
import javafx.stage.Stage;

// We need these to handle screen loading errors
import java.io.IOException;
// We need this to wait for user response on popup
import java.util.Optional;

// This class handles the Production Dashboard screen
// It shows the main menu for production supervisors with buttons to different functions
public class ProductionDashboardController {
    // This runs when the screen first loads (empty because we don't need to set up anything)
    @javafx.fxml.FXML
    public void initialize() {
    }

    // This runs when user clicks "Record Production" button
    // It opens the screen where users can record what was produced
    @javafx.fxml.FXML
    public void recordProductionOnClick(ActionEvent actionEvent) {
        try {
            // Load the record production output screen from FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("RecordProductionOutputView.fxml"));
            // Create a new scene (screen) from the FXML
            Scene scene = new Scene(fxmlLoader.load());
            // Get the current window
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            // Set the title of the window
            stage.setTitle("Record Production Output");
            // Show the new screen in the window
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            // Print error if screen cannot load
            e.printStackTrace();
        }
    }

    // This runs when user clicks "Calculate Waste" button
    // It opens the screen where users can calculate waste materials
    @javafx.fxml.FXML
    public void calculateWasteOnClick(ActionEvent actionEvent) {
        try {
            // Load the calculate waste screen from FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CalculateWasteView.fxml"));
            // Create a new scene from the FXML
            Scene scene = new Scene(fxmlLoader.load());
            // Get the current window
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            // Set the title
            stage.setTitle("Paper Mill - Calculate Waste");
            // Show the screen
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            // Print error if screen cannot load
            e.printStackTrace();
        }
    }

    // This runs when user clicks "Set Production Target" button
    // It opens the screen where supervisors can set production goals
    @javafx.fxml.FXML
    public void setProductionTargetOnClick(ActionEvent actionEvent) {
        try {
            // Load the set production target screen from FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SetProductionTargetView.fxml"));
            // Create a new scene from the FXML
            Scene scene = new Scene(fxmlLoader.load());
            // Get the current window
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            // Set the title
            stage.setTitle("Paper Mill - Set Production Target");
            // Show the screen
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            // Print error if screen cannot load
            e.printStackTrace();
        }
    }

    // This runs when user clicks "Safety Checklist" button
    // It opens the screen where users can verify safety checklists
    @FXML
    public void safetyChecklistOnClick(ActionEvent actionEvent) {
        try {
            // Load the safety checklist screen from FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("VerifySafetyChecklistView.fxml"));
            // Create a new scene from the FXML
            Scene scene = new Scene(fxmlLoader.load());
            // Get the current window
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            // Set the title
            stage.setTitle("Paper Mill - Safety Checklist");
            // Show the screen
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            // Print error if screen cannot load
            e.printStackTrace();
        }
    }

    // This runs when user clicks "Update Machine Status" button
    // It opens the screen where users can update the status of machines
    @javafx.fxml.FXML
    public void updateMachineStatusOnClick(ActionEvent actionEvent) {
        try {
            // Load the update machine status screen from FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UpdateMachineStatusView.fxml"));
            // Create a new scene from the FXML
            Scene scene = new Scene(fxmlLoader.load());
            // Get the current window
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            // Set the title
            stage.setTitle("Update Machine Status");
            // Show the screen
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            // Print error if screen cannot load
            e.printStackTrace();
        }
    }

    // This runs when user clicks "Team Efficiency" button
    // It opens the screen where users can view team efficiency reports
    @javafx.fxml.FXML
    public void teamEfficiencyOnClick(ActionEvent actionEvent) {
        try {
            // Load the team efficiency report screen from FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TeamEfficiencyReportView.fxml"));
            // Create a new scene from the FXML
            Scene scene = new Scene(fxmlLoader.load());
            // Get the current window
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            // Set the title
            stage.setTitle("Paper Mill - Team Efficiency");
            // Show the screen
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            // Print error if screen cannot load
            e.printStackTrace();
        }
    }

    // This runs when user clicks "Machine History" button
    // It opens the screen where users can view the history of machines
    @javafx.fxml.FXML
    public void machineHistoryOnClick(ActionEvent actionEvent) {
        try {
            // Load the view machine history screen from FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ViewMachineHistoryView.fxml"));
            // Create a new scene from the FXML
            Scene scene = new Scene(fxmlLoader.load());
            // Get the current window
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            // Set the title
            stage.setTitle("Paper Mill - Machine History");
            // Show the screen
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            // Print error if screen cannot load
            e.printStackTrace();
        }
    }

    // This runs when user clicks "Assign Workers" button
    // It opens the screen where supervisors can assign workers to machines
    @javafx.fxml.FXML
    public void assignWorkersOnClick(ActionEvent actionEvent) {
        try {
            // Load the assign worker screen from FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AssignWorkerView.fxml"));
            // Create a new scene from the FXML
            Scene scene = new Scene(fxmlLoader.load());
            // Get the current window
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            // Set the title
            stage.setTitle("Paper Mill - Assign Workers");
            // Show the screen
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            // Print error if screen cannot load
            e.printStackTrace();
        }
    }

    // This runs when user clicks Logout button
    // It shows a confirmation and returns to login screen
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
                // Make window not resizable
                currentStage.setResizable(false);
            } catch (IOException e) {
                // Print error if screen cannot load
                e.printStackTrace();

            }
        }
    }
}