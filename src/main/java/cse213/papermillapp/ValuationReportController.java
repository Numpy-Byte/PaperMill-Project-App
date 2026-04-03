package cse213.papermillapp;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;

import java.io.IOException;

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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InventoryDashBoardView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Paper Mill - InventoryDashboard");
        stage.setScene(scene);
        stage.show();


    }

    @javafx.fxml.FXML
    public void logoutOnClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TheView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Paper Mill - Login");
        stage.setScene(scene);
        stage.show();
    }
}