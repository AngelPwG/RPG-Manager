package com.rpgmanager.controllers;

import com.rpgmanager.models.Campaign;
import com.rpgmanager.models.Skill;
import com.rpgmanager.utils.DatabaseManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreateCharacterController {

    @FXML private TextField nameField;
    @FXML private CheckBox type;
    @FXML private TextField acField, hpField;
    @FXML private TextField strField, dexField, conField, intField, wisField, chaField;
    @FXML private TextArea bondsField, fearsField, lovesField, hatesField;

    @FXML private ListView<Skill> availableSkillsListView;

    private Campaign campaign;

    private final ObservableList<Skill> allSkills = FXCollections.observableArrayList();
    private final ObservableList<Skill> selectedCharacterSkills = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        availableSkillsListView.setItems(allSkills);

        availableSkillsListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        loadAllSkills();
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    private void loadAllSkills() {
        allSkills.clear(); // Clear existing data
        String sql = "SELECT id, name FROM skills"; // Assuming you have a 'Skills' table with 'id' and 'name'
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                allSkills.add(new Skill(id, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error loading skills from database.");
            alert.showAndWait();
        }
    }

    @FXML
    private void createCharacter() {
        int statsId = -1; // Initialize statsId outside try block

        try (Connection conn = DatabaseManager.getConnection()) {
            conn.setAutoCommit(false);

            String sqlStats = "INSERT INTO Stats (strength, dexterity, constitution, intelligence, wisdom, charisma) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstStats = conn.prepareStatement(sqlStats, PreparedStatement.RETURN_GENERATED_KEYS);
            pstStats.setInt(1, Integer.parseInt(strField.getText()));
            pstStats.setInt(2, Integer.parseInt(dexField.getText()));
            pstStats.setInt(3, Integer.parseInt(conField.getText()));
            pstStats.setInt(4, Integer.parseInt(intField.getText()));
            pstStats.setInt(5, Integer.parseInt(wisField.getText()));
            pstStats.setInt(6, Integer.parseInt(chaField.getText()));
            pstStats.executeUpdate();

            ResultSet rsStats = pstStats.getGeneratedKeys();
            if (rsStats.next()) {
                statsId = rsStats.getInt(1);
            } else {
                throw new SQLException("Failed to get generated keys for Stats.");
            }
            rsStats.close();
            pstStats.close();

            String sqlCharacter = "INSERT INTO characters (campaign_id, name, isNPC, ac, hp, stats_id, bonds, fears, loves, hates) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstCharacter = conn.prepareStatement(sqlCharacter, PreparedStatement.RETURN_GENERATED_KEYS); // Get character_id
            pstCharacter.setInt(1, campaign.getId());
            pstCharacter.setString(2, nameField.getText());
            pstCharacter.setBoolean(3, type.isSelected());
            pstCharacter.setInt(4, Integer.parseInt(acField.getText()));
            pstCharacter.setInt(5, Integer.parseInt(hpField.getText()));
            pstCharacter.setInt(6, statsId);
            pstCharacter.setString(7, bondsField.getText());
            pstCharacter.setString(8, fearsField.getText());
            pstCharacter.setString(9, lovesField.getText());
            pstCharacter.setString(10, hatesField.getText());
            pstCharacter.executeUpdate();

            ResultSet rsCharacter = pstCharacter.getGeneratedKeys();
            int characterId = -1;
            if (rsCharacter.next()) {
                characterId = rsCharacter.getInt(1);
            } else {
                throw new SQLException("Failed to get generated keys for Character.");
            }
            rsCharacter.close();
            pstCharacter.close();

            String sqlCharacterSkill = "INSERT INTO characterSkill (character_id, skill_id) VALUES (?, ?)";
            PreparedStatement pstCharacterSkill = conn.prepareStatement(sqlCharacterSkill);

            for (Skill skill : selectedCharacterSkills) {
                pstCharacterSkill.setInt(1, characterId);
                pstCharacterSkill.setInt(2, skill.getId());
                pstCharacterSkill.addBatch();
            }
            pstCharacterSkill.executeBatch();
            pstCharacterSkill.close();

            conn.commit();

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Character created successfully!");
            alert.showAndWait();
            closeWindow();

        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter valid numbers for AC, HP, and Stats.");
            alert.showAndWait();
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }
}
