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

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Optional;

public class ViewMachineHistoryController {
    @javafx.fxml.FXML
    private TableColumn<MaintenanceLog, String> typeCol;
    @javafx.fxml.FXML
    private TableView<MaintenanceLog> historyTable;
    @javafx.fxml.FXML
    private TableColumn<MaintenanceLog, String> dateCol;
    @javafx.fxml.FXML
    private ComboBox<String> machineComboBox;
    @javafx.fxml.FXML
    private TableColumn<MaintenanceLog, String> machineIdCol;
    @javafx.fxml.FXML
    private Label statusLabel;
    @javafx.fxml.FXML
    private TableColumn<MaintenanceLog, String> remarksCol;

    @javafx.fxml.FXML
    public void initialize() {
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        machineIdCol.setCellValueFactory(new PropertyValueFactory<>("machineId"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("maintenanceType"));
        remarksCol.setCellValueFactory(new PropertyValueFactory<>("remarks"));

        loadMachineDropdown();
    }

    @javafx.fxml.FXML
    public void ViewHistoryOnClick(ActionEvent actionEvent) {
        String selectedMachine = machineComboBox.getValue();
        if (selectedMachine == null) {
            statusLabel.setText("Please select a machine.");
            return;
        }

        ArrayList<MaintenanceLog> logs = new ArrayList<>();
        File file = new File("MaintenanceLog.bin");
        if(!file.exists()) {
            statusLabel.setText("No log file found.");
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            while (true) {
                try {
                    MaintenanceLog log = (MaintenanceLog) ois.readObject();
                    if (log.getMachineId().equals(selectedMachine)) {
                        logs.add(log);
                    }
                } catch (EOFException e) { break; }
            }
        } catch (Exception e) { e.printStackTrace(); }

        historyTable.setItems(FXCollections.observableArrayList(logs));
    }

    private void loadMachineDropdown() {
        ObservableList<String> ids = FXCollections.observableArrayList();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Machine.bin"))) {
            while (true) {
                try {
                    Machine m = (Machine) ois.readObject();
                    ids.add(m.getId());
                } catch (EOFException e) { break; }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        machineComboBox.setItems(ids);
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

                Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                currentStage.setTitle("Paper Mill System - Login");
                currentStage.setScene(loginScene);
                currentStage.show();
            } catch (Exception e) {
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
            stage.setTitle("Paper Mill - Production Supervisor Dashboard");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}