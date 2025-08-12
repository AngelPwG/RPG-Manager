package com.rpgmanager;

import com.rpgmanager.controllers.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/MainLayout.fxml"));

        Parent root = mainLoader.load();

        MainController mainController = mainLoader.getController();

        root.setUserData(mainController);

        mainController.setContentWithCampaign("/screens/main.fxml");
        Font.loadFont(getClass().getResource("/fonts/DragonHunter.otf").toExternalForm(), 12);

        Scene scene = new Scene(root, 1280, 700);
        primaryStage.setScene(scene);
        primaryStage.setTitle("RPG Campaign Manager");
        //scene.getStylesheets().add(getClass().getResource("/styles/main_style.css").toExternalForm());

        primaryStage.show();
    }
}