package cse213.papermillapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class InventoryDashboardController
{
    @javafx.fxml.FXML
    private TableView<?> inventoryTable;
    @javafx.fxml.FXML
    private TableColumn<?, ?> idCol;
    @javafx.fxml.FXML
    private TableColumn<?, ?> nameCol;
    @javafx.fxml.FXML
    private TableColumn<?, ?> quantityCol;
    @javafx.fxml.FXML
    private TableColumn<?, ?> categoryCol;

    @javafx.fxml.FXML
    public void initialize() {
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
}