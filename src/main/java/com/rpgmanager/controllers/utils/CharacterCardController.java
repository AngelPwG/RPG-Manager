package com.rpgmanager.controllers.utils;

import com.rpgmanager.controllers.screens.CharactersController;
import com.rpgmanager.models.Campaign;
import com.rpgmanager.models.Character;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Optional;

public class CharacterCardController {
    @FXML private Label nameLabel;
    @FXML private Label typeLabel;
    @FXML private Label acLabel;
    @FXML private Label hpLabel;
    @FXML private Label descriptionLabel;

    private com.rpgmanager.models.Character character;
    private Campaign campaign;

    public void setCharacter(Character character) {
        this.character = character;
        nameLabel.setText(character.getName());
        typeLabel.setText((character.isNPC())? "NPC" : "PC");
        acLabel.setText(String.valueOf(character.getAc()));
        hpLabel.setText(String.valueOf(character.getHp()));
        descriptionLabel.setText(character.getBonds()+ "\n" + character.getFears() + "\n" + character.getLoves() + "\n" + character.getHates());
    }
    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    private CharactersController parentController;

    public void setParentController(CharactersController controller){
        this.parentController = controller;
    }

    @FXML
    private void onEdit() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/utils/character_detail.fxml"));
            Parent root = loader.load();

            CharacterDetailController controller = loader.getController();
            controller.setCharacter(character);

            Stage stage = new Stage();
            controller.setStage(stage);

            stage.setTitle("Detalles de personaje - " + character.getName());
            stage.setScene(new Scene(root, 350, 615));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            if (parentController != null){
                parentController.loadCharacters();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error cargando la ventana de Detalles de personaje.");
            alert.showAndWait();
        }
    }

    @FXML
    private void deleteCharacter() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmar eliminacion");
        alert.setHeaderText("¿Estás seguro que quieres eliminar a " + character.getName() + "?");
        alert.setContentText("Esta acción no se puede deshacer");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            String sql = "DELETE FROM characters WHERE id = ?";

            try (Connection conn = com.rpgmanager.utils.DatabaseManager.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql))
            {
                ps.setInt(1, character.getId());
                ps.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (parentController != null){
                parentController.loadCharacters();
            }
        }

    }

}
