package com.rpgmanager.controllers.screens;

import com.rpgmanager.models.Items;
import com.rpgmanager.utils.DatabaseManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ItemsController extends GoToController{

    @FXML
    private TextField searchBar;

    @FXML
    private TableView<Items> itemsTable;

    @FXML
    private TableColumn<Items, String> colName;

    @FXML
    private TableColumn<Items, String> colDescription;

    @FXML
    private TableColumn<Items, String> colCategory;

    @FXML
    private TableColumn<Items, String> colDamageDice;

    private final ObservableList<Items> itemsList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colDamageDice.setCellValueFactory(new PropertyValueFactory<>("damage_dice"));

        colDescription.setCellFactory(tc -> {
            TableCell<Items, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);

            text.wrappingWidthProperty().bind(colDescription.widthProperty());


            cell.itemProperty().addListener((obs, oldVal, newVal) -> {
                text.setText(newVal);

                cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            });
            return cell;
        });

        loadItems("");
    }

    @FXML
    private void onSearch() {
        String text = searchBar.getText().trim().toLowerCase();
        loadItems(text);
    }

    private void loadItems(String filtro) {
        itemsList.clear();

        String sql = "SELECT * FROM items";

        if (!filtro.isEmpty()) {
            sql += " WHERE lower(name) LIKE '%" + filtro + "%'";
        }

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Items item = new Items(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getString("description"),
                        rs.getString("damage_dice")
                );
                itemsList.add(item);
            }
            itemsTable.setItems(itemsList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
