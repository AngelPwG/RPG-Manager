package com.rpgmanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/screens/main.fxml")); // Make sure "your-view-name.fxml" is correct
        HBox root = fxmlLoader.load();
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("RPG Manager");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
