package com.rpgmanager.controllers;

import com.rpgmanager.models.Campaign;
import com.rpgmanager.models.Rolls;
import com.rpgmanager.models.Monsters;
import com.rpgmanager.models.Rolls;
import com.rpgmanager.utils.DatabaseManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class RollHistoryController extends GoToController{

    @FXML
    private TableView<Rolls> rollsTable;

    @FXML
    private TableColumn<Rolls, String> colCampaign;

    @FXML
    private TableColumn<Rolls, String> colSession;

    @FXML
    private TableColumn<Rolls, String> colCharacter;

    @FXML
    private TableColumn<Rolls, String> colType;

    @FXML
    private TableColumn<Rolls, String> colDate;

    @FXML
    private TableColumn<Rolls, String> colResult;

    private final ObservableList<Rolls> rollList = FXCollections.observableArrayList();

    private Campaign campaign;

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;

        loadRolls();
    }

    @FXML
    public void initialize() {
        colCampaign.setCellValueFactory(new PropertyValueFactory<>("campaign"));
        colSession.setCellValueFactory(new PropertyValueFactory<>("session"));
        colCharacter.setCellValueFactory(new PropertyValueFactory<>("character"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
        colResult.setCellValueFactory(new PropertyValueFactory<>("result"));

        loadRolls();
    }

    @FXML
    private void refresh(){
        loadRolls();
    }

    private void loadRolls() {
        rollList.clear();

        String sql = "SELECT " +
                "r.id AS roll_id, " +
                "r.type AS roll_type, " +
                "r.date_time AS roll_date_time, " +
                "r.result AS roll_result, " +
                "c.name AS campaign_name, " +
                "ch.name AS character_name, " +
                "s.name AS session_name " +
                "FROM rolls r " +
                "LEFT JOIN campaigns c ON r.campaign_id = c.id " +
                "LEFT JOIN characters ch ON r.character_id = ch.id " +
                "LEFT JOIN sessions s ON r.session_id = s.id";

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Rolls r = new Rolls(
                        rs.getInt("roll_id"),
                        rs.getString("character_name"),
                        rs.getString("roll_type"),
                        rs.getString("session_name"),
                        rs.getString("campaign_name"),
                        rs.getString("roll_date_time"),
                        rs.getString("roll_result")
                );
                rollList.add(r);
            }
            rollsTable.setItems(rollList);
        } catch (Exception e) {
            e.printStackTrace();
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
