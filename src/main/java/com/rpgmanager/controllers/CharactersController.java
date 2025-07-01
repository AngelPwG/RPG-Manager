package com.rpgmanager.controllers;

import com.rpgmanager.models.Campaign;
import com.rpgmanager.models.Character;
import com.rpgmanager.utils.DatabaseManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CharactersController extends GoToController{
    @FXML private Label campaignTitle;
    @FXML private FlowPane charactersContainer;
    @FXML private ScrollPane myScrollPane;

    private Campaign campaign;

    @FXML
    public void initialize() {
        charactersContainer.prefWidthProperty().bind(myScrollPane.widthProperty());
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
        campaignTitle.setText("Characters from " + campaign.getName());
        loadCharacters();
    }

    private void loadCharacters() {
        charactersContainer.getChildren().clear();
        try (Connection conn = DatabaseManager.getConnection()) {
            String sql = "SELECT * FROM characters WHERE campaign_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, campaign.getId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Character character = new Character(
                        rs.getInt("id"),             // id
                        rs.getInt("campaign_id"),
                        rs.getBoolean("isNPC"),
                        rs.getInt("ac"),
                        rs.getInt("hp"),
                        rs.getInt("stats_id"),
                        rs.getString("name"),
                        rs.getString("race"),
                        rs.getString("class"),
                        rs.getString("bonds"),
                        rs.getString("fears"),
                        rs.getString("loves"),
                        rs.getString("hates")
                );

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/utils/character_card.fxml"));
                Parent card = loader.load();
                CharacterCardController controller = loader.getController();
                controller.setCharacter(character);

                charactersContainer.getChildren().add(card);
            }

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error to load characters.");
            alert.showAndWait();
        }
    }

    @FXML
    private void onAddCharacter() {
        try {
            FXMLLoader loader = new FXMLLoader(CreateCharacterController.class.getResource("/utils/create_character.fxml"));
            Parent root = loader.load();
            CreateCharacterController controller = loader.getController();
            controller.setCampaign(campaign);

            Stage stage = new Stage();
            stage.setTitle("Create New Character");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
            loadCharacters();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "The window Create Character couldn't load.");
            alert.showAndWait();
        }
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
