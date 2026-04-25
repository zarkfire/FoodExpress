module org.example.tp9mvn {

    requires javafx.controls;
    requires javafx.fxml;

    // ✔ IMPORTANT : ton Main est ici
    exports org.example;

    opens org.example to javafx.graphics;

    opens org.example.model to javafx.base;
    opens org.example.service to javafx.base;
}