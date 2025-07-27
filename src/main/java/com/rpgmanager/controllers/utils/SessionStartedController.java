package com.rpgmanager.controllers.utils;

import com.rpgmanager.controllers.screens.SessionController;
import com.rpgmanager.models.Campaign;
import com.rpgmanager.utils.DatabaseManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;

public class SessionStartedController {

    @FXML private Label sessionTitle;
    @FXML private ListView<String> eventsList;
    @FXML private TextField eventField;
    @FXML private TextField nameField;

    private Campaign campaign;
    private int sessionId;
    private Stage stage;
    private SessionController controller;

    public void startSession(Campaign campaign, int sessionId, String name, SessionController controller) {
        this.campaign = campaign;
        this.sessionId = sessionId;
        this.stage = (Stage) sessionTitle.getScene().getWindow();
        this.controller = controller;
        sessionTitle.setText("Sesión: " + name);
    }

    @FXML
    private void addEvent() {
        String evento = eventField.getText().trim();
        String name = nameField.getText().trim();
        if (evento.isEmpty()) return;
        if (name.isEmpty()) return;

        eventsList.getItems().add(evento);
        saveEvent(name, evento);
        eventField.clear();
    }

    public void addRollDice(String result){
        eventsList.getItems().add("Resultado de tirada = " + result);
        saveEvent("ComandoDado", "Resultado de tirada = " + result);
        eventField.clear();
    }

    private void saveEvent(String name, String texto) {
        try (Connection conn = DatabaseManager.getConnection()) {
            String sql = "INSERT INTO eventNotes (session_id, name, description, creation_at) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, sessionId);
            stmt.setString(2, name);
            stmt.setString(3, texto);
            stmt.setString(4, LocalDateTime.now().toString());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void endSession() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Terminar sesión");
        dialog.setHeaderText("Escribe un resumen final para la sesión");
        dialog.setContentText("Resumen:");

        dialog.showAndWait().ifPresent(resumenFinal -> {
            try (Connection conn = DatabaseManager.getConnection()) {
                String sql = "UPDATE sessions SET final_resume = ?, ended_at = ? WHERE id = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, resumenFinal);
                stmt.setString(2, LocalDateTime.now().toString());
                stmt.setInt(3, sessionId);
                stmt.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(controller != null) controller.clearActiveSession();
            stage.close();
        });
    }

    @FXML
    private void onRollDice() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/utils/dice_roller.fxml"));
            Parent root = loader.load();

            RollDiceChatController controller = loader.getController();
            controller.setSessionStartedController(this);
            controller.setCampaign(campaign);
            controller.setSession(sessionId);

            Stage stage = new Stage();
            stage.setTitle("Tirar dado - Chat");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error cargando dice chat window.");
            alert.showAndWait();
        }
    }
}