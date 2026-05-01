package org.example.ui;

import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.example.model.FoodItem;

import java.util.List;
import java.util.function.Consumer;

public class FoodListView {
    public static ScrollPane createFoodList() {

        TilePane grid = new TilePane();
        grid.setPadding(new Insets(20));
        grid.setHgap(20);
        grid.setVgap(20);

        ScrollPane scrollPane = new ScrollPane(grid);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setPannable(true);

        scrollPane.getStyleClass().add("food-scroll");

        return scrollPane;
    }

    public static void updateFoodList(
            ScrollPane scrollPane,
            List<FoodItem> foods,
            Consumer<FoodItem> onAdd
    ){
        TilePane grid = (TilePane) scrollPane.getContent();
        grid.getChildren().clear();

        for (FoodItem item : foods) {

            VBox card = new VBox(10);
            card.getStyleClass().add("food-card");
            card.setPadding(new Insets(10));

            Label name = new Label(item.name());
            Label price = new Label(item.price() + " €");

            Button add = new Button("Ajouter");
            add.setOnAction(e -> onAdd.accept(item));

            card.getChildren().addAll(name, price, add);
            grid.getChildren().add(card);

            // ===================== ANIMATION FADE-IN =====================
            card.setOpacity(0);

            FadeTransition ft = new FadeTransition(Duration.millis(250), card);
            ft.setFromValue(0);
            ft.setToValue(1);
            ft.play();
        }
    }
}
