package org.example.ui;

import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.example.model.Category;
import org.example.model.FoodSubtype;

import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class FilterBarView {
    private static final Map<Category, Set<FoodSubtype>> SUBTYPE_MAP = Map.of(

            Category.BURGER, Set.of(
                    FoodSubtype.HAMBURGER,
                    FoodSubtype.CHEESEBURGER,
                    FoodSubtype.BACON_BURGER
            ),

            Category.PIZZA, Set.of(
                    FoodSubtype.MARGHERITA,
                    FoodSubtype.FOUR_CHEESE,
                    FoodSubtype.PEPPERONI
            ),

            Category.SUSHI, Set.of(
                    FoodSubtype.MAKI,
                    FoodSubtype.NIGIRI
            ),

            Category.DESSERT, Set.of(
                    FoodSubtype.TIRAMISU
            ),

            Category.FAST_FOOD, Set.of(
                    FoodSubtype.CHICKEN_BURGER
            )
    );

    public static HBox createFilterBar(
            Consumer<Category> onCategory,
            Consumer<FoodSubtype> onSubtype
    ) {

        HBox bar = new HBox(15);
        bar.setPadding(new Insets(10));

        Label label = new Label("Filtrer :");

        ComboBox<Category> categoryBox = new ComboBox<>();
        categoryBox.getItems().addAll(Category.values());
        categoryBox.setPromptText("Catégorie");

        ComboBox<FoodSubtype> subtypeBox = new ComboBox<>();
        subtypeBox.setPromptText("Sous-type");
        subtypeBox.setDisable(true);

        categoryBox.setOnAction(e -> {

            Category selected = categoryBox.getValue();
            onCategory.accept(selected);

            subtypeBox.getItems().clear();
            subtypeBox.setValue(null);

            if (selected != null && SUBTYPE_MAP.containsKey(selected)) {
                subtypeBox.getItems().addAll(SUBTYPE_MAP.get(selected));
                subtypeBox.setDisable(false);
            } else {
                subtypeBox.setDisable(true);
            }
        });

        subtypeBox.setOnAction(e ->
                onSubtype.accept(subtypeBox.getValue())
        );

        bar.getChildren().addAll(label, categoryBox, subtypeBox);

        return bar;
    }
}
