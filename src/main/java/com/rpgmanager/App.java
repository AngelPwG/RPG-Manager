package com.rpgmanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/screens/main.fxml"));
        BorderPane root = loader.load();

        Scene scene = new Scene(root, 900, 600);
        primaryStage.setTitle("RPG Campaign Manager");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
