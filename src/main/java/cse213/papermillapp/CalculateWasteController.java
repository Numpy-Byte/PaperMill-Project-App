package cse213.papermillapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Optional;

public class CalculateWasteController {
    @javafx.fxml.FXML
    private TableColumn<WasteReport, String> machineCol;
    @javafx.fxml.FXML
    private TableColumn<WasteReport, Double> wasteCol;
    @javafx.fxml.FXML
    private TableView<WasteReport> wasteTable;
    @javafx.fxml.FXML
    private TableColumn<WasteReport, Integer> producedCol;
    @javafx.fxml.FXML
    private Button CalculateBtn;
    @javafx.fxml.FXML
    private Label statusLabel;
    @javafx.fxml.FXML
    private TableColumn<WasteReport, Integer> rawCol;

    @javafx.fxml.FXML
    public void initialize() {
        machineCol.setCellValueFactory(new PropertyValueFactory<>("machineId"));
        rawCol.setCellValueFactory(new PropertyValueFactory<>("rawMaterialUsed"));
        producedCol.setCellValueFactory(new PropertyValueFactory<>("paperProduced"));
        wasteCol.setCellValueFactory(new PropertyValueFactory<>("wastePercentage"));
    }

    @javafx.fxml.FXML
    public void CalculateWasteOnClick(ActionEvent actionEvent) {
        ArrayList<RawMaterialUsage> rawData = new ArrayList<>();
        ArrayList<ProductionRecord> prodData = new ArrayList<>();
        ObservableList<WasteReport> reportList = FXCollections.observableArrayList();

        File rawFile = new File("RawMaterialUsage.bin");
        if (rawFile.exists()) {
            try {
                FileInputStream fis = new FileInputStream(rawFile);
                ObjectInputStream ois = new ObjectInputStream(fis);

                while (true) {
                    try {
                        rawData.add((RawMaterialUsage) ois.readObject());
                    } catch (EOFException | ClassNotFoundException e) {
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            statusLabel.setText("ERROR: Raw material data missing. Run Dummy Generator.");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        File prodFile = new File("ProductionRecord.bin");
        if (prodFile.exists()) {
            try {
                FileInputStream fis = new FileInputStream(prodFile);
                ObjectInputStream ois = new ObjectInputStream(fis);

                while (true) {
                    try {
                        prodData.add((ProductionRecord) ois.readObject());
                    } catch (EOFException | ClassNotFoundException e) {
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for (RawMaterialUsage raw : rawData) {
            String currentMachine = raw.getMachineId();
            int rawUsed = raw.getAmountUsed();
            int totalProduced = 0;

            for (ProductionRecord prod : prodData) {
                if (prod.getMachineId().equals(currentMachine)) {
                    totalProduced += prod.getProducedQuantity();
                }
            }

            double wastePct = 0.0;
            if (rawUsed > 0 && rawUsed >= totalProduced) {
                int wasteAmount = rawUsed - totalProduced;
                wastePct = ((double) wasteAmount / rawUsed) * 100;
                wastePct = Math.round(wastePct * 100.0) / 100.0;
            }

            reportList.add(new WasteReport(currentMachine, rawUsed, totalProduced, wastePct));
        }

        wasteTable.setItems(reportList);
        statusLabel.setText("Success: Waste report generated!");
        statusLabel.setText("-fx-text-fill: green;");
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

    @javafx.fxml.FXML
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
}