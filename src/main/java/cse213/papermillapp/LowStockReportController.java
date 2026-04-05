package cse213.papermillapp;

import javafx.collections.FXCollections;
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

        loadLowStockData();


    }

    private void loadLowStockData() {
        masterLowStockList.clear();
        try{
            File file = new File("Material.bin");
            if(!file.exists()) {
                statusLabel.setText("Error: Material.bin not found!");
                return;
            }

            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            while (true) {
                try {
                    Material m = (Material) ois.readObject();

                    if(m.getCurrentStock() < m.getMinThreshold()) {
                        masterLowStockList.add(m);
                    }
                } catch (EOFException e) {
                    break;
                }
            }

            ois.close();

            lowStockTableView.setItems(FXCollections.observableArrayList(masterLowStockList));

            if(masterLowStockList.isEmpty()) {
                statusLabel.setText("Inventory is Healthy. No low stock items.");
                statusLabel.setStyle("-fx-text-fill: green;");
            } else {
                statusLabel.setText("Warning: " + masterLowStockList.size() + " items are low in stock!");
                statusLabel.setStyle("-fx-text-fill: red;");
            }
        } catch (Exception e) {
            statusLabel.setText("Error reading Material.bin file! Check Console.");
            e.printStackTrace();

        }
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
                statusLabel.setText("Error returning to login Screen!");
                e.printStackTrace();
            }
        }
    }

    @javafx.fxml.FXML
    public void loadReportOnClick(ActionEvent actionEvent) {
        masterLowStockList.clear();
        try {
            File file = new File("Material.bin");
            if(!file.exists()) {
                statusLabel.setText("Error: Material.bin not found!");
                return;
            }

            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            while (true) {
                try {
                    Material m = (Material) ois.readObject();

                    if (m.getCurrentStock() < m.getMinThreshold()) {
                       masterLowStockList.add(m);
                    }
                } catch (EOFException e) {
                    break;
                }
            }

            ois.close();

            if (masterLowStockList.isEmpty()) {
                statusLabel.setText("Inventory is Healthy");
                statusLabel.setStyle("-fx-text-fill: green;");
            } else {
                statusLabel.setText("Warning: " + masterLowStockList.size() + " items are low!");
                statusLabel.setStyle("-fx-text-fill: red;");
            }
        } catch (Exception e) {
            statusLabel.setText("Error reading file! Check Console.");
            e.printStackTrace();

        }
    }

    @javafx.fxml.FXML
    public void backToDashboardOnClick(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InventoryNavigationMenuView.fxml"));
            Scene dashboardScene = new Scene(fxmlLoader.load());

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Dashboard Screen");
            stage.setScene(dashboardScene);
            stage.show();
            stage.setResizable(false);


        } catch (Exception e) {
            statusLabel.setText("Error loading Dashboard Screen! Check Console");
            e.printStackTrace();
        }

    }

    @javafx.fxml.FXML
    public void filterReportOnClick(ActionEvent actionEvent) {
        String searchText = searchTextField.getText().trim().toLowerCase();
        ArrayList<Material> filteredList = new ArrayList<>();

        try {
            File file = new File("Material.bin");
            if (!file.exists()) return;

            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            while (true) {
                try {
                    Material m = (Material) ois.readObject();
                    boolean isLowStock = m.getCurrentStock() < m.getMinThreshold();
                    boolean matchesSearch = m.getName().toLowerCase().contains(searchText) ||
                            m.getId().toLowerCase().contains(searchText);

                    if (isLowStock && matchesSearch) {
                        filteredList.add(m);
                    }
                } catch (EOFException e) {
                    break;
                }
            }
            ois.close();

            lowStockTableView.setItems(FXCollections.observableArrayList(filteredList));

            if(filteredList.isEmpty()) {
                statusLabel.setText("No low-stock items match '" + searchText + "'");
            } else {
                statusLabel.setText("Found " + filteredList.size() + " low-stock matches.");
            }

        } catch (Exception e) {
            statusLabel.setText("Filter Error! Check Console.");
            e.printStackTrace();
        }
    }
}