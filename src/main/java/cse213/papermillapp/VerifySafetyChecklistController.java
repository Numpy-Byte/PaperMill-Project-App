package cse213.papermillapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Optional;

public class VerifySafetyChecklistController {

    @FXML private ComboBox<String> machineComboBox;
    @FXML private CheckBox q1CheckBox;
    @FXML private CheckBox q2CheckBox;
    @FXML private CheckBox q3CheckBox;
    @FXML private Button approveBtn;

    private ArrayList<SafetyLog> logList = new ArrayList<>();
    @FXML
    private Label statusLabel;

    @FXML
    public void initialize() {
        loadMachineDropdown();
        loadExistingLogs(); // Loads previous logs so we don't overwrite them
    }

    @FXML
    public void approveOperationOnClick(ActionEvent actionEvent) {
        String selectedMachine = machineComboBox.getValue();

        // 1. Validate Machine Selection
        if (selectedMachine == null) {
            statusLabel.setText("Error: Please select a machine first.");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        // 2. Validate Checkboxes
        if (q1CheckBox.isSelected() && q2CheckBox.isSelected() && q3CheckBox.isSelected()) {

            // Create and save the log
            SafetyLog newLog = new SafetyLog(selectedMachine, true);
            logList.add(newLog);
            saveLogsToFile();

            statusLabel.setText("Success: " + selectedMachine + " is APPROVED for operation.");
            statusLabel.setStyle("-fx-text-fill: green;");

            // Clear the form for the next machine
            machineComboBox.setValue(null);
            q1CheckBox.setSelected(false);
            q2CheckBox.setSelected(false);
            q3CheckBox.setSelected(false);

        } else {
            statusLabel.setText("Error: All safety checks must be verified before approval.");
            statusLabel.setStyle("-fx-text-fill: red;");
        }
    }

    private void loadMachineDropdown() {
        ObservableList<String> machineIds = FXCollections.observableArrayList();
        File file = new File("Machine.bin");
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                while (true) {
                    try {
                        Machine m = (Machine) ois.readObject();
                        machineIds.add(m.getId());
                    } catch (EOFException | ClassNotFoundException e) { break; }
                }
            } catch (IOException e) { e.printStackTrace(); }
        }
        machineComboBox.setItems(machineIds);
    }

    private void loadExistingLogs() {
        logList.clear();
        File file = new File("SafetyLog.bin");
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                while (true) {
                    try {
                        logList.add((SafetyLog) ois.readObject());
                    } catch (EOFException | ClassNotFoundException e) { break; }
                }
            } catch (IOException e) { e.printStackTrace(); }
        }
    }

    private void saveLogsToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("SafetyLog.bin"))) {
            for (SafetyLog log : logList) {
                oos.writeObject(log);
            }
        } catch (IOException e) { e.printStackTrace(); }
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