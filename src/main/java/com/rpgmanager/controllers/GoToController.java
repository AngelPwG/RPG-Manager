package com.rpgmanager.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class GoToController {
    public void goToHome(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/screens/main.fxml"));
            Parent root = fxmlLoader.load();

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

            Scene scene = new Scene(root, 1000, 600);
            stage.setScene(scene);
            stage.setTitle("Home");

        } catch (IOException e) {
            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se pudo cargar la página");
            alert.setContentText("Error al cargar home.fxml");
            alert.showAndWait();
        }
    }

    public void goToMonstersDatabase(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/screens/monsters_database.fxml"));
            Parent root = fxmlLoader.load();

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

            Scene scene = new Scene(root, 1000, 600);
            stage.setScene(scene);
            stage.setTitle("Monsters Database");

        } catch (IOException e) {
            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se pudo cargar la página");
            alert.setContentText("Error al cargar monster_database.fxml");
            alert.showAndWait();
        }
    }
}
