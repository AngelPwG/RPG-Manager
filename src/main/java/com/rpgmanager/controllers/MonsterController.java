package com.rpgmanager.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MonsterController {
    TableView<User> table = new TableView<>();


    @FXML
    TableColumn<User, String> nameCol = new TableColumn<>("Name");

    @FXML
    TableColumn
    @Override
    public void start(Stage stage) {
        TableColumn<User, String> nameCol = new TableColumn<>("Nombre");
        nameCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().name));

        TableColumn<User, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().email));

        table.getColumns().addAll(nameCol, emailCol);
        table.setItems(getUsers());

        VBox root = new VBox(table);
        Scene scene = new Scene(root, 400, 300);
        stage.setTitle("Usuarios desde SQLite");
        stage.setScene(scene);
        stage.show();
    }

    // Cargar usuarios desde la base de datos
    public ObservableList<User> getUsers() {
        ObservableList<User> list = FXCollections.observableArrayList();
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:users.db");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT name, email FROM users")) {

            while (rs.next()) {
                list.add(new User(rs.getString("name"), rs.getString("email")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public static class User {
        String name;
        String email;

        User(String name, String email) {
            this.name = name;
            this.email = email;
        }
    }
}
