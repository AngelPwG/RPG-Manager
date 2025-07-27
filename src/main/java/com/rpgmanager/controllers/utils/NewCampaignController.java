package com.rpgmanager.controllers.utils;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import com.rpgmanager.utils.DatabaseManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;

public class NewCampaignController {

    @FXML
    private TextField nameField;

    @FXML
    private ComboBox<String> systemField;

    @FXML
    private TextArea descField;

    @FXML
    private void initialize() {
        systemField.setItems(FXCollections.observableArrayList(
                "Dungeons & Dragons 5e"
        ));
    }

    @FXML
    private void createCampaign(ActionEvent event) {
        String name = nameField.getText().trim();
        String system = systemField.getValue();
        String description = descField.getText().trim();

        if (name.isEmpty() || system == null || system.isEmpty()) {
            showAlert("Nombre y sistema son obligatorios.");
            return;
        }

        try (Connection conn = DatabaseManager.getConnection()) {
            String sql = "INSERT INTO campaigns (name, system, description, created_at, state) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, system);
            stmt.setString(3, description);
            stmt.setString(4, LocalDateTime.now().toString());
            stmt.setString(5, "No iniciado");
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error al guardar la campaña.");
            return;
        }

        closeWindow();
    }

    @FXML
    private void cancel(ActionEvent event) {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void showWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(NewCampaignController.class.getResource("/utils/new_campaign.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Crear una nueva campaña");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "La ventana de nueva campaña no pudo abrir.");
            alert.showAndWait();
        }
    }
}
