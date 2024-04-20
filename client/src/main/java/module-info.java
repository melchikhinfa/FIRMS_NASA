module org.firms.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;
    requires com.gluonhq.maps;


    opens org.firms.client to javafx.fxml;
    opens org.firms.client.controllers to javafx.fxml;
    opens org.firms.client.tableEntities to javafx.fxml;

    exports org.firms.client;
    exports org.firms.client.controllers;
    exports org.firms.client.utils;
    exports org.firms.client.jsonEntities.in.user;
    exports org.firms.client.jsonEntities.out.user;
    exports org.firms.client.jsonEntities.out.subscription;
    exports org.firms.client.jsonEntities.out.region;
    exports org.firms.client.tableEntities;

}