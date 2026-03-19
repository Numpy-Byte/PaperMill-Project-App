package cse213.papermillapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class MainApplicationClass extends Application {

    @Override
    public void start(Stage defaultStage) throws IOException {
        FXMLLoader myFxmlLoader = new FXMLLoader(
                MainApplicationClass.class.getResource("TheView.fxml")
        );

        Scene firstScene = new Scene(myFxmlLoader.load());

        defaultStage.setTitle("Paper Mill System - Process 1 Login");
        defaultStage.setScene(firstScene);
        defaultStage.show();
    }

    static void main(String[] args) {
        launch(args);
    }
}