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
import java.util.ArrayList;
import java.util.Arrays;

public class LoginController {

    @javafx.fxml.FXML
    private TextField idTextField;
    @javafx.fxml.FXML
    private Label errorLabel;
    @javafx.fxml.FXML
    private PasswordField pwTextField;

    private ArrayList<String> validUserIds = new ArrayList<>(Arrays.asList("2430999", "2432035", "2432046", "2230902"));
    private ArrayList<String> validPasswords = new ArrayList<>(Arrays.asList("1234", "1234", "1234", "1234"));

    @javafx.fxml.FXML
    public void loginButtonOnClick(ActionEvent actionEvent) {

        try {
            // Get text using your exact fx:id names
            String userIdText = idTextField.getText().trim();
            String password = pwTextField.getText().trim();

            // Checking if the ID is exactly 4 or 7 numbers
            if (!userIdText.matches("\\d+")) {
                errorLabel.setText("Validation Error: ID must contain only numbers.");
                errorLabel.setStyle("-fx-text-fill: red;");
                return;
            }

            if (userIdText.length() != 4 && userIdText.length() != 7) {
                errorLabel.setText("Validation Error: ID must be exactly 4 or 7 numbers.");
                errorLabel.setStyle("-fx-text-fill: red;");
                return;
            }

            boolean loginSuccess = false;
            for (int i = 0; i < validUserIds.size(); i++) {
                if (userIdText.equals(validUserIds.get(i)) && password.equals(validPasswords.get(i))) {
                    loginSuccess = true;
                    break;
                }
            }

            if (loginSuccess) {
                errorLabel.setText("Login Successful! Loading dashboard...");
                errorLabel.setStyle("-fx-text-fill: green;");

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InventoryDashboardView.fxml"));
                Scene dashboardScene = new Scene(fxmlLoader.load());

                Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                currentStage.setTitle("Dashboard");
                currentStage.setScene(dashboardScene);
                currentStage.show();

            } else {
                errorLabel.setText("Verification Error: Incorrect ID or Password.");
                errorLabel.setStyle("-fx-text-fill: red;");
            }
        } catch (Exception e) {
            errorLabel.setText("Error loading Dashboard! Check console.");
            e.printStackTrace();
        }
    }
}