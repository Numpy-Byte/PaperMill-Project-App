package cse213.papermillapp;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class ValuationReportController
{
    @javafx.fxml.FXML
    private PieChart valuationPieChart;

    @javafx.fxml.FXML
    public void initialize() {
        PieChart.Data slice1 = new PieChart.Data("Wood Pulp ($45k)", 45000);
        PieChart.Data slice2 = new PieChart.Data("Bleach/Chemicals ($12k)", 12000);
        PieChart.Data slice3 = new PieChart.Data("Recycled Paper ($28k)", 28000);
        PieChart.Data slice4 = new PieChart.Data("Packaging Boxes ($8k)", 8000);

        ObservableList<PieChart.Data> chartData = FXCollections.observableArrayList(slice1, slice2, slice3, slice4);

        valuationPieChart.setData(chartData);

        valuationPieChart.setLabelsVisible(true);
        valuationPieChart.setLegendVisible(false);
    }

    @javafx.fxml.FXML
    public void backtoDashboardOnClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InventoryNavigationMenuView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Paper Mill - InventoryDashboard");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);


    }

    @javafx.fxml.FXML
    public void logoutOnClick(ActionEvent actionEvent) throws IOException {
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

            } catch ( Exception e) {
                System.out.println("Error returning to login Screen!");
                e.printStackTrace();
            }
        }
    }
}