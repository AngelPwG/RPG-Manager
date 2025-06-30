package com.rpgmanager.controllers;

import com.rpgmanager.models.Campaign;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.time.format.DateTimeFormatter;

public class CampaignCardController {

    @FXML private Label nameLabel;
    @FXML private Label systemLabel;
    @FXML private Text descText;
    @FXML private Label dateLabel;
    @FXML private Label stateLabel;

    public void setCampaign(Campaign campaign) {
        nameLabel.setText(campaign.getName());
        systemLabel.setText("System: " + campaign.getSystem());
        descText.setText(campaign.getDescription());
        dateLabel.setText("Created: " + campaign.getCreated_at().format(DateTimeFormatter.ofPattern("dd MMM yyyy")));
        stateLabel.setText("State: " + campaign.getState());
    }
}