package cse213.papermillapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;

public class DamagedMaterialController
{
    @javafx.fxml.FXML
    private TextField materialIdTextField;
    @javafx.fxml.FXML
    private TextField damagedQuantityTextField;
    @javafx.fxml.FXML
    private Label statusLabel;

    private ArrayList<String> validMatrials = new ArrayList<>(Arrays.asList("MAT-01", "MAT-02", "MAT-03"));

    @javafx.fxml.FXML
    public void initialize() {
    }

    @javafx.fxml.FXML
    public void logoutOnClick(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TheView.fxml"));
            Scene loginScene = new Scene(fxmlLoader.load());

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Login Screen");
            stage.setScene(loginScene);
            stage.show();

        } catch (Exception e) {
            statusLabel.setText("Error loading Login Screen! Check Console");
            e.printStackTrace();

        }
    }

    @javafx.fxml.FXML
    public void removeDamagedStockOnClick(ActionEvent actionEvent) {
            String materialId = materialIdTextField.getText();
            String damagedQuantity = damagedQuantityTextField.getText();

            if (materialId.isEmpty() || damagedQuantity.isEmpty()) {
                statusLabel.setText("Please fill in all the fields");
                statusLabel.setStyle("-fx-text-fill: red;");
                return;
            }

            if (!validMatrials.contains(materialId)) {
                statusLabel.setText("Material ID is not valid");
                statusLabel.setStyle("-fx-text-fill: red;");
                return;
            }

            try {
                int DamagedQuantity = Integer.parseInt(damagedQuantity);

                int currentStock = 500;

                if(DamagedQuantity > currentStock) {
                    statusLabel.setText("Error: Damaged Quantity Exceeds the Current Stock");
                    statusLabel.setStyle("-fx-text-fill: red;");
                } else {
                    int newStock = currentStock - DamagedQuantity;
                    statusLabel.setText("Success: Removed " + DamagedQuantity + " units. New Stock: " + newStock);
                    statusLabel.setStyle("-fx-text-fill: darkorange;");
                }
            } catch (NumberFormatException e) {
                statusLabel.setText("Error: Quantity must be a number.");
                statusLabel.setStyle("-fx-text-fill: red;");
            }
    }

    @javafx.fxml.FXML
    public void backToDashboardOnClick(ActionEvent actionEvent) {
       try {
           FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InventoryNavigationMenuView.fxml"));
           Scene dashboardScene = new Scene(fxmlLoader.load());

           Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
           stage.setTitle("Dashboard Scene");
           stage.setScene(dashboardScene);
           stage.show();

       } catch (Exception e) {
           statusLabel.setText("Error loading Dashboard Screen! Check Console");
           e.printStackTrace();
       }
    }
}