// This package contains all the classes for the paper mill application
package cse213.papermillapp;

// We need this to handle button clicks
import javafx.event.ActionEvent;
// This is needed to work with UI elements from the screen
import javafx.fxml.FXML;
// This is needed to load other screens
import javafx.fxml.FXMLLoader;
// This is needed to draw the screen
import javafx.scene.Scene;
// This is for error messages
import javafx.scene.control.Label;
// This is for password input (shows dots instead of text)
import javafx.scene.control.PasswordField;
// This is for text input
import javafx.scene.control.TextField;
// This is needed to open a new window
import javafx.stage.Stage;

// We need these to read files
import java.io.*;

// This class handles the Login screen
// It verifies user credentials and directs them to their dashboard
public class LoginController {

    // Text box to enter user ID
    @FXML
    private TextField idTextField;
    // Label to show error messages
    @FXML
    private Label errorLabel;
    // Password field to enter password
    @FXML
    private PasswordField pwTextField;

    // This runs when user clicks Login button
    @FXML
    public void loginButtonOnClick(ActionEvent actionEvent) {
        // Get the text from input boxes
        String userId = idTextField.getText().trim();
        String userPassword = pwTextField.getText().trim();

        // Check if any field is empty
        if (userId.isEmpty() || userPassword.isEmpty()) {
            errorLabel.setText("Error: All fields must be filled out!");
            errorLabel.setStyle("-fx-text-fill: red");
            return;
        }

        // Check if user ID is valid format (only digits and 4 or 7 characters)
        if(!userId.matches("\\d+") || (userId.length() != 4 && userId.length() != 7)) {
            errorLabel.setText("Error: Username must be 4 or 7 digits!");
            errorLabel.setStyle("-fx-text-fill: red");
            return;
        }

        try {
            // Convert user ID to a number
            int typeId = Integer.parseInt(userId);
            // Flag to track if login was successful
            boolean loginSuccess = false;
            // To store the user's role
            String detectedRole = "";

            // Open the login credentials file
            File file = new File("LoginCredentials.bin");
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            LoginCredentials credentials;

            // Read through all login records
            while (true) {
                try {
                    credentials = (LoginCredentials) ois.readObject();
                    // Check if ID and password match
                    if (credentials.getUserId() == typeId && credentials.getPw().equals(userPassword)) {
                        loginSuccess = true;
                        detectedRole = credentials.getUserType();
                        break;
                    }
                } catch (EOFException e) {
                    // Stop when we reach the end of file
                    break;
                }
            }

            // Close the file
            ois.close();

            // If login was successful, go to the right dashboard
            if(loginSuccess) {
                // This will hold the filename of the dashboard to show
                String fxmlFilename = "";

                // Print the role for debugging
                System.out.println("The ROLE FOUND IN FILE IS: [" + detectedRole + "] ");

                // Choose the right dashboard based on role
                switch (detectedRole.trim()) {
                    case "Inventory Officer":
                        fxmlFilename = "InventoryNavigationMenuView.fxml";
                        break;
                    case "Production Supervisor":
                        fxmlFilename = "ProductionDashboardView.fxml";
                        break;
                    case "Accountant":
                    case "HR Officer":
                    case "Plant Manager":
                    case "Maintenance Coordinator":
                    case "Customer":
                    case "Sales Representative":
                        fxmlFilename = "DummyDashboardView.fxml";
                        break;
                    default:
                        errorLabel.setText("Dashboard for this role is missing!");
                        errorLabel.setStyle("-fx-text-fill: red");
                        return;
                }

                // Load the dashboard screen
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFilename));
                Scene dashboardScene = new Scene(fxmlLoader.load());

                // Get the current window
                Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
                // Set the title
                stage.setTitle("Paper Mill System - " + detectedRole + " Dashboard");
                // Show the dashboard
                stage.setScene(dashboardScene);
                stage.show();

            } else {
                // Show error if login failed
                errorLabel.setText("Incorrect ID or Password");
                errorLabel.setStyle("-fx-text-fill: red");
            }
    } catch (Exception e) {
            // Show error if something goes wrong
            errorLabel.setText("There has been an Error! plese Check Console!");
            errorLabel.setStyle("-fx-text-fill: red");
            e.printStackTrace();
        }
    }
}