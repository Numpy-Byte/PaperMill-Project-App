// This package contains all the classes for the paper mill application
package cse213.papermillapp;

// We need this to work with observable data
import javafx.beans.Observable;
// We need this to create observable lists
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
// This is for the pie chart to show data visually
import javafx.scene.chart.PieChart;
// These are UI controls
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
// This is needed to open a new window
import javafx.stage.Stage;

// We need this to handle screen loading errors
import java.io.IOException;
// We need this to wait for user response on popup
import java.util.Optional;

// This class handles the Valuation Report screen
// It shows a pie chart with the value of different materials
public class ValuationReportController
{
    // Pie chart to show valuation data visually
    @javafx.fxml.FXML
    private PieChart valuationPieChart;

    // This runs when the screen first loads
    @javafx.fxml.FXML
    public void initialize() {
        // Create the pie chart slices (parts of the chart)
        // First slice: Wood Pulp worth $45,000
        PieChart.Data slice1 = new PieChart.Data("Wood Pulp ($45k)", 45000);
        // Second slice: Bleach/Chemicals worth $12,000
        PieChart.Data slice2 = new PieChart.Data("Bleach/Chemicals ($12k)", 12000);
        // Third slice: Recycled Paper worth $28,000
        PieChart.Data slice3 = new PieChart.Data("Recycled Paper ($28k)", 28000);
        // Fourth slice: Packaging Boxes worth $8,000
        PieChart.Data slice4 = new PieChart.Data("Packaging Boxes ($8k)", 8000);

        // Put the slices into an observable list
        ObservableList<PieChart.Data> chartData = FXCollections.observableArrayList(slice1, slice2, slice3, slice4);

        // Set the data for the pie chart
        valuationPieChart.setData(chartData);

        // Show labels on the chart pieces
        valuationPieChart.setLabelsVisible(true);
        // Hide the legend (the colored box that explains the chart)
        valuationPieChart.setLegendVisible(false);
    }

    // This runs when user clicks Back button
    // It returns to the inventory menu
    @javafx.fxml.FXML
    public void backtoDashboardOnClick(ActionEvent actionEvent) throws IOException {
        // Load the inventory navigation menu screen from FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InventoryNavigationMenuView.fxml"));
        // Create a new scene from the FXML
        Scene scene = new Scene(fxmlLoader.load());

        // Get the current window
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        // Set the title
        stage.setTitle("Paper Mill - InventoryDashboard");
        // Show the dashboard screen
        stage.setScene(scene);
        stage.show();
        // Make window not resizable
        stage.setResizable(false);


    }

    // This runs when user clicks Logout button
    // It shows confirmation and returns to login screen
    @javafx.fxml.FXML
    public void logoutOnClick(ActionEvent actionEvent) throws IOException {
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

            } catch ( Exception e) {
                // Print error if screen cannot load
                System.out.println("Error returning to login Screen!");
                e.printStackTrace();
            }
        }
    }
}