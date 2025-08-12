package com.rpgmanager.controllers.utils;

import com.rpgmanager.models.Session;
import com.rpgmanager.utils.DatabaseManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.text.View;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SessionDetailsController {
    private Session session;
    private Stage stage;

    @FXML private Label sessionTittle;
    @FXML private VBox eventsContainer;
    @FXML private ListView<String> participantsList;

    public void setSession(Session session) {
        this.session = session;
        sessionTittle.setText(session.getName());
        loadSessionEvents();
        loadCharacters();

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void loadCharacters() {
        try (Connection conn = DatabaseManager.getConnection()) {
            String sql = "SELECT characters.name " +
                    "FROM characters " +
                    "JOIN characterSession " +
                    "WHERE characters.id = characterSession.character_id " +
                    "AND characterSession.session_id = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, session.getId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                participantsList.getItems().add(name);
                System.out.println("Agregado: " + name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Total en ListView: " + participantsList.getItems().size());
    }

    private void loadSessionEvents() {
        String sql = "SELECT * FROM eventNotes WHERE session_id = ?";

        try (Connection conn = com.rpgmanager.utils.DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setInt(1, session.getId());
            ResultSet rs = stmt.executeQuery();

            eventsContainer.getChildren().clear();

            while (rs.next()){
                String eventName = rs.getString("name");
                String description = rs.getString("description");

                Label nameLabel = new Label("» " + eventName);
                nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 13");

                Label descLabel = new Label(description);
                descLabel.setWrapText(true);
                descLabel.setStyle("-fx-font-size: 12");

                VBox eventBox = new VBox(nameLabel, descLabel);
                eventBox.setSpacing(2);
                eventBox.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 8; -fx-background-radius: 6;");

                eventsContainer.getChildren().add(eventBox);
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}