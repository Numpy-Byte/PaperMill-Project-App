// This package contains all the classes for the paper mill application
package cse213.papermillapp;

// We need this to react to changes in data
import javafx.beans.Observable;
// We need this to work with lists that can update automatically in the UI
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
// We need this to handle button clicks
import javafx.event.ActionEvent;
// This is needed to load the screen layouts
import javafx.fxml.FXMLLoader;
// This is needed to work with screen elements
import javafx.scene.Node;
// This is needed to draw the screen
import javafx.scene.Scene;
// These are UI controls like buttons and labels
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
// This is needed to open a new window
import javafx.stage.Stage;

// We need these to read files
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Optional;

// This class handles the Inventory Dashboard screen
// It shows a table of all materials in the warehouse
public class InventoryDashboardController
{
    // Table to show all materials
    @javafx.fxml.FXML
    private TableView<Material> inventoryTable;
    // Column to show material ID
    @javafx.fxml.FXML
    private TableColumn<Material, String> idCol;
    // Column to show material name
    @javafx.fxml.FXML
    private TableColumn<Material, String> nameCol;
    // Column to show quantity in stock
    @javafx.fxml.FXML
    private TableColumn<Material, Integer> quantityCol;
    // Column to show category
    @javafx.fxml.FXML
    private TableColumn<Material, String> categoryCol;
    // Text box to search for materials
    @javafx.fxml.FXML
    private TextField searchTextField;

    // This runs when the screen first loads
    @javafx.fxml.FXML
    public void initialize() {
        // Set up the ID column to get data from Material
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        // Set up the name column to get data from Material
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        // Set up the category column to get data from Material
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        // Set up the quantity column to get data from Material
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("currentStock"));

        // Load the material data into the table
        loadInventoryData();
    }

    // This loads material data from the file
    private void loadInventoryData() {
        // Create a list to hold the materials
        ArrayList<Material> materialList = new ArrayList<>();
        // Get the material file
        File file = new File("Material.bin");

        // Check if file exists
        if(!file.exists()) {
            // Print message if file is missing
            System.out.println("Material.bin not found! Run DummyDataGenerator first.");
            return;
        }

        try {
            // Open the file for reading
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            // Read all materials from the file
            while (true) {
                try {
                    // Read one material
                    Material m = (Material) ois.readObject();
                    materialList.add(m);
                } catch (EOFException e) {
                    // Stop when we reach the end
                    break;
                }
            }

            // Close the file
            ois.close();

            // Convert to a list that can update the UI automatically
            ObservableList<Material> observableList = FXCollections.observableList(materialList);
            // Show the materials in the table
            inventoryTable.setItems(observableList);

        } catch (Exception e) {
            // Print error if something goes wrong
            System.out.println("Error: Loading Material.bin");
            e.printStackTrace();
        }
    }

    // This runs when user clicks Back button
    @javafx.fxml.FXML
    public void backtoDashboardOnClick(ActionEvent actionEvent) {
        try {
            // Load the inventory menu screen
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InventoryNavigationMenuView.fxml"));
            Scene dashboardScene = new Scene(fxmlLoader.load());

            // Get the current window
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            // Set the title
            stage.setTitle("Paper Mill - Inventory Officer Dashboard");
            // Show the dashboard
            stage.setScene(dashboardScene);
            stage.show();
            // Make window not resizable
            stage.setResizable(false);

        } catch (Exception e) {
            // Print error if something goes wrong
            System.out.println("Error loading Dashboard Screen! Check Console");
            e.printStackTrace();
        }
    }

    // This runs when user clicks Logout button
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
                // Load the login screen
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TheView.fxml"));
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
                // Print error if something goes wrong
                System.out.println("Error returning to login Screen!");
                e.printStackTrace();
            }
        }
    }

    // This runs when user clicks Filter button
    @javafx.fxml.FXML
    public void filterInventoryOnClick(ActionEvent actionEvent) {
        // Get the search text
        String searchText = searchTextField.getText().trim().toLowerCase();
        // Create a list to hold the filtered results
        ArrayList<Material> filteredList = new ArrayList<>();
        // Get the material file
        File file = new File("Material.bin");

        // If file does not exist, do nothing
        if (!file.exists()) return;

        try {
            // Open the file for reading
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            // Read all materials from the file
            while (true) {
                try {
                    // Read one material
                    Material m = (Material) ois.readObject();

                    // Check if the material matches the search
                    if (searchText.isEmpty() || m.getName().toLowerCase().contains(searchText) ||
                            m.getId().toLowerCase().contains(searchText)) {
                        // Add to the filtered list
                        filteredList.add(m);
                    }
                } catch (EOFException e) {
                    // Stop when we reach the end
                    break;
                }
            }

            // Close the file
            ois.close();

            // Show the filtered results in the table
            inventoryTable.setItems(FXCollections.observableArrayList(filteredList));

        } catch (Exception e) {
            // Print error if something goes wrong
            System.out.println("Error: filtering inventory!");
            e.printStackTrace();
        }
    }
}