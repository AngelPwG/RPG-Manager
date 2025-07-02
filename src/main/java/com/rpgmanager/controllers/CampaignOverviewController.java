package com.rpgmanager.controllers;

import com.rpgmanager.models.Campaign;
import com.rpgmanager.models.Rolls;
import com.rpgmanager.utils.DatabaseManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CampaignOverviewController extends GoToController{

    @FXML
    private Label labelCampaignName;

    @FXML
    private Label labelSessions;

    @FXML
    private Label labelCharacters;

    @FXML
    private Label labelDices;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private TextField typeField;

    private Campaign campaign;

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
        labelCampaignName.setText(campaign.getName());
        descriptionArea.setText(campaign.getDescription());
        typeField.setText(campaign.getSystem());

        loadStats();
    }

    private void loadStats() {
        int sessions = 0;
        int characters = 0;
        int rolls = 0;

        try (Connection conn = DatabaseManager.getConnection()) {
            PreparedStatement st1 = conn.prepareStatement("SELECT COUNT(*) FROM sessions WHERE campaign_id = ?");
            st1.setInt(1, campaign.getId());
            ResultSet rs1 = st1.executeQuery();
            if (rs1.next()) sessions = rs1.getInt(1);

            PreparedStatement st2 = conn.prepareStatement("SELECT COUNT(*) FROM characters WHERE campaign_id = ?");
            st2.setInt(1, campaign.getId());
            ResultSet rs2 = st2.executeQuery();
            if (rs2.next()) characters = rs2.getInt(1);

            PreparedStatement st3 = conn.prepareStatement("SELECT COUNT(*) FROM rolls WHERE campaign_id = ?");
            st3.setInt(1, campaign.getId());
            ResultSet rs3 = st3.executeQuery();
            if (rs3.next()) rolls = rs3.getInt(1);


        } catch (Exception e) {
            e.printStackTrace();
        }

        labelSessions.setText(String.valueOf(sessions));
        labelCharacters.setText(String.valueOf(characters));
        labelDices.setText(String.valueOf(rolls));
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

    @FXML
    private void onExport() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Export");
        alert.setHeaderText(null);
        alert.setContentText("Export Functionality not implemented yet");
        alert.showAndWait();
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
    private void goToSessions(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/screens/sessions.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            SessionController controller = loader.getController();
            controller.setCampaign(campaign);

            Scene scene = new Scene(root, 1000, 600);
            stage.setScene(scene);
            stage.setTitle("Sessions");

        } catch (IOException e) {
            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("The page could not load");
            alert.setContentText("Error to load sessions.fxml");
            alert.showAndWait();
        }
    }
}