package com.rpgmanager.controllers.utils;

import com.rpgmanager.controllers.screens.SessionController;
import com.rpgmanager.models.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Optional;

public class SessionCardController {


    @FXML private Label labelCreated;
    @FXML private Label labelEnded;
    @FXML private Label labelName;
    @FXML
    private Label labelResume;

    private Session session;

    public void setSession(Session session) {
        this.session = session;
        labelCreated.setText("Inició: " + session.getCreatedAt());
        labelEnded.setText("Terminó: " + session.getEndedAt());
        labelName.setText(session.getName());
        labelResume.setText(session.getResume() == null || session.getResume().isEmpty()
                ? "(sin resumen final)"
                : session.getResume());
        this.session = session;
    }

    SessionController sessionController;

    public void setSessionController(SessionController sessionController) {
        this.sessionController = sessionController;
    }

    @FXML
    private void showDetails() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/utils/session_details.fxml"));
            Parent root = loader.load();

            SessionDetailsController controller = loader.getController();
            controller.setSession(session);


            Stage stage = new Stage();
            controller.setStage(stage);
            stage.setTitle("Detalles de sessión - " + session.getName());
            stage.setScene(new Scene(root, 350, 550));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR,"Error al cargar la ventana de detalles de la sesión.");
            alert.showAndWait();
        }
    }

    public void deleteSession() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmacion");
        alert.setHeaderText("¿Estas seguro de que quieres eliminar la sesión " + session.getName() + "?");
        alert.setContentText("Esta acción no se puede deshacer");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            String sql = "DELETE FROM sessions WHERE id = ?";

            try (Connection conn = com.rpgmanager.utils.DatabaseManager.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)){

                stmt.setInt(1, session.getId());
                stmt.executeUpdate();

            } catch (Exception e) {
                e.printStackTrace();
            }
            if (sessionController != null){
                sessionController.loadSessions();
            }
        }
    }
}
