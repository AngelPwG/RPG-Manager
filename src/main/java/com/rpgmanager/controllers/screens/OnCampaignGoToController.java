package com.rpgmanager.controllers.screens;

import com.rpgmanager.models.Campaign;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class OnCampaignGoToController extends GoToController{

    protected Campaign campaign;

    public abstract void setCampaign(Campaign campaign);

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
