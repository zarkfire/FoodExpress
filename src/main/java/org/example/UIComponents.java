package org.example;

import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Duration;
import org.example.model.*;

import org.example.service.CartService;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Composants UI de l'application FoodExpress.
 */
public class UIComponents {

    // ===================== TOP BAR =====================

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

    // ===================== SIDEBAR =====================

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

    // ===================== FOOD LIST =====================

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

    public static void updateFoodList(ScrollPane scrollPane, List<FoodItem> foods) {

        TilePane grid = (TilePane) scrollPane.getContent();
        grid.getChildren().clear();

        for (FoodItem item : foods) {

            VBox card = new VBox(10);
            card.getStyleClass().add("food-card");
            card.setPadding(new Insets(10));

            Label name = new Label(item.getName());
            Label price = new Label(item.getPrice() + " €");

            Button add = new Button("Ajouter");

            add.setOnAction(e ->
                    CartService.getInstance().addItem(item)
            );

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

    // ===================== CART =====================

    public static VBox createCart() {

        VBox cart = new VBox(12);
        cart.getStyleClass().add("cart");
        cart.setPadding(new Insets(15));

        // ===================== CARD WRAPPER =====================
        VBox card = new VBox(10);
        card.setPadding(new Insets(15));
        card.setStyle("""
        -fx-background-color: white;
        -fx-background-radius: 18;
        -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.12), 20, 0, 0, 6);
    """);

        // ===================== TITLE =====================
        Label title = new Label("🛒 Mon panier");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // ===================== LIST =====================
        ListView<CartItem> list = new ListView<>();
        list.setItems(CartService.getInstance().getItems());

        list.setPrefHeight(350);
        list.setStyle("""
        -fx-background-color: transparent;
        -fx-border-color: transparent;
    """);

        // ===================== TOTAL =====================
        Label total = new Label();

        Runnable updateTotal = () ->
                total.setText("Total: " + CartService.getInstance().getTotal() + " €");

        total.setStyle("""
        -fx-font-size: 16px;
        -fx-font-weight: bold;
        -fx-text-fill: #ff4b2b;
    """);

        // ===================== CELL FACTORY =====================
        list.setCellFactory(param -> new ListCell<>() {

            @Override
            protected void updateItem(CartItem item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {

                    HBox box = new HBox(10);
                    box.setPadding(new Insets(8));

                    Label label = new Label(item.toString());
                    label.setStyle("-fx-font-size: 13px; -fx-text-fill: #333;");

                    Region spacer = new Region();
                    HBox.setHgrow(spacer, Priority.ALWAYS);

                    Button plus = new Button("+");
                    Button minus = new Button("-");
                    Button delete = new Button("✖");

                    String btnStyle = """
                    -fx-background-radius: 10;
                    -fx-padding: 4 10;
                """;

                    plus.setStyle(btnStyle);
                    minus.setStyle(btnStyle);
                    delete.setStyle(btnStyle + "-fx-background-color: #ff4b2b; -fx-text-fill: white;");

                    plus.setOnAction(e -> {
                        item.increment();
                        list.refresh();
                        updateTotal.run();
                    });

                    minus.setOnAction(e -> {
                        item.decrement();

                        if (item.getQuantity() <= 0) {
                            CartService.getInstance().removeItem(item);
                        }

                        list.refresh();
                        updateTotal.run();
                    });

                    delete.setOnAction(e -> {
                        CartService.getInstance().removeItem(item);
                        list.refresh();
                        updateTotal.run();
                    });

                    box.getChildren().addAll(label, spacer, plus, minus, delete);
                    setGraphic(box);
                }
            }
        });

        // ===================== UPDATE TOTAL =====================
        CartService.getInstance().getItems()
                .addListener((javafx.collections.ListChangeListener<CartItem>) c -> updateTotal.run());

        updateTotal.run();

        // ===================== ORDER BUTTON =====================
        Button order = new Button("Commander");
        order.setStyle("""
        -fx-background-color: linear-gradient(to right, #ff416c, #ff4b2b);
        -fx-text-fill: white;
        -fx-background-radius: 20;
        -fx-padding: 10 15;
        -fx-font-weight: bold;
    """);

        order.setMaxWidth(Double.MAX_VALUE);

        order.setOnAction(e -> {
            CartService.getInstance().clear();
            updateTotal.run();
        });

        // ===================== ASSEMBLY =====================
        card.getChildren().addAll(title, list, total, order);

        cart.getChildren().add(card);

        return cart;
    }

    // ===================== FILTER BAR =====================

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