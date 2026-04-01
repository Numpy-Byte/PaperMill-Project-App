package cse213.papermillapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

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

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SyncStockView.fxml"));
            Scene syncScene = new Scene(fxmlLoader.load());

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setTitle("Sync Digital Stock");
            currentStage.setScene(syncScene);
            currentStage.show();
        } catch (Exception e) {
            welcomeLabel.setText("Error loading Sync Screen! Check Console.");
            e.printStackTrace();
        }
    }

    @javafx.fxml.FXML
    public void logoutOnClick(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Log Out Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to log out?");

        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        Optional<ButtonType> result = alert.showAndWait();

        System.out.println("user session successfully");

        try {
            FXMLLoader myFxmlLoader = new FXMLLoader(getClass().getResource("TheView.fxml"));
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

    @javafx.fxml.FXML
    public void recordDeliveryOnClick(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("RecordDeliveryView.fxml"));
            Scene deliveryScene = new Scene(fxmlLoader.load());

            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            currentStage.setTitle("Record Delivery");
            currentStage.setScene(deliveryScene);
            currentStage.show();
        } catch (Exception e) {
            welcomeLabel.setText("Error loading Delivery Screen");
            e.printStackTrace();
        }
    }

    @javafx.fxml.FXML
    public void goToMaterialIssueOnClick(ActionEvent event) {
        try {
            // Pointing to the newly renamed FXML file!
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MaterialIssueView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            // Getting the current window (Stage) and changing the scene
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Paper Mill - Issue Materials");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            System.out.println("Error loading the Material Issue screen.");
            e.printStackTrace();
        }
    }

    @FXML
    public void recordDamagedMaterialOnClick(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DamagedMaterialView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Paper Mill - Damaged Materials");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            welcomeLabel.setText("Error loading Damaged Material Screen");
            e.printStackTrace();
        }
    }
}