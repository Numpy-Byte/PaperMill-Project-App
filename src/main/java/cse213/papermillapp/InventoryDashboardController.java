package cse213.papermillapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class InventoryDashboardController
{
    @javafx.fxml.FXML
    private Label welcomeLabel;

    @javafx.fxml.FXML
    public void initialize() {
    }

    @javafx.fxml.FXML
    public void syncStockOnClick(ActionEvent event) {
        System.out.println("Sync Stock Button Clicked! Ready to build the next screen");
        welcomeLabel.setText("Loading Sync Screen...");
    }

    @javafx.fxml.FXML
    public void logoutOnClick(ActionEvent event) {
        try {
            FXMLLoader myFxmlLoader = new FXMLLoader(getClass().getResource("LoginView.fxml"));
            Scene loginScene = new Scene(myFxmlLoader.load());

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            currentStage.setTitle("Paper Mill System - Login");
            currentStage.setScene(loginScene);
            currentStage.show();

        }catch (IOException e) {
            System.out.println("Error loading Login Screen! Check your file path.");
            e.printStackTrace();
        }
    }
}