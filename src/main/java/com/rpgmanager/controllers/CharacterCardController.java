package com.rpgmanager.controllers;

import com.rpgmanager.models.Character;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

public class CharacterCardController {
    @FXML private Label nameLabel;
    @FXML private Label typeLabel;
    @FXML private Label acLabel;
    @FXML private Label hpLabel;
    @FXML private Label descriptionLabel;

    private com.rpgmanager.models.Character character;

    public void setCharacter(Character character) {
        this.character = character;
        nameLabel.setText(character.getName());
        typeLabel.setText((character.isNPC())? "NPC" : "PC");
        acLabel.setText(String.valueOf(character.getAc()));
        hpLabel.setText(String.valueOf(character.getHp()));
        descriptionLabel.setText(character.getBonds());
    }

    @FXML
    private void onEdit() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Edit Character");
        alert.setHeaderText(null);
        alert.setContentText("Function not implemented yet.");
        alert.showAndWait();
    }
}
