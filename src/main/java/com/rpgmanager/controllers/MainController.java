package com.rpgmanager.controllers;

import com.rpgmanager.utils.CampaignAware;
import com.rpgmanager.utils.MainAware;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.Node;
import com.rpgmanager.models.Campaign;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private VBox sidebar;

    @FXML
    private BorderPane mainContent;

    private Campaign currentCampaign;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Sidebar.fxml"));
            Node sidebarNode = loader.load();
            SidebarController sidebarController = loader.getController();
            sidebarController.setMainController(this);
            sidebar.getChildren().setAll(sidebarNode);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setCampaign(Campaign campaign) {
        this.currentCampaign = campaign;
    }

    public void setContent(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Node content = loader.load();
            mainContent.setCenter(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setContentWithCampaign(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Node content = loader.load();

            Object controller = loader.getController();
            if (controller instanceof CampaignAware) {
                ((CampaignAware) controller).setCampaign(currentCampaign);
            }
            if (controller instanceof MainAware) {
                System.out.println("Main Controller setteado");
                ((MainAware) controller).setMainController(this);
                ((MainAware) controller).initData();
            }

            mainContent.setCenter(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Campaign getCurrentCampaign(){
        return  currentCampaign;
    }
}