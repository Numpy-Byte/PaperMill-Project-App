package cse213.papermillapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class AddSupplierController
{
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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InventoryDashboardView.fxml"));
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
}