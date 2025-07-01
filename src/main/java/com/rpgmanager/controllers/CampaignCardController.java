package com.rpgmanager.controllers;

import com.rpgmanager.models.Campaign;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class CampaignCardController {

    private Campaign campaign;

    @FXML private Label nameLabel;
    @FXML private Label systemLabel;
    @FXML private Text descText;
    @FXML private Label dateLabel;
    @FXML private Label stateLabel;

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
        nameLabel.setText(campaign.getName());
        systemLabel.setText("System: " + campaign.getSystem());
        descText.setText(campaign.getDescription());
        dateLabel.setText("Created: " + campaign.getCreated_at().format(DateTimeFormatter.ofPattern("dd MMM yyyy")));
        stateLabel.setText("State: " + campaign.getState());
    }

    @FXML
    private void onOpenCampaign(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/screens/campaign_resume.fxml"));
            Parent root = loader.load();

            CampaignOverviewController controller = loader.getController();
            controller.setCampaign(campaign);

            Stage stage = new Stage();
            stage.setTitle("Campaign: " + campaign.getName());
            stage.setScene(new Scene(root, 1000, 600));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "No se pudo abrir la campaña.");
            alert.showAndWait();
        }
    }
}