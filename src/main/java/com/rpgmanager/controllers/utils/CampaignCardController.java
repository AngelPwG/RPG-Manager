package com.rpgmanager.controllers.utils;

import com.rpgmanager.controllers.MainController;
import com.rpgmanager.models.Campaign;
import com.rpgmanager.utils.MainAware;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.time.format.DateTimeFormatter;

public class CampaignCardController {

    private MainController mainController;

    private Campaign campaign;

    @FXML private Label nameLabel;
    @FXML private Label systemLabel;
    @FXML private Text descText;
    @FXML private Label dateLabel;
    @FXML private Label stateLabel;

    public void setMainController(MainController mainController) {
        if(mainController == null) System.out.println("mainController null en cardCampaign");
        this.mainController = mainController;
    }

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
            this.mainController.setCampaign(campaign);
            this.mainController.setContentWithCampaign("/screens/campaign_resume.fxml");
        } catch (NullPointerException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "The campaign could not be opened.");
            alert.showAndWait();
        }
    }
}