package cse213.papermillapp;

import javafx.collections.FXCollections;
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

public class RecordProductionOutputController {
    @javafx.fxml.FXML
    private TableView productionTable;
    @javafx.fxml.FXML
    private TableColumn quantityCol;
    @javafx.fxml.FXML
    private TableColumn machineIdCol;
    @javafx.fxml.FXML
    private Label statusLabel;

    private ArrayList<ProductionRecord> productionList = new ArrayList<>();
    @FXML
    private TextField machineIdTextField;
    @FXML
    private TextField quantityTextField;

    @javafx.fxml.FXML
    public void initialize() {
        machineIdCol.setCellValueFactory(new PropertyValueFactory<>("machineId"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("ProducedQuantity"));

        loadProductionData();
    }

    @javafx.fxml.FXML
    public void saveProductionOnClick(ActionEvent actionEvent) {
        String machineId = machineIdTextField.getText().trim();
        String qtyText = quantityTextField.getText().trim();

        if (machineId.isEmpty() || qtyText.isEmpty()) {
            statusLabel.setText("Validation Error: All fields must be filled!");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        try {
            int quantity = Integer.parseInt(qtyText);
            if (quantity <= 0) {
                statusLabel.setText("Validation Error: Quantity must be greater than 0.");
                statusLabel.setStyle("-fx-text-fill: red;");
                return;
            }

            File machineFile = new File("Machine.bin");
            boolean machineFound = false;

            if (machineFile.exists()) {
                try {
                    FileInputStream fis = new FileInputStream(machineFile);
                    ObjectInputStream ois = new ObjectInputStream(fis);

                    while (true) {
                        try {
                            Machine m = (Machine) ois.readObject();
                            if (m.getId().equalsIgnoreCase(machineId)) {
                                machineFound = true;
                                break;
                            }
                        } catch (EOFException e) {
                            break;
                        } catch (ClassNotFoundException e) {
                            System.out.println("Class not found error.");
                            break;
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Error reading machine file.");
                }
            }

            if (!machineFound) {
                statusLabel.setText("Error: Machine ID '" + machineId + "' not found in factory.");
                statusLabel.setStyle("-fx-text-fill: red;");
                return;
            }

            ProductionRecord newRecord = new ProductionRecord(machineId, quantity);
            productionList.add(newRecord);
            saveDataToFile();

            productionTable.setItems(FXCollections.observableArrayList(productionList));
            statusLabel.setText("Success! " + quantity + " units recorded for " + machineId.toUpperCase());
            statusLabel.setStyle("-fx-text-fill: green;");

            machineIdTextField.clear();
            quantityTextField.clear();

        } catch (NumberFormatException e) {
            statusLabel.setText("Validation Error: Quantity must be a valid number!");
            statusLabel.setStyle("-fx-text-fill: red;");
        } catch (Exception e) {
            statusLabel.setText("System Error: Could not save record.");
            statusLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }

    private  void loadProductionData() {
        productionList.clear();
        File file = new File("ProductionRecord.bin");
        if (!file.exists())
            return;

        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            while (true) {
                try {
                    ProductionRecord pr = (ProductionRecord) ois.readObject();
                    productionList.add(pr);
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        productionTable.setItems(FXCollections.observableArrayList(productionList));
    }

    private void saveDataToFile() {
        try {
            FileOutputStream fos = new FileOutputStream("ProductionRecord.bin");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            for (ProductionRecord pr : productionList) {
                oos.writeObject(pr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @javafx.fxml.FXML
    public void backToDashboardOnClick(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader((getClass().getResource("ProductionDashboardView.fxml")));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Production Dashboard");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            statusLabel.setText("Error loading Dashboard!");
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

        if(result.isPresent() && result.get() == buttonTypeYes) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TheView.fxml"));
                Scene loginScene = new Scene(fxmlLoader.load());

                Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                currentStage.setTitle("Login");
                currentStage.setScene(loginScene);
                currentStage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
