package cse213.papermillapp;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class InventoryDashboardController
{
    @javafx.fxml.FXML
    private TableView<Material> inventoryTable;
    @javafx.fxml.FXML
    private TableColumn<Material, String> idCol;
    @javafx.fxml.FXML
    private TableColumn<Material, String> nameCol;
    @javafx.fxml.FXML
    private TableColumn<Material, Integer> quantityCol;
    @javafx.fxml.FXML
    private TableColumn<Material, String> categoryCol;
    @javafx.fxml.FXML
    private TextField searchTextField;

    @javafx.fxml.FXML
    public void initialize() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("currentStock"));

        loadInventoryData();
    }

    private void loadInventoryData() {
        ArrayList<Material> materialList = new ArrayList<>();
        File file = new File("Material.bin");

        if(!file.exists()) {
            System.out.println("Material.bin not found! Run DummyDataGenerator first.");
            return;
        }

        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            while (true) {
                try {
                    Material m = (Material) ois.readObject();
                    materialList.add(m);
                } catch (EOFException e) {
                    break;
                }
            }

            ois.close();

            ObservableList<Material> observableList = FXCollections.observableList(materialList);
            inventoryTable.setItems(observableList);

        } catch (Exception e) {
            System.out.println("Error: Loading Material.bin");
            e.printStackTrace();
        }
    }

    @javafx.fxml.FXML
    public void backtoDashboardOnClick(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InventoryNavigationMenuView.fxml"));
            Scene dashboardScene = new Scene(fxmlLoader.load());

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Paper Mill - Inventory Officer Dashboard");
            stage.setScene(dashboardScene);
            stage.show();
        } catch (Exception e) {
            System.out.println("Error loading Dashboard Screen! Check Console");
            e.printStackTrace();
        }
    }

    @javafx.fxml.FXML
    public void logoutOnClick(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TheView.fxml"));
            Scene loginScene = new Scene(fxmlLoader.load());

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Paper Mill - Login");
            stage.setScene(loginScene);
            stage.show();
        } catch (Exception e) {
            System.out.println("Error loading Login Screen! Check Console");
            e.printStackTrace();
        }
    }

    @javafx.fxml.FXML
    public void filterInventoryOnClick(ActionEvent actionEvent) {
        String searchText = searchTextField.getText().trim().toLowerCase();
        ArrayList<Material> filteredList = new ArrayList<>();
        File file = new File("Material.bin");

        if (!file.exists()) return;

        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            while (true) {
                try {
                    Material m = (Material) ois.readObject();

                    if (searchText.isEmpty() || m.getName().toLowerCase().contains(searchText) ||
                            m.getId().toLowerCase().contains(searchText)) {
                        filteredList.add(m);
                    }
                } catch (EOFException e) {
                    break;
                }
            }

            ois.close();

            inventoryTable.setItems(FXCollections.observableArrayList(filteredList));

        } catch (Exception e) {
            System.out.println("Error: filtering inventory!");
            e.printStackTrace();
        }
    }
}