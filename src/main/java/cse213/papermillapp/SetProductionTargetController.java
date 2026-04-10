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
import java.util.ArrayList;
import java.util.Optional;

// This class handles the Set Production Target screen
// It lets supervisors set production targets for different products
public class SetProductionTargetController {

    // Dropdown to select a product
    @FXML private ComboBox<String> productComboBox;
    // Text box to enter target quantity
    @FXML private TextField targetQuantityField;
    // Label to show status messages
    @FXML private Label statusLabel;

    // Table to show all production targets
    @FXML private TableView<ProductionTarget> targetTable;
    // Column to show product name
    @FXML private TableColumn<ProductionTarget, String> productCol;
    // Column to show target quantity
    @FXML private TableColumn<ProductionTarget, Integer> targetQuantityCol;

    // This is the list to store production targets (ArrayList is preferred by our faculty!)
    private ArrayList<ProductionTarget> targetList = new ArrayList<>();

    // This runs when the screen first loads
    @FXML
    public void initialize() {
        // 1. Link table columns to the ProductionTarget class properties
        productCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
        targetQuantityCol.setCellValueFactory(new PropertyValueFactory<>("targetQuantity"));

        // 2. Load default products into the dropdown
        ObservableList<String> products = FXCollections.observableArrayList(
                "Standard A4 Paper",
                "Premium Cardboard",
                "Newsprint Roll",
                "Tissue Jumbo Roll"
        );
        productComboBox.setItems(products);

        // 3. Load previously saved targets from the binary file
        loadTargetData();
    }

    // This runs when user clicks "Save Target" button
    // It validates input and saves the production target
    @FXML
    public void saveTargetOnClick(ActionEvent actionEvent) {
        // Get the selected product from dropdown
        String selectedProduct = productComboBox.getValue();
        // Get the quantity from text box
        String qtyText = targetQuantityField.getText().trim();

        // Validation: Check if product and quantity are entered
        if (selectedProduct == null || qtyText.isEmpty()) {
            // Show error message in red
            statusLabel.setText("Error: Please select a product and enter a quantity.");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        try {
            // Convert quantity text to a number
            int quantity = Integer.parseInt(qtyText);
            // Check if quantity is positive
            if (quantity <= 0) {
                // Show error message in red
                statusLabel.setText("Error: Quantity must be greater than 0.");
                statusLabel.setStyle("-fx-text-fill: red;");
                return;
            }

            // Create a new production target object
            ProductionTarget newTarget = new ProductionTarget(selectedProduct, quantity);
            // Add to the list
            targetList.add(newTarget);
            // Save to file
            saveTargetData();

            // Refresh the table to show new data
            targetTable.setItems(FXCollections.observableArrayList(targetList));
            targetTable.refresh();

            // Show success message in green
            statusLabel.setText("Success: Target set for " + selectedProduct + ".");
            statusLabel.setStyle("-fx-text-fill: green;");

            // Clear inputs for the next entry
            productComboBox.setValue(null);
            targetQuantityField.clear();

        } catch (NumberFormatException e) {
            // Show error if quantity is not a valid number
            statusLabel.setText("Error: Quantity must be a valid number.");
            statusLabel.setStyle("-fx-text-fill: red;");
        }
    }

    // This loads production targets from the file
    private void loadTargetData() {
        // Clear the list first
        targetList.clear();
        // Get the production target file
        File file = new File("ProductionTarget.bin");
        // If file does not exist, exit (first time running, no file yet)
        if (!file.exists()) return;

        try {
            // Open the file for reading
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            // Read all targets from the file
            while (true) {
                try {
                    // Read one production target
                    ProductionTarget pt = (ProductionTarget) ois.readObject();
                    // Add to the list
                    targetList.add(pt);
                } catch (EOFException | ClassNotFoundException e) {
                    // Stop when we reach the end
                    break;
                }
            }
        } catch (IOException e) {
            // Print error if something goes wrong
            e.printStackTrace();
        }

        // Show the targets in the table
        targetTable.setItems(FXCollections.observableArrayList(targetList));
    }

    // This saves production targets to the file
    private void saveTargetData() {
        try (FileOutputStream fos = new FileOutputStream("ProductionTarget.bin");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            // Write each target to the file
            for (ProductionTarget pt : targetList) {
                oos.writeObject(pt);
            }
        } catch (Exception e) {
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