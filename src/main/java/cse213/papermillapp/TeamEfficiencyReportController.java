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

public class TeamEfficiencyReportController {

    @FXML private Button generateReportBtn;
    @FXML private Label statusLabel;

    @FXML private TableView<EfficiencyReport> efficiencyTable;
    @FXML private TableColumn<EfficiencyReport, String> workerCol;
    @FXML private TableColumn<EfficiencyReport, String> machineCol;
    @FXML private TableColumn<EfficiencyReport, Integer> outputCol;
    @FXML private TableColumn<EfficiencyReport, String> ratingCol;

    @FXML
    public void initialize() {
        workerCol.setCellValueFactory(new PropertyValueFactory<>("workerId"));
        machineCol.setCellValueFactory(new PropertyValueFactory<>("machineAssigned"));
        outputCol.setCellValueFactory(new PropertyValueFactory<>("tasksHandled"));
        ratingCol.setCellValueFactory(new PropertyValueFactory<>("efficiencyRating"));
    }

    @FXML
    public void generateReportOnClick(ActionEvent actionEvent) {
        ObservableList<EfficiencyReport> reportList = FXCollections.observableArrayList();
        File file = new File("EfficiencyData.bin");

        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                while (true) {
                    try {
                        reportList.add((EfficiencyReport) ois.readObject());
                    } catch (EOFException | ClassNotFoundException e) { break; }
                }
            } catch (IOException e) { e.printStackTrace(); }

            efficiencyTable.setItems(reportList);
            statusLabel.setText("Success: Team efficiency report generated!");
            statusLabel.setStyle("-fx-text-fill: green;");
        } else {
            statusLabel.setText("Error: Efficiency data missing. Run Dummy Generator.");
            statusLabel.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    public void backToDashboardOnClick(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ProductionDashboardView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Production Supervisor Dashboard");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) { e.printStackTrace(); }
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

        java.util.Optional<ButtonType> result = alert.showAndWait();

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