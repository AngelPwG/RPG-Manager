package com.rpgmanager.controllers.screens;

import com.rpgmanager.controllers.MainController;
import com.rpgmanager.controllers.utils.RollDiceChatController;
import com.rpgmanager.models.Campaign;
import com.rpgmanager.utils.CampaignAware;
import com.rpgmanager.utils.DatabaseManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

public class CampaignOverviewController implements CampaignAware {

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
            stage.setTitle("Tirar dado - Chat");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error cargando dice chat window.");
            alert.showAndWait();
        }
    }

    @FXML
    private void deleteCampaign() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmar eliminación");
        alert.setHeaderText("¿Estás seguro de que quieres eliminar esta camapaña?");
        alert.setContentText("Esta acción no se puede deshacer");

        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            this.deleteCampaignByID(campaign.getId());

            try {
                MainController mainController = (MainController)
                        labelCampaignName.getScene().getRoot().getUserData();

                if (mainController != null) {
                    mainController.setContentWithCampaign("/screens/main.fxml");
                } else {
                    System.out.println("No se encontró el MainController");
                }
            } catch (Exception e) {
                e.printStackTrace();
                Alert errorAlert = new Alert(Alert.AlertType.ERROR, "No se pudo cargar el menú principal.");
                errorAlert.showAndWait();
            }
        }
    }

    public void deleteCampaignByID(int campaignID) {
        String sql = "DELETE FROM campaigns WHERE id = ?";
        try (Connection conn = com.rpgmanager.utils.DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, campaignID);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}