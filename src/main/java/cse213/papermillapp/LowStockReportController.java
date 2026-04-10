// This package contains all the classes for the paper mill application
package cse213.papermillapp;

// We need this to create observable lists that update the UI automatically
import javafx.collections.FXCollections;
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

// This class handles the Low Stock Report screen
// It shows materials that are running low in inventory
public class LowStockReportController
{
    // Table to show low stock items
    @javafx.fxml.FXML
    private TableView<Material> lowStockTableView;
    // Column to show material name
    @javafx.fxml.FXML
    private TableColumn<Material, String> nameColumn;
    // Column to show minimum threshold
    @javafx.fxml.FXML
    private TableColumn<Material, Integer> thresholdColumn;
    // Column to show current stock
    @javafx.fxml.FXML
    private TableColumn<Material, Integer> stockColumn;
    // Label to show status messages
    @javafx.fxml.FXML
    private Label statusLabel;
    // Column to show material ID
    @javafx.fxml.FXML
    private TableColumn<Material, String> idColumn;
    // Text box to search for materials
    @javafx.fxml.FXML
    private TextField searchTextField;

    // This is the master list to store low stock items
    private ArrayList<Material> masterLowStockList = new  ArrayList<>();

    // This runs when the screen first loads
    @javafx.fxml.FXML
    public void initialize() {
        // Link table columns to the Material class properties
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("currentStock"));
        thresholdColumn.setCellValueFactory(new PropertyValueFactory<>("minThreshold"));

        // Load the low stock data
        loadLowStockData();


    }

    // This loads materials that are low in stock
    private void loadLowStockData() {
        // Clear the list first
        masterLowStockList.clear();
        try{
            // Get the material file
            File file = new File("Material.bin");
            // If file does not exist, show error
            if(!file.exists()) {
                statusLabel.setText("Error: Material.bin not found!");
                return;
            }

            // Open the file for reading
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            // Read all materials from the file
            while (true) {
                try {
                    // Read one material
                    Material m = (Material) ois.readObject();

                    // Check if current stock is below the minimum threshold
                    if(m.getCurrentStock() < m.getMinThreshold()) {
                        // Add to low stock list
                        masterLowStockList.add(m);
                    }
                } catch (EOFException e) {
                    // Stop when we reach the end
                    break;
                }
            }

            // Close the file
            ois.close();

            // Show the low stock items in the table
            lowStockTableView.setItems(FXCollections.observableArrayList(masterLowStockList));

            // Check if list is empty (healthy inventory)
            if(masterLowStockList.isEmpty()) {
                // Show green message for healthy inventory
                statusLabel.setText("Inventory is Healthy. No low stock items.");
                statusLabel.setStyle("-fx-text-fill: green;");
            } else {
                // Show red warning with count
                statusLabel.setText("Warning: " + masterLowStockList.size() + " items are low in stock!");
                statusLabel.setStyle("-fx-text-fill: red;");
            }
        } catch (Exception e) {
            // Show error if something goes wrong
            statusLabel.setText("Error reading Material.bin file! Check Console.");
            e.printStackTrace();

        }
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
                Stage currentStage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
                // Set the title
                currentStage.setTitle("Paper Mill System - Login");
                // Show the login screen
                currentStage.setScene(loginScene);
                currentStage.show();
                // Make window not resizable
                currentStage.setResizable(false);
            } catch (Exception e) {
                // Show error if screen cannot load
                statusLabel.setText("Error returning to login Screen!");
                e.printStackTrace();
            }
        }
    }

    // This runs when user clicks "Load Report" button
    // It reloads the low stock report
    @javafx.fxml.FXML
    public void loadReportOnClick(ActionEvent actionEvent) {
        // Clear the list first
        masterLowStockList.clear();
        try {
            // Get the material file
            File file = new File("Material.bin");
            // If file does not exist, show error
            if(!file.exists()) {
                statusLabel.setText("Error: Material.bin not found!");
                return;
            }

            // Open the file for reading
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            // Read all materials from the file
            while (true) {
                try {
                    // Read one material
                    Material m = (Material) ois.readObject();

                    // Check if current stock is below minimum threshold
                    if (m.getCurrentStock() < m.getMinThreshold()) {
                       // Add to low stock list
                       masterLowStockList.add(m);
                    }
                } catch (EOFException e) {
                    // Stop when we reach the end
                    break;
                }
            }

            // Close the file
            ois.close();

            // Check if list is empty
            if (masterLowStockList.isEmpty()) {
                // Show green message
                statusLabel.setText("Inventory is Healthy");
                statusLabel.setStyle("-fx-text-fill: green;");
            } else {
                // Show red warning with count
                statusLabel.setText("Warning: " + masterLowStockList.size() + " items are low!");
                statusLabel.setStyle("-fx-text-fill: red;");
            }
        } catch (Exception e) {
            // Show error if something goes wrong
            statusLabel.setText("Error reading file! Check Console.");
            e.printStackTrace();

        }
    }

    // This runs when user clicks Back button
    // It returns to the inventory menu
    @javafx.fxml.FXML
    public void backToDashboardOnClick(ActionEvent actionEvent) {
        try {
            // Load the inventory navigation menu screen from FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InventoryNavigationMenuView.fxml"));
            // Create a new scene from the FXML
            Scene dashboardScene = new Scene(fxmlLoader.load());

            // Get the current window
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            // Set the title
            stage.setTitle("Dashboard Screen");
            // Show the dashboard screen
            stage.setScene(dashboardScene);
            stage.show();
            // Make window not resizable
            stage.setResizable(false);


        } catch (Exception e) {
            // Show error if screen cannot load
            statusLabel.setText("Error loading Dashboard Screen! Check Console");
            e.printStackTrace();
        }

    }

    // This runs when user clicks Filter button
    // It filters the low stock report by search text
    @javafx.fxml.FXML
    public void filterReportOnClick(ActionEvent actionEvent) {
        // Get the search text
        String searchText = searchTextField.getText().trim().toLowerCase();
        // Create a list to hold filtered results
        ArrayList<Material> filteredList = new ArrayList<>();

        try {
            // Get the material file
            File file = new File("Material.bin");
            // If file does not exist, exit
            if (!file.exists()) return;

            // Open the file for reading
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            // Read all materials from the file
            while (true) {
                try {
                    // Read one material
                    Material m = (Material) ois.readObject();
                    // Check if it's low stock
                    boolean isLowStock = m.getCurrentStock() < m.getMinThreshold();
                    // Check if it matches the search text
                    boolean matchesSearch = m.getName().toLowerCase().contains(searchText) ||
                            m.getId().toLowerCase().contains(searchText);

                    // Add if it matches both conditions
                    if (isLowStock && matchesSearch) {
                        filteredList.add(m);
                    }
                } catch (EOFException e) {
                    // Stop when we reach the end
                    break;
                }
            }
            // Close the file
            ois.close();

            // Show filtered results in the table
            lowStockTableView.setItems(FXCollections.observableArrayList(filteredList));

            // Show message based on results
            if(filteredList.isEmpty()) {
                statusLabel.setText("No low-stock items match '" + searchText + "'");
            } else {
                statusLabel.setText("Found " + filteredList.size() + " low-stock matches.");
            }

        } catch (Exception e) {
            // Show error if something goes wrong
            statusLabel.setText("Filter Error! Check Console.");
            e.printStackTrace();
        }
    }
}