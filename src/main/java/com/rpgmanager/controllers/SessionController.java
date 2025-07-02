package com.rpgmanager.controllers;

import com.rpgmanager.controllers.utils.CreateSessionController;
import com.rpgmanager.controllers.utils.RollDiceChatController;
import com.rpgmanager.controllers.utils.SessionCardController;
import com.rpgmanager.models.Campaign;
import com.rpgmanager.models.Session;
import com.rpgmanager.utils.DatabaseManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SessionController extends GoToController{
    @FXML
    private Label campaignTitle;
    @FXML private FlowPane sessionsContainer;

    private Campaign campaign;
    private static Stage sessionActiveStage;

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
        campaignTitle.setText("Sesiones de " + campaign.getName());
        loadSessions();
    }



    private void loadSessions() {
        sessionsContainer.getChildren().clear();
        try (Connection conn = DatabaseManager.getConnection()) {
            String sql = "SELECT id, created_at, ended_at, name, final_resume FROM sessions WHERE campaign_id = ? ORDER BY created_at DESC";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, campaign.getId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String createdAt = rs.getString("created_at");
                String endedAt = rs.getString("ended_at");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String endDate;
                LocalDateTime localDateTimeCreated = LocalDateTime.parse(createdAt);
                String createDate = localDateTimeCreated.format(formatter);
                if(endedAt == null){
                    endDate = "";
                }
                else {
                    LocalDateTime localDateTimeEnded = LocalDateTime.parse(endedAt);
                    endDate = localDateTimeEnded.format(formatter);
                }
                Session session = new Session(
                        rs.getInt("id"),
                        createDate,
                        endDate,
                        rs.getString("name"),
                        rs.getString("final_resume")
                );
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/utils/session_card.fxml"));
                Parent card = loader.load();
                SessionCardController controller = loader.getController();
                controller.setSession(session);

                sessionsContainer.getChildren().add(card);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onNewSession() {
        if (sessionActiveStage != null && sessionActiveStage.isShowing()) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Sesión en curso");
            alerta.setHeaderText(null);
            alerta.setContentText("Ya hay una sesión en curso abierta.");
            alerta.showAndWait();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/utils/create_session.fxml"));
            Parent root = loader.load();

            CreateSessionController controller = loader.getController();
            controller.setCampaign(campaign);
            controller.setSessionController(this);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("New Session");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            loadSessions();

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "The window create session could not load.");
            alert.showAndWait();
        }
    }

    public void registerActiveSession(Stage stage) {
        sessionActiveStage = stage;
    }

    public void clearActiveSession() {
        sessionActiveStage = null;
        loadSessions();
    }

    @FXML
    private void goToCampaigns(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/screens/campaign_resume.fxml"));
            Parent root = loader.load();

            CampaignOverviewController controller = loader.getController();
            controller.setCampaign(campaign);

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

            Scene scene = new Scene(root, 1000, 600);
            stage.setScene(scene);
            stage.setTitle("Campaign: " + campaign.getName());

        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "The campaign could not be opened.");
            alert.showAndWait();
        }
    }

    @FXML
    private void goToCharacters(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/screens/characters.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            CharactersController controller = loader.getController();
            controller.setCampaign(campaign);

            Scene scene = new Scene(root, 1000, 600);
            stage.setScene(scene);
            stage.setTitle("Characters");

        } catch (IOException e) {
            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("The page could not load");
            alert.setContentText("Error to load character.fxml");
            alert.showAndWait();
        }
    }

    @FXML
    private void goToRolls(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/screens/rolls-history.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            RollHistoryController controller = loader.getController();
            controller.setCampaign(campaign);

            Scene scene = new Scene(root, 1000, 600);
            stage.setScene(scene);
            stage.setTitle("Roll History");

        } catch (IOException e) {
            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("The page could not load");
            alert.setContentText("Error to load roll-history.fxml");
            alert.showAndWait();
        }
    }

    @FXML
    private void onRollDice() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/utils/dice_roller.fxml"));
            Parent root = loader.load();

            RollDiceChatController controller = loader.getController();
            controller.setCampaign(campaign);

            Stage stage = new Stage();
            stage.setTitle("Dice Roller - Chat");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error loading dice chat window.");
            alert.showAndWait();
        }
    }
}
