package com.rpgmanager.controllers.utils;

import com.rpgmanager.controllers.SessionController;
import com.rpgmanager.models.Campaign;
import com.rpgmanager.utils.DatabaseManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;

public class CreateSessionController {

    @FXML
    private TextField sessionTitle;

    @FXML private ListView<String> charactersList;

    private Campaign campaign;
    private SessionController controller;

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
        loadCharacters();
    }

    public void setSessionController(SessionController controller){
        this.controller = controller;
    }

    @FXML
    private void initialize() {
        charactersList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    private void loadCharacters() {
        try (Connection conn = DatabaseManager.getConnection()) {
            String sql = "SELECT id, name FROM characters WHERE campaign_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, campaign.getId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                charactersList.getItems().add(id + ": " + name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void saveSession() {
        String name = sessionTitle.getText().trim();

        if (name.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please write a title or a short description.");
            alert.showAndWait();
            return;
        }

        try (Connection conn = DatabaseManager.getConnection()) {
            String sql = "INSERT INTO sessions (campaign_id, name, created_at) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, campaign.getId());
            stmt.setString(2, name);
            stmt.setString(3, LocalDateTime.now().toString());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int sessionId = rs.getInt(1);
                syncCharacters(conn, sessionId);
                openSessionWindow(sessionId, name);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error to start the session.");
            alert.showAndWait();
            return;
        }

        if(!campaign.getState().equals("Started")) campaign.setState("Started");

        close();
    }

    private void syncCharacters(Connection conn, int sesionId) {
        try {
            String sql = "INSERT INTO characterSession (character_id, session_id) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            for (String item : charactersList.getSelectionModel().getSelectedItems()) {
                int characterId = Integer.parseInt(item.split(":")[0]);
                stmt.setInt(1, characterId);
                stmt.setInt(2, sesionId);
                stmt.addBatch();
            }
            stmt.executeBatch();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openSessionWindow(int sessionId, String name) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/utils/session_started.fxml"));
            Parent root = loader.load();

            SessionStartedController startedController = loader.getController();

            Stage stage = new Stage();
            stage.setTitle("Session Started");
            stage.setScene(new Scene(root));

            startedController.startSession(campaign, sessionId, name, controller);

            if (controller != null) controller.registerActiveSession(stage);

            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "The window session started could not load.");
            alert.showAndWait();
        }
    }

    @FXML
    private void cancel() {
        close();
    }

    private void close() {
        Stage stage = (Stage) sessionTitle.getScene().getWindow();
        stage.close();
    }


}
