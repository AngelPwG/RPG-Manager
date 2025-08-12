package com.rpgmanager.controllers.utils;

import com.rpgmanager.models.Campaign;
import com.rpgmanager.utils.DatabaseManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RollDiceChatController {

    @FXML
    private ListView<String> chatList;

    @FXML
    private TextField inputCommand;

    private SessionStartedController sessionStartedController;
    private Campaign campaign;
    private int sessionId = -1;

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    public void setSession(int sessionId){
        this.sessionId = sessionId;
    }

    public void setSessionStartedController(SessionStartedController sessionStartedController){
        this.sessionStartedController = sessionStartedController;
    }

    @FXML
    private void onRoll() {
        String input = inputCommand.getText().trim();
        if (input.isEmpty()) return;

        String personaje = null;
        String command;

        if (input.contains(":")) {
            String[] partes = input.split(":", 2);
            personaje = partes[0].trim();
            command = partes[1].trim();
        } else {
            command = input;
        }

        String rs = rollInterpretation(command);
        if (rs == null) {
            chatList.getItems().add("[Error] Comando invalido: " + command);
            return;
        }

        String message = (personaje != null ? personaje : "DM") + ": " + command + " → " + rs;
        chatList.getItems().add(message);

        if (sessionStartedController != null ) sessionStartedController.addRollDice(message);

        saveOnDB(personaje, command, rs);
        inputCommand.clear();
    }

    private String rollInterpretation(String comando) {
        Pattern pattern = Pattern.compile("(\\d*)d(\\d+)([+\\-]?\\d*)");
        Matcher matcher = pattern.matcher(comando);
        if (!matcher.matches()) return null;

        int quantity = matcher.group(1).isEmpty() ? 1 : Integer.parseInt(matcher.group(1));
        int faces = Integer.parseInt(matcher.group(2));
        int mod = matcher.group(3).isEmpty() ? 0 : Integer.parseInt(matcher.group(3));

        Random rand = new Random();
        int total = 0;
        StringBuilder detail = new StringBuilder("[");
        for (int i = 0; i < quantity; i++) {
            int roll = rand.nextInt(faces) + 1;
            total += roll;
            detail.append(roll);
            if (i < quantity - 1) detail.append(", ");
        }
        total += mod;
        detail.append("]");

        return detail + (mod != 0 ? (mod > 0 ? " +" : " ") + mod : "") + " = " + total;
    }

    private void saveOnDB(String character, String command, String result) {
        try (Connection conn = DatabaseManager.getConnection()) {
            Integer characterId = null;
            if (character != null) {
                PreparedStatement find = conn.prepareStatement(
                        "SELECT id FROM characters WHERE lower(name) = lower(?) AND campaign_id = ?");
                find.setString(1, character);
                find.setInt(2, campaign.getId());
                java.sql.ResultSet rs = find.executeQuery();
                if (rs.next()) characterId = rs.getInt("id");
            }

            PreparedStatement insert = conn.prepareStatement(
                    "INSERT INTO rolls (campaign_id, session_id,character_id, type, result, date_time) VALUES (?, ?, ?, ?, ?, ?)");
            insert.setInt(1, campaign.getId());
            if (sessionId != -1) insert.setInt(2, sessionId); else insert.setNull(2, java.sql.Types.INTEGER);
            if (characterId != null) insert.setInt(3, characterId); else insert.setNull(3, java.sql.Types.INTEGER);
            insert.setString(4, command);
            insert.setString(5, result);
            insert.setString(6, LocalDateTime.now().toString());
            insert.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
