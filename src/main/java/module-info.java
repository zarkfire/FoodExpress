module org.example.tp9mvn {

    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires com.google.gson;


    exports org.example;
    exports org.example.model;
    exports org.example.service;

    opens org.example to javafx.graphics;
    opens org.example.model to com.google.gson, javafx.base;
    opens org.example.service to javafx.base;
    exports org.example.ui;
    opens org.example.ui to javafx.graphics;
}