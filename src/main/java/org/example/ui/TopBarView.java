package org.example.ui;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class TopBarView {
    public static HBox createTopBar(TextField searchField) {

        HBox topBar = new HBox();
        topBar.setPadding(new Insets(15));
        topBar.setSpacing(20);
        topBar.getStyleClass().add("top-bar");

        Label title = new Label("🍔 FoodExpress");
        title.getStyleClass().add("title");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        searchField.setPromptText("Rechercher un plat...");
        searchField.setPrefWidth(250);

        topBar.getChildren().addAll(title, spacer, searchField);

        return topBar;
    }

}
