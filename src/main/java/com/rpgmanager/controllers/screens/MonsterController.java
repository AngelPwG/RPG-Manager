package com.rpgmanager.controllers.screens;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import com.rpgmanager.models.Monsters;
import com.rpgmanager.utils.DatabaseManager;
import javafx.scene.text.Text;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class MonsterController extends GoToController{

    @FXML
    private TextField searchBar;

    @FXML
    private TableView<Monsters> monstersTable;

    @FXML
    private TableColumn<Monsters, String> colName;

    @FXML
    private TableColumn<Monsters, Double> colHP;

    @FXML
    private TableColumn<Monsters, Double> colAC;

    @FXML
    private TableColumn<Monsters, Integer> colStrength;

    @FXML
    private TableColumn<Monsters, Integer> colDexterity;

    @FXML
    private TableColumn<Monsters, Integer> colConstitution;

    @FXML
    private TableColumn<Monsters, Integer> colIntelligence;

    @FXML
    private TableColumn<Monsters, Integer> colWisdom;

    @FXML
    private TableColumn<Monsters, Integer> colCharisma;

    @FXML
    private TableColumn<Monsters, String> colDesc;

    private final ObservableList<Monsters> monstersList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colHP.setCellValueFactory(new PropertyValueFactory<>("hp"));
        colAC.setCellValueFactory(new PropertyValueFactory<>("ac"));

        colStrength.setCellValueFactory(new PropertyValueFactory<>("strength"));
        colDexterity.setCellValueFactory(new PropertyValueFactory<>("dexterity"));
        colConstitution.setCellValueFactory(new PropertyValueFactory<>("constitution"));
        colIntelligence.setCellValueFactory(new PropertyValueFactory<>("intelligence"));
        colWisdom.setCellValueFactory(new PropertyValueFactory<>("wisdom"));
        colCharisma.setCellValueFactory(new PropertyValueFactory<>("charisma"));

        colDesc.setCellFactory(tc -> {
            TableCell<Monsters, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);

            text.wrappingWidthProperty().bind(colDesc.widthProperty());

            cell.itemProperty().addListener((obs, oldVal, newVal) -> {
                text.setText(newVal);
                cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            });
            return cell;
        });

        loadMonsters("");
    }

    @FXML
    private void onSearch() {
        String text = searchBar.getText().trim().toLowerCase();
        loadMonsters(text);
    }

    private void loadMonsters(String filter) {
        monstersList.clear();

        String sql = "SELECT m.id, m.name, m.description, m.hp, m.ac, s.strength, s.dexterity, s.constitution, s.intelligence, s.wisdom, s.charisma " +
                "FROM monsters m JOIN Stats s ON m.stats_id = s.id";

        if (!filter.isEmpty()) {
            sql += " WHERE lower(m.name) LIKE '%" + filter + "%'";
        }

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Monsters m = new Monsters(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("hp"),
                        rs.getInt("ac"),
                        rs.getString("description"),
                        rs.getInt("strength"),
                        rs.getInt("dexterity"),
                        rs.getInt("constitution"),
                        rs.getInt("intelligence"),
                        rs.getInt("wisdom"),
                        rs.getInt("charisma")
                );
                monstersList.add(m);
            }
            monstersTable.setItems(monstersList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
