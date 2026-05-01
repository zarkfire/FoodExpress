package org.example.ui;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.function.Consumer;

public class SideBarView {
    public static VBox createSidebar(Consumer<String> onCategorySelected) {

        VBox sidebar = new VBox(15);
        sidebar.setPadding(new Insets(15));
        sidebar.getStyleClass().add("sidebar");

        sidebar.getChildren().add(new Label("Catégories"));

        Button all = new Button("Tous");
        Button pizza = new Button("Pizza");
        Button burger = new Button("Burgers");
        Button sushi = new Button("Sushi");
        Button dessert = new Button("Desserts");

        all.setOnAction(e -> onCategorySelected.accept("ALL"));
        pizza.setOnAction(e -> onCategorySelected.accept("PIZZA"));
        burger.setOnAction(e -> onCategorySelected.accept("BURGER"));
        sushi.setOnAction(e -> onCategorySelected.accept("SUSHI"));
        dessert.setOnAction(e -> onCategorySelected.accept("DESSERT"));

        sidebar.getChildren().addAll(all, pizza, burger, sushi, dessert);

        return sidebar;
    }

}
