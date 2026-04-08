package cse213.papermillapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Optional;

public class SetProductionTargetController {

    @FXML private ComboBox<String> productComboBox;
    @FXML private TextField targetQuantityField;
    @FXML private Label statusLabel;

    @FXML private TableView<ProductionTarget> targetTable;
    @FXML private TableColumn<ProductionTarget, String> productCol;
    @FXML private TableColumn<ProductionTarget, Integer> targetQuantityCol;

    // The ArrayList just like your faculty prefers!
    private ArrayList<ProductionTarget> targetList = new ArrayList<>();

    @FXML
    public void initialize() {
        // 1. Link table columns to the ProductionTarget class
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

        // 3. Load previously saved targets from the .bin file
        loadTargetData();
    }

    @FXML
    public void saveTargetOnClick(ActionEvent actionEvent) {
        String selectedProduct = productComboBox.getValue();
        String qtyText = targetQuantityField.getText().trim();

        // Validation: Empty checks
        if (selectedProduct == null || qtyText.isEmpty()) {
            statusLabel.setText("Error: Please select a product and enter a quantity.");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        try {
            // Validation: Number check
            int quantity = Integer.parseInt(qtyText);
            if (quantity <= 0) {
                statusLabel.setText("Error: Quantity must be greater than 0.");
                statusLabel.setStyle("-fx-text-fill: red;");
                return;
            }

            // Create target, add to ArrayList, and save!
            ProductionTarget newTarget = new ProductionTarget(selectedProduct, quantity);
            targetList.add(newTarget);
            saveTargetData();

            // Refresh table
            targetTable.setItems(FXCollections.observableArrayList(targetList));
            targetTable.refresh();

            statusLabel.setText("Success: Target set for " + selectedProduct + ".");
            statusLabel.setStyle("-fx-text-fill: green;");

            // Clear inputs
            productComboBox.setValue(null);
            targetQuantityField.clear();

        } catch (NumberFormatException e) {
            statusLabel.setText("Error: Quantity must be a valid number.");
            statusLabel.setStyle("-fx-text-fill: red;");
        }
    }

    private void loadTargetData() {
        targetList.clear();
        File file = new File("ProductionTarget.bin");
        if (!file.exists()) return; // First time running, no file yet

        try {
        FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis);

            while (true) {
                try {
                    ProductionTarget pt = (ProductionTarget) ois.readObject();
                    targetList.add(pt);
                } catch (EOFException | ClassNotFoundException e) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Show data in the table
        targetTable.setItems(FXCollections.observableArrayList(targetList));
    }

    private void saveTargetData() {
        try (FileOutputStream fos = new FileOutputStream("ProductionTarget.bin");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            for (ProductionTarget pt : targetList) {
                oos.writeObject(pt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void backToDashboardOnClick(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ProductionDashboardView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Production Supervisor Dashboard");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
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

                Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                currentStage.setTitle("Paper Mill System - Login");
                currentStage.setScene(loginScene);
                currentStage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}