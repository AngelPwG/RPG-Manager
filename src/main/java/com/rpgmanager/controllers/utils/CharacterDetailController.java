package com.rpgmanager.controllers.utils;

import com.rpgmanager.models.Character;
import com.rpgmanager.utils.DatabaseManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CharacterDetailController {

    private Character character;

    @FXML public Button editButton;
    @FXML public Button saveButton;

    @FXML private TextField nameField;
    @FXML private CheckBox isNpcBox;
    @FXML private TextField acField;
    @FXML private TextField hpField;
    @FXML private TextField bondsField;
    @FXML private TextField fearsField;
    @FXML private TextField lovesField;
    @FXML private TextField hatesField;

    public void setCharacter(Character character) {
        this.character = character;
        nameField.setText(character.getName());
        isNpcBox.setSelected(character.isNPC());
        acField.setText(String.valueOf(character.getAc()));
        hpField.setText(String.valueOf(character.getHp()));
        bondsField.setText(character.getBonds());
        fearsField.setText(character.getFears());
        lovesField.setText(character.getLoves());
        hatesField.setText(character.getHates());
    }

    @FXML private void toggleEditMode(){
        boolean editing = true;
        nameField.setDisable(!editing);
        isNpcBox.setDisable(!editing);
        acField.setDisable(!editing);
        hpField.setDisable(!editing);
        bondsField.setDisable(!editing);
        fearsField.setDisable(!editing);
        lovesField.setDisable(!editing);
        hatesField.setDisable(!editing);
        editButton.setVisible(false);
        saveButton.setVisible(true);
    }

    @FXML private void saveCharacterChanges(){
        if (character == null) return;

        character.setName(nameField.getText());
        character.setNPC(isNpcBox.isSelected());
        character.setAc(Integer.valueOf(acField.getText()));
        character.setHp(Integer.valueOf(hpField.getText()));
        character.setBonds(bondsField.getText());
        character.setFears(fearsField.getText());
        character.setLoves(lovesField.getText());
        character.setHates(hatesField.getText());

        boolean editing = false;
        nameField.setDisable(!editing);
        isNpcBox.setDisable(!editing);
        acField.setDisable(!editing);
        hpField.setDisable(!editing);
        bondsField.setDisable(!editing);
        fearsField.setDisable(!editing);
        lovesField.setDisable(!editing);
        hatesField.setDisable(!editing);
        editButton.setVisible(true);
        saveButton.setVisible(false);

        updateCharacterInDatabase(this.character);
    }



    private void updateCharacterInDatabase(Character character)  {
        String sqlupdateCharacter = "update characters set name = ?, isNPC = ?, ac = ?, hp = ?, bonds = ?, fears = ?, loves = ?, hates = ? where id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlupdateCharacter)){

            stmt.setString(1, character.getName());
            stmt.setBoolean(2, isNpcBox.isSelected());
            stmt.setInt(3, character.getAc());
            stmt.setInt(4, character.getHp());
            stmt.setString(5, character.getBonds());
            stmt.setString(6, character.getFears());
            stmt.setString(7, character.getLoves());
            stmt.setString(8, character.getHates());
            stmt.setInt(9, character.getId());

            int affected = stmt.executeUpdate();
            if (affected > 0) {
                System.out.println("Personaje actualizado correctamente");
            } else {
                System.out.println("No se encontró ningún personaje con ese id");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al actualizar el personaje");
        }
    }


    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void onClose() {
        stage.close();
    }

}
