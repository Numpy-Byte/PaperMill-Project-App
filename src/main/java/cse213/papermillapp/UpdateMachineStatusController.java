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

import javax.crypto.Mac;
import java.io.*;
import java.util.ArrayList;
import java.util.Optional;

public class UpdateMachineStatusController
{
    @javafx.fxml.FXML
    private TableColumn<Machine, String> statusCol;
    @javafx.fxml.FXML
    private ComboBox<String> statusComboBox;
    @javafx.fxml.FXML
    private TableColumn<Machine, String> machineNameCol;
    @javafx.fxml.FXML
    private TableView<Machine> machineTable;
    @javafx.fxml.FXML
    private TableColumn<Machine, String> machineIdCol;
    @javafx.fxml.FXML
    private ComboBox<String> machineIdComboBox;
    @javafx.fxml.FXML
    private Label statusLabel;

    private ArrayList<Machine> machineList = new ArrayList<>();

    @javafx.fxml.FXML
    public void initialize() {
        machineIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        machineNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        ObservableList<String> statusOptions = FXCollections.observableArrayList("Running", "Maintenance", "Broken");
        statusComboBox.setItems(statusOptions);

        loadMachineData();
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
            Scene dashboardScene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Paper Mill - Production Supervisor Dashboard");
            stage.setScene(dashboardScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @javafx.fxml.FXML
    public void updateStatusOnClick(ActionEvent actionEvent) {
        String selectedMachineId = machineIdComboBox.getValue();
        String newStatus = statusComboBox.getValue();

        if (selectedMachineId == null || newStatus == null) {
            statusLabel.setText("Error: Please select both Machine ID and Status.");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        boolean updated = false;
        for (Machine m : machineList) {
            if (m.getId().equals(selectedMachineId)) {
                m.setStatus(newStatus);
                updated = true;
                break;
            }
        }

        if (updated) {
            saveMachineData();

            machineTable.setItems(FXCollections.observableArrayList(machineList));
            machineTable.refresh();

            statusLabel.setText("Success: " + selectedMachineId + " is now marked as " + newStatus + ".");
            statusLabel.setStyle("-fx-text-fill: green;");

            machineIdComboBox.setValue(null);
            statusComboBox.setValue(null);
        } else {
            statusLabel.setText("System Error: Could not update status");
            statusLabel.setStyle("-fx-text-fill: red;");
        }
    }

    private void loadMachineData() {
        machineList.clear();
        File file = new File("Machine.bin");
        if (!file.exists()) {
            statusLabel.setText("Error: Machine.bin not found!");
            return;
        }

        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            while (true) {
                try {
                    Machine m = (Machine) ois.readObject();
                    machineList.add(m);
                } catch (EOFException e) {
                    break;
                } catch (ClassNotFoundException e) {
                    break;
                }
            }
            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        machineTable.setItems(FXCollections.observableArrayList(machineList));
        ObservableList<String> machineIds = FXCollections.observableArrayList();
        for (Machine m : machineList) {
            machineIds.add(m.getId());
        }
        machineIdComboBox.setItems(machineIds);
    }

    private void saveMachineData() {
        try {
            FileOutputStream fos = new FileOutputStream("Machine.bin");
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            for (Machine m: machineList) {
                oos.writeObject(m);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}