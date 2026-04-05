package cse213.papermillapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Optional;

public class AddSupplierController {
    @javafx.fxml.FXML
    private TextField idTextField;
    @javafx.fxml.FXML
    private TextField materialTextField;
    @javafx.fxml.FXML
    private TextField nameTextField;
    @javafx.fxml.FXML
    private TextField contactTextField;
    @javafx.fxml.FXML
    private Label statusLabel;

    @javafx.fxml.FXML
    public void initialize() {
    }

    @javafx.fxml.FXML
    public void saveSupplierOnClick(ActionEvent actionEvent) {
        String id = idTextField.getText().trim();
        String name = nameTextField.getText().trim();
        String contact = contactTextField.getText().trim();
        String material = materialTextField.getText().trim();

        if (id.isEmpty() || name.isEmpty() || contact.isEmpty() || material.isEmpty()) {
            statusLabel.setText("Error: All field must be fillerd out!");
            statusLabel.setStyle("-fx-text-fill: red");
            return;
        }

        Supplier newSupplier = new Supplier(id, name, contact, material);

        File f = new File("Supplier.bin");

        try {
            FileOutputStream fos = new FileOutputStream(f, true);
            ObjectOutputStream oos;

            if (f.exists() && f.length() > 0) {
                oos = new AppendableObjectOutputStream(fos);
            } else {
                oos = new ObjectOutputStream(fos);
            }

            oos.writeObject(newSupplier);
            oos.close();

            statusLabel.setText("Supploer saved successfully!");
            statusLabel.setStyle("-fx-text-fill: green");

            idTextField.clear();
            nameTextField.clear();
            contactTextField.clear();
            materialTextField.clear();

        } catch (Exception e) {
            statusLabel.setText("Error saving supplier! Check console");
            statusLabel.setStyle("-fx-text-fill: red");
            e.printStackTrace();

        }
    }

    @javafx.fxml.FXML
    public void backtoDashboardOnClick(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InventoryNavigationMenuView.fxml"));
            Scene dashboardScene = new Scene(fxmlLoader.load());

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Dashboard Scene");
            stage.setScene(dashboardScene);
            stage.show();
            stage.setResizable(false);

        } catch (Exception e) {
            statusLabel.setText("Error loading Dashboard Screen! Check Console");
            e.printStackTrace();
        }
    }

    @javafx.fxml.FXML
    public void logoutOnClick(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Log out Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to log out?");

        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == buttonTypeYes) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TheView.fxml"));
                Scene loginScene = new Scene(fxmlLoader.load());

                Stage currentStage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
                currentStage.setTitle("Paper Mill System - Login");
                currentStage.setScene(loginScene);
                currentStage.show();
                currentStage.setResizable(false);

            } catch (Exception e) {
                System.out.println("Error returning to login Screen!");
                e.printStackTrace();
            }
        }
    }
}