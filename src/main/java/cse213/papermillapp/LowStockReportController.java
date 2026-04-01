package cse213.papermillapp;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class LowStockReportController
{
    @javafx.fxml.FXML
    private TableView<Material> lowStockTableView;
    @javafx.fxml.FXML
    private TableColumn<Material, String> nameColumn;
    @javafx.fxml.FXML
    private TableColumn<Material, Integer> thresholdColumn;
    @javafx.fxml.FXML
    private TableColumn<Material, Integer> stockColumn;
    @javafx.fxml.FXML
    private Label statusLabel;
    @javafx.fxml.FXML
    private TableColumn<Material, String> idColumn;
    @javafx.fxml.FXML
    private TextField searchTextField;

    private ArrayList<Material> masterLowStockList = new  ArrayList<>();

    @javafx.fxml.FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("currentStock"));
        thresholdColumn.setCellValueFactory(new PropertyValueFactory<>("minThreshold"));


    }

    @javafx.fxml.FXML
    public void logoutOnClick(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TheView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Log Out");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            statusLabel.setText("Error loading Login Screen! Check Console");
            e.printStackTrace();
        }
    }

    @javafx.fxml.FXML
    public void loadReportOnClick(ActionEvent actionEvent) {
        ArrayList<Material> allMaterials= new ArrayList<>();
        allMaterials.add(new Material("MAT-01", "Wood Pulp", 50, 100));
        allMaterials.add(new Material("MAT-02", "Chemicals", 300, 100));
        allMaterials.add(new Material("MAT-03", "Dye", 20, 50));
        allMaterials.add(new Material("MAT-04", "Bleach", 500, 200));

        masterLowStockList.clear();

        for (Material material: allMaterials) {
            if(material.getCurrentStock() < material.getMinThreshold()) {
                masterLowStockList.add(material);
            }
        }

        lowStockTableView.setItems(FXCollections.observableArrayList(masterLowStockList));

        if(masterLowStockList.isEmpty()) {
            statusLabel.setText("Inventory is healthy!");
            statusLabel.setStyle("-fx-text-fill: green");
        } else {
            statusLabel.setText("Warning " + masterLowStockList.size() + " items are low!");
            statusLabel.setStyle("-fx-text-fill: red");
        }
    }

    @javafx.fxml.FXML
    public void backToDashboardOnClick(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InventoryDashboardView.fxml"));
            Scene dashboardScene = new Scene(fxmlLoader.load());

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Dashboard Screen");
            stage.setScene(dashboardScene);
            stage.show();

        } catch (Exception e) {
            statusLabel.setText("Error loading Dashboard Screen! Check Console");
            e.printStackTrace();
        }

    }

    @javafx.fxml.FXML
    public void filterReportOnClick(ActionEvent actionEvent) {
        String searchText = searchTextField.getText();

        try {
            if(searchText == null || searchText.trim().isEmpty()) {
                lowStockTableView.setItems(FXCollections.observableArrayList(masterLowStockList));
                statusLabel.setText("Showing all low stock Levels");
                statusLabel.setStyle("-fx-text-fill: green");
                return;
            }

            String lowerCaseFilter = searchText.trim().toLowerCase();

            ArrayList<Material> filteredList = new ArrayList<>();

            for(Material m: masterLowStockList) {
                if(m.getName().toLowerCase().contains(lowerCaseFilter) || m.getId().toLowerCase().contains(lowerCaseFilter)) {
                    filteredList.add(m);
                }
            }

            lowStockTableView.setItems(FXCollections.observableArrayList(filteredList));

            statusLabel.setText("Filtered applied Found: " + filteredList.size() + " matching items.");
            statusLabel.setStyle("-fx-text-fill: #4A3F35;");

        } catch (Exception e) {
            statusLabel.setText("Error loading Table View! Check Console");
            e.printStackTrace();

        }
    }
}