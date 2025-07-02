module com.rpgmanager {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.rpgmanager to javafx.fxml;
    exports com.rpgmanager;
    exports com.rpgmanager.models;

    exports com.rpgmanager.controllers;
    opens com.rpgmanager.models to javafx.base;
    opens com.rpgmanager.controllers to javafx.base, javafx.fxml;
    exports com.rpgmanager.controllers.utils;
    opens com.rpgmanager.controllers.utils to javafx.base, javafx.fxml;
}