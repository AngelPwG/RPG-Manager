package com.rpgmanager.controllers.screens;

import com.rpgmanager.controllers.MainController;
import com.rpgmanager.controllers.utils.CampaignCardController;
import com.rpgmanager.controllers.utils.NewCampaignController;
import com.rpgmanager.utils.CampaignAware;
import com.rpgmanager.utils.DatabaseManager;
import com.rpgmanager.utils.MainAware;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import com.rpgmanager.models.Campaign;
import javafx.scene.layout.FlowPane;
import javafx.scene.Node;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;

public class CampaignsController implements CampaignAware, MainAware {

    private final ObservableList<Campaign> campaignList = FXCollections.observableArrayList();
    @FXML private FlowPane cardContainer;

    private MainController mainController;
    private Campaign currentCampaign;

    @Override
    public void setCampaign(Campaign campaign) {
        if (campaign != null){
            this.currentCampaign = campaign;
        }
    }

    @Override
    public void setMainController(MainController controller) {
        this.mainController = controller;
        if(this.mainController == null) System.out.println("mainController null en campaigns");
    }

    public void initData(){
        cardContainer.getChildren().clear();

        loadCampaigns();
    }

    private void loadCampaigns() {
        campaignList.clear();
        cardContainer.getChildren().clear();

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM campaigns")) {

            while (rs.next()) {
                Campaign c = new Campaign(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("system"),
                        rs.getString("description"),
                        LocalDateTime.parse(rs.getString("created_at")),
                        rs.getString("state")
                );
                campaignList.add(c);

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/utils/campaign_card.fxml"));
                Node card = loader.load();
                CampaignCardController controller = loader.getController();
                controller.setCampaign(c);
                controller.setMainController(this.mainController);
                cardContainer.getChildren().add(card);
            }

        } catch (Exception e) {
            e.printStackTrace();
            showError("Error to load the campaigns");
        }
    }

    @FXML
    private void onNewCampaign(ActionEvent event) {
        NewCampaignController.showWindow();
        loadCampaigns();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
