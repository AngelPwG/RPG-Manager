package com.rpgmanager.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class SidebarController {

    private MainController mainController;

    public void setMainController(MainController controller) {
        this.mainController = controller;
    }

    @FXML
    private void goToHome(ActionEvent e) {
        if (mainController != null) {
            mainController.setContentWithCampaign("/screens/main.fxml");
        }
    }

    @FXML
    private void goToMonstersDatabase(ActionEvent e) {
        if (mainController != null) {
            mainController.setContent("/screens/monsters_database.fxml");
        }
    }

    @FXML
    private void goToItems(ActionEvent e) {
        if (mainController != null) {
            mainController.setContent("/screens/items_database.fxml");
        }
    }

    @FXML
    private void goToCampaigns(ActionEvent e) {
        if (mainController != null) {
            mainController.setContentWithCampaign("/screens/campaign_resume.fxml");
        }
    }

    @FXML
    private void goToCharacters(ActionEvent e) {
        if (mainController != null) {
            mainController.setContentWithCampaign("/screens/characters.fxml");
        }
    }

    @FXML
    private void goToSessions(ActionEvent e) {
        if (mainController != null) {
            mainController.setContentWithCampaign("/screens/sessions.fxml");
        }
    }

    @FXML
    private void goToRolls(ActionEvent e) {
        if (mainController != null) {
            mainController.setContent("/screens/rolls-history.fxml");
        }
    }
}
