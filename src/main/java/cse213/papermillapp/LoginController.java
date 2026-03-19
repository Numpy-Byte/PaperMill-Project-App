package cse213.papermillapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @javafx.fxml.FXML
    private TextField idTextField;
    @javafx.fxml.FXML
    private Label errorLabel;
    @javafx.fxml.FXML
    private PasswordField pwTextField;

    private InventoryOfficer dummyOfficer = new InventoryOfficer(2430999, "Emdad", "1234", "Inventory", "Officer", 50000.0f);

    @javafx.fxml.FXML
    public void loginButtonOnClick(ActionEvent actionEvent) {
        try {
            int enteredId = Integer.parseInt(idTextField.getText());
            String enteredPw = pwTextField.getText();

            if (Employee.verifyLogin(enteredId, enteredPw, dummyOfficer)) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InventoryDashboardView.fxml"));
                Scene dashboardScene = new Scene(fxmlLoader.load());

                Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

                currentStage.setTitle("Inventory Officer Dashboard");
                currentStage.setScene(dashboardScene);
                currentStage.show();
            } else {
                errorLabel.setText("Login Failed! Incorrect ID or Password!");
            }
        } catch (NumberFormatException e) {
            errorLabel.setText("ID must be a number");
        } catch (IOException e) {
            errorLabel.setText("System Error! Check IDE Console.");
            e.printStackTrace();
        }
    }
}