package cse213.papermillapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;

public class LoginController {

    @FXML
    private TextField idTextField;
    @FXML
    private Label errorLabel;
    @FXML
    private PasswordField pwTextField;

    @FXML
    public void loginButtonOnClick(ActionEvent actionEvent) {
        String userId = idTextField.getText().trim();
        String userPassword = pwTextField.getText().trim();

        if (userId.isEmpty() || userPassword.isEmpty()) {
            errorLabel.setText("Error: All fields must be filled out!");
            errorLabel.setStyle("-fx-text-fill: red");
            return;
        }

        if(!userId.matches("\\d+") || (userId.length() != 4 && userId.length() != 7)) {
            errorLabel.setText("Error: Username must be 4 or 7 digits!");
            errorLabel.setStyle("-fx-text-fill: red");
            return;
        }

        try {
            int typeId = Integer.parseInt(userId);
            boolean loginSuccess = false;
            String detectedRole = "";

            File file = new File("LoginCredentials.bin");
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            LoginCredentials credentials;

            while (true) {
                try {
                    credentials = (LoginCredentials) ois.readObject();
                    if (credentials.getUserId() == typeId && credentials.getPw().equals(userPassword)) {
                        loginSuccess = true;
                        detectedRole = credentials.getUserType();
                        break;
                    }
                } catch (EOFException e) {
                    break;
                }
            }

            ois.close();

            if(loginSuccess) {
                String fxmlFilename = "";

                switch (detectedRole) {
                    case "Inventory Officer":
                        fxmlFilename = "InventoryDashboardView.fxml";
                        break;
                    case "Production Supervisor":
                        fxmlFilename = "ProductionDashboardView.fxml";
                        break;
                    default:
                        errorLabel.setText("Dashboard for this role is missing!");
                        errorLabel.setStyle("-fx-text-fill: red");
                        return;
                }

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFilename));
                Scene dashboardScene = new Scene(fxmlLoader.load());

                Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
                stage.setTitle("Paper Mill System - " + detectedRole + " Dashboard");
                stage.setScene(dashboardScene);
                stage.show();

            } else {
                errorLabel.setText("Incorrect ID or Password");
                errorLabel.setStyle("-fx-text-fill: red");
            }
    } catch (Exception e) {
            errorLabel.setText("There has been an Error! plese Check Console!");
            errorLabel.setStyle("-fx-text-fill: red");
            e.printStackTrace();
        }
    }
}