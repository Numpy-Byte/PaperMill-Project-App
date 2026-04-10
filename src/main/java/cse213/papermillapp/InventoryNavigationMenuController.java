// This package contains all the classes for the paper mill application
package cse213.papermillapp;

// We need this to handle button clicks and user actions
import javafx.event.ActionEvent;
// We need this to connect the controller to the FXML screen
import javafx.fxml.FXML;
// We need this to load FXML screen files
import javafx.fxml.FXMLLoader;
// We need this to get the current window/stage
import javafx.scene.Node;
// We need this to create and display screens
import javafx.scene.Scene;
// We need these to show popup messages and confirmation dialogs
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
// We need this to display text labels on screen
import javafx.scene.control.Label;
// We need this to open windows
import javafx.stage.Stage;

// We need this to handle file loading errors
import java.io.IOException;
// We need this to wait for user response on popups
import java.util.Optional;

// This class handles the Inventory Navigation Menu screen
// This is the main dashboard for the Inventory Officer showing buttons to different screens
public class InventoryNavigationMenuController
{
    // This label shows welcome messages
    // It displays text to the user about what is happening
    @javafx.fxml.FXML
    private Label welcomeLabel;

    // This runs when the screen first loads (we don't need to set up anything here)
    // The @FXML annotation tells JavaFX to call this method automatically
    // This method runs once when the JavaFX controller is created
    // We use it to set up any initial values the screen might need
    @javafx.fxml.FXML
    public void initialize() {
        // No initialization needed for this screen
        // All the buttons are already connected in the FXML layout file
    }

    // This runs when user clicks "Sync Stock" button
    // It opens the Sync Stock screen where inventory can be updated
    // ActionEvent contains information about the button click
    @javafx.fxml.FXML
    public void syncStockOnClick(ActionEvent event) {
        // Print a message to the console to show button was clicked
        // This is helpful for debugging
        System.out.println("Sync Stock Button Clicked! Ready to build the next screen");
        // Change the label to show we are loading
        // This tells the user the program is working
        welcomeLabel.setText("Loading Sync Screen...");

        // Try to load the new screen (we use try-catch for error handling)
        try {
            // Load the Sync Stock screen from the FXML file
            // FXML is an XML file that describes the screen layout
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SyncStockView.fxml"));
            // Create a new scene (screen) from the FXML
            // A Scene is what JavaFX displays to the user
            Scene syncScene = new Scene(fxmlLoader.load());

            // Get the current window
            // This is the window the user is already looking at
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            // Set the title of the window
            // The title appears at the top of the window
            currentStage.setTitle("Sync Digital Stock");
            // Show the new screen
            // setScene() replaces what is shown in the window
            currentStage.setScene(syncScene);
            currentStage.show();
            // Make the window not resizable
            // This prevents the user from changing the window size
            currentStage.setResizable(false);

        } catch (Exception e) {
            // Show error message if screen cannot load
            // This tells the user something went wrong
            welcomeLabel.setText("Error loading Sync Screen! Check Console.");
            // Print the error details
            // This helps us find what went wrong
            e.printStackTrace();
        }
    }

    // This runs when user clicks "Logout" button
    // It shows a confirmation popup and returns to login screen
    // ActionEvent contains information about the button click
    @javafx.fxml.FXML
    public void logoutOnClick(ActionEvent event) {
        // Create a popup to ask for confirmation
        // Alert is a dialog box that pops up on screen
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        // Set the title of the popup
        alert.setTitle("Log Out Confirmation");
        // Don't show a header
        // The header would be extra text above the main message
        alert.setHeaderText(null);
        // Ask the user if they want to logout
        alert.setContentText("Are you sure you want to log out?");

        // Create a Yes button
        // This button allows the user to confirm logout
        ButtonType buttonTypeYes = new ButtonType("Yes");
        // Create a No button
        // This button allows the user to cancel logout
        ButtonType buttonTypeNo = new ButtonType("No");
        // Add both buttons to the popup
        // The user can click either button
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        // Wait for the user to click a button and get their choice
        // showAndWait() stops the program until the user responds
        Optional<ButtonType> result = alert.showAndWait();

        // Print message to console (showing user is logging out)
        System.out.println("user session successfully");

        try {
            // Load the login screen from FXML file
            // FXML file describes what the login screen looks like
            FXMLLoader myFxmlLoader = new FXMLLoader(getClass().getResource("TheView.fxml"));
            // Create a new scene from the FXML
            // This is the login screen we will show
            Scene loginScene = new Scene(myFxmlLoader.load());

            // Get the current window
            // This is the window we will change
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the title
            // The title appears at the top of the window
            currentStage.setTitle("Paper Mill System - Login");
            // Show the login screen
            // setScene() replaces what is shown in the window
            currentStage.setScene(loginScene);
            currentStage.show();
            // Make the window not resizable
            // This prevents the user from changing the window size
            currentStage.setResizable(false);

        }catch (IOException e) {
            // Print error message if login screen cannot load
            // This tells us what went wrong
            System.out.println("Error loading Login Screen! Check your file path.");
            // Print the error details
            e.printStackTrace();
        }
    }

    // This runs when user clicks "Record Delivery" button
    // It opens the screen to record when materials are delivered
    // ActionEvent contains information about the button click
    @javafx.fxml.FXML
    public void recordDeliveryOnClick(ActionEvent actionEvent) {
        try {
            // Load the Record Delivery screen from FXML file
            // FXML describes the screen layout for recording deliveries
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("RecordDeliveryView.fxml"));
            // Create a new scene from the FXML
            // This is the delivery screen we will show
            Scene deliveryScene = new Scene(fxmlLoader.load());

            // Get the current window
            // This is the window we will change
            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            // Set the title
            // The title appears at the top of the window
            currentStage.setTitle("Record Delivery");
            // Show the screen
            // setScene() replaces what is shown in the window
            currentStage.setScene(deliveryScene);
            currentStage.show();
            // Make the window not resizable
            // This prevents the user from changing the window size
            currentStage.setResizable(false);

        } catch (Exception e) {
            // Show error message if screen cannot load
            // This tells the user something went wrong
            welcomeLabel.setText("Error loading Delivery Screen");
            // Print the error details
            // This helps us find what went wrong
            e.printStackTrace();
        }
    }

    // This runs when user clicks "Material Issue" button
    // It opens the screen to record when materials are taken out for use
    // ActionEvent contains information about the button click
    @javafx.fxml.FXML
    public void goToMaterialIssueOnClick(ActionEvent event) {
        try {
            // Load the Material Issue screen from the newly renamed FXML file
            // FXML describes the screen layout for material issues
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MaterialIssueView.fxml"));
            // Create a new scene from the FXML
            // This is the material issue screen we will show
            Scene scene = new Scene(fxmlLoader.load());

            // Get the current window and change the scene to the material issue screen
            // This is the window we will change
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            // Set the title
            // The title appears at the top of the window
            stage.setTitle("Paper Mill - Issue Materials");
            // Show the screen
            // setScene() replaces what is shown in the window
            stage.setScene(scene);
            stage.show();
            // Make the window not resizable
            // This prevents the user from changing the window size
            stage.setResizable(false);

        } catch (Exception e) {
            // Print error message if screen cannot load
            // This tells us something went wrong
            System.out.println("Error loading the Material Issue screen.");
            // Print the error details
            // This helps us find what went wrong
            e.printStackTrace();
        }
    }

    // This runs when user clicks "Record Damaged Material" button
    // It opens the screen to report damaged materials
    // ActionEvent contains information about the button click
    @FXML
    public void recordDamagedMaterialOnClick(ActionEvent actionEvent) {
        try {
            // Load the Damaged Material screen from FXML file
            // FXML describes the screen layout for damaged materials
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DamagedMaterialView.fxml"));
            // Create a new scene from the FXML
            // This is the damaged material screen we will show
            Scene scene = new Scene(fxmlLoader.load());

            // Get the current window
            // This is the window we will change
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            // Set the title
            // The title appears at the top of the window
            stage.setTitle("Paper Mill - Damaged Materials");
            // Show the screen
            // setScene() replaces what is shown in the window
            stage.setScene(scene);
            stage.show();
            // Make the window not resizable
            // This prevents the user from changing the window size
            stage.setResizable(false);

        } catch (Exception e) {
            // Show error message if screen cannot load
            // This tells the user something went wrong
            welcomeLabel.setText("Error loading Damaged Material Screen");
            // Print the error details
            // This helps us find what went wrong
            e.printStackTrace();
        }
    }

    // This runs when user clicks "Stock Alert Report" button
    // It opens the screen to show materials that have low stock
    // ActionEvent contains information about the button click
    @FXML
    public void stockAlertReportOnClick(ActionEvent actionEvent) {
        try {
            // Load the Low Stock Report screen from FXML file
            // FXML describes the screen layout for low stock reports
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LowStockReportView.fxml"));
            // Create a new scene from the FXML
            // This is the low stock report screen we will show
            Scene scene = new Scene(fxmlLoader.load());

            // Get the current window
            // This is the window we will change
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            // Set the title
            // The title appears at the top of the window
            stage.setTitle("Paper Mill - Low Stock Alert Report");
            // Show the screen
            // setScene() replaces what is shown in the window
            stage.setScene(scene);
            stage.show();
            // Make the window not resizable
            // This prevents the user from changing the window size
            stage.setResizable(false);

        } catch (Exception e) {
            // Show error message if screen cannot load
            // This tells the user something went wrong
            welcomeLabel.setText("Error loading Low Stock Report Screen");
            // Print the error details
            // This helps us find what went wrong
            e.printStackTrace();
        }
    }

    // This runs when user clicks "Add Supplier" button
    // It opens the screen to add new suppliers to the system
    // ActionEvent contains information about the button click
    @FXML
    public void addSupplierOnClick(ActionEvent actionEvent) {
        try {
            // Load the Add Supplier screen from FXML file
            // FXML describes the screen layout for adding suppliers
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddSupplierView.fxml"));
            // Create a new scene from the FXML
            // This is the add supplier screen we will show
            Scene scene = new Scene(fxmlLoader.load());

            // Get the current window
            // This is the window we will change
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            // Set the title
            // The title appears at the top of the window
            stage.setTitle("Paper Mill - Add Supplier Dashboard");
            // Show the screen
            // setScene() replaces what is shown in the window
            stage.setScene(scene);
            stage.show();
            // Make the window not resizable
            // This prevents the user from changing the window size
            stage.setResizable(false);

        } catch (Exception e) {
            // Show error message if screen cannot load
            // This tells the user something went wrong
            welcomeLabel.setText("Error loading Add Supplier Screen");
            // Print the error details
            // This helps us find what went wrong
            e.printStackTrace();
        }
    }

    // This runs when user clicks "Valuation Report" button
    // It opens the screen to show the value of all materials in stock
    // ActionEvent contains information about the button click
    @FXML
    public void valuationReportOnClick(ActionEvent actionEvent) throws IOException {
        // Load the Valuation Report screen from FXML file
        // FXML describes the screen layout for valuation reports
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ValuationReportView.fxml"));
        // Create a new scene from the FXML
        // This is the valuation report screen we will show
        Scene scene = new Scene(fxmlLoader.load());

        // Get the current window
        // This is the window we will change
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        // Set the title
        // The title appears at the top of the window
        stage.setTitle("Paper Mill - Valuation Report");
        // Show the screen
        // setScene() replaces what is shown in the window
        stage.setScene(scene);
        stage.show();
        // Make the window not resizable
        // This prevents the user from changing the window size
        stage.setResizable(false);

    }

    // This runs when user clicks "Check Warehouse Inventory" button
    // It opens the screen to view all materials in the warehouse
    // ActionEvent contains information about the button click
    @FXML
    public void checkWarehouseInventoryOnClick(ActionEvent actionEvent) {
        try {
            // Load the Inventory Dashboard screen from FXML file
            // FXML describes the screen layout for viewing warehouse inventory
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InventoryDashboardView.fxml"));
            // Create a new scene from the FXML
            // This is the warehouse inventory screen we will show
            Scene warehouseScene = new Scene(fxmlLoader.load());

            // Get the current window
            // This is the window we will change
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            // Set the title
            // The title appears at the top of the window
            stage.setTitle("Paper Mill - Check Warehouse Inventory");
            // Show the screen
            // setScene() replaces what is shown in the window
            stage.setScene(warehouseScene);
            stage.show();
            // Make the window not resizable
            // This prevents the user from changing the window size
            stage.setResizable(false);

        } catch (Exception e) {
            // Show error message if screen cannot load
            // This tells the user something went wrong
            welcomeLabel.setText("Error loading Check Warehouse Inventory Screen");
            // Print the error details
            // This helps us find what went wrong
            e.printStackTrace();
        }
    }
}

