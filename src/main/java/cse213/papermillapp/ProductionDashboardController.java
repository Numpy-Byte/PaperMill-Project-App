package cse213.papermillapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class ProductionDashboardController {
    @javafx.fxml.FXML
    public void initialize() {
    }

    @javafx.fxml.FXML
    public void recordProductionOnClick(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("RecordProductionOutputView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Record Production Output");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @javafx.fxml.FXML
    public void calculateWasteOnClick(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void setProductionTargetOnClick(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void safetyChecklistOnClick(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void updateMachineStatusOnClick(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void teamEfficiencyOnClick(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void machineHistoryOnClick(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void assignWorkersOnClick(ActionEvent actionEvent) {
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
                currentStage.setResizable(false);
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }
}