package com.rpgmanager.controllers.utils;

import com.rpgmanager.models.Session;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class SessionCardController {

    @FXML private Label labelCreated;
    @FXML private Label labelEnded;
    @FXML private Label labelName;
    @FXML
    private Label labelResume;

    private Session session;

    public void setSession(Session session) {
        this.session = session;
        labelCreated.setText("Created Date: " + session.getCreatedAt());
        labelEnded.setText("Ended Date: " + session.getEndedAt());
        labelName.setText(session.getName());
        labelResume.setText(session.getResume() == null || session.getResume().isEmpty()
                ? "(without final resume)"
                : session.getResume());
        this.session = session;
    }

    @FXML
    private void showDetails() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Session Details");
        alert.setHeaderText(null);
        alert.setContentText("Function not implemented yet.");
        alert.showAndWait();
    }
}
