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

public class AssignWorkerController {
    @javafx.fxml.FXML
    private ComboBox<String> workerComboBox;
    @javafx.fxml.FXML
    private ComboBox<String> machineComboBox;
    @javafx.fxml.FXML
    private TableView<Worker> assignmentTable;
    @javafx.fxml.FXML
    private TableColumn<Worker, String> workerIdCol;
    @javafx.fxml.FXML
    private TableColumn<Worker, String> machineIdCol;
    @javafx.fxml.FXML
    private Label statusLabel;

    private ArrayList<Worker> workerList = new ArrayList<>();

    @javafx.fxml.FXML
    public void initialize() {
        workerIdCol.setCellValueFactory(new PropertyValueFactory<>("workerId"));
        machineIdCol.setCellValueFactory(new PropertyValueFactory<>("assignedMachine"));

        loadMachineDropdown();
        loadWorkerData();
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
    public void assignWorkerOnClick(ActionEvent actionEvent) {
        String selectedWorkerId = workerComboBox.getValue();
        String selectedMachineId = machineComboBox.getValue();

        if (selectedWorkerId == null || selectedMachineId == null) {
            statusLabel.setText("Error: Please select both Worker and a Machine.");
            statusLabel.setStyle("-fx-text-fill: red");
            return;
        }

        boolean updated = false;
        for (Worker w : workerList) {
            if (w.getWorkerId().equals(selectedWorkerId)) {
                w.setAssignedMachine(selectedMachineId);
                updated = true;
                break;
            }
        }

        if (updated) {
            saveWorkerData();

            assignmentTable.setItems(FXCollections.observableArrayList(workerList));
            assignmentTable.refresh();

            statusLabel.setText("Success: Worker assigned!");
            statusLabel.setStyle("-fx-text-fill: green");

            workerComboBox.setValue(null);
            machineComboBox.setValue(null);
        }
    }

    private  void loadWorkerData() {
        workerList.clear();
        File file = new File("Worker.bin");
        if (!file.exists()) {
            statusLabel.setText("Worker.bin not found! Run DummyDataGenerator.");
            return;
        }

        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            while (true) {
                try {
                    Worker w = (Worker) ois.readObject();
                    workerList.add(w);
                } catch (EOFException e) {
                    break;
                } catch (ClassNotFoundException e) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        assignmentTable.setItems(FXCollections.observableArrayList(workerList));

        ObservableList<String> workerIds = FXCollections.observableArrayList();
        for (Worker w: workerList) {
            workerIds.add(w.getWorkerId());
        }
        workerComboBox.setItems(workerIds);
    }

    private void loadMachineDropdown() {
        ObservableList<String> machineIds = FXCollections.observableArrayList();
        File machineFile = new File("Machine.bin");
        if (machineFile.exists()) {
            try {
                FileInputStream fis = new FileInputStream(machineFile);
                ObjectInputStream ois = new ObjectInputStream(fis);

                while (true) {
                    try {
                        Machine m = (Machine) ois.readObject();
                        machineIds.add(m.getId());
                    } catch (EOFException e) {
                        break;
                    } catch (ClassNotFoundException e) {
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        machineComboBox.setItems(machineIds);
    }

    private void saveWorkerData() {
        try {
            FileOutputStream fos = new FileOutputStream("Worker.bin");
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            for (Worker w: workerList) {
                oos.writeObject(w);
            }
        } catch (Exception e) {
            e.printStackTrace();
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