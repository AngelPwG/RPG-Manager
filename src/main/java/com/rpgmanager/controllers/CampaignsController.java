package com.rpgmanager.controllers;

import com.rpgmanager.utils.DatabaseManager;
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

public class CampaignsController {

    private final ObservableList<Campaign> campaignList = FXCollections.observableArrayList();
    @FXML private FlowPane cardContainer;

    @FXML
    public void initialize() {
        // Limpia el contenedor por si hay algo (opcional)
        cardContainer.getChildren().clear();

        // Carga las campañas y crea las tarjetas en el contenedor
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

                // Cargar tarjeta individual
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/utils/campaign_card.fxml"));
                Node card = loader.load();
                CampaignCardController controller = loader.getController();
                controller.setCampaign(c);
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

    @FXML
    private void onOpenCampaign(ActionEvent event) {
        showError("Function not implemented yet");
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
