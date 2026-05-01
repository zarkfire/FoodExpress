package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.example.controller.CartController;
import org.example.model.Category;
import org.example.model.FoodItem;
import org.example.model.FoodSubtype;
import org.example.service.CartService;
import org.example.service.FoodService;

import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        System.out.println("APP STARTING...");
        BorderPane root = new BorderPane();

        // ================= DATA =================
        List<FoodItem> allFoods = List.of(

                // ================= BURGERS =================
                new FoodItem("Hamburger Classic", 8.99, Category.BURGER, FoodSubtype.HAMBURGER),
                new FoodItem("Cheeseburger", 9.50, Category.BURGER, FoodSubtype.CHEESEBURGER),
                new FoodItem("Bacon Burger", 10.50, Category.BURGER, FoodSubtype.BACON_BURGER),

                // ================= PIZZAS =================
                new FoodItem("Margherita", 12.50, Category.PIZZA, FoodSubtype.MARGHERITA),
                new FoodItem("Four Cheese", 13.90, Category.PIZZA, FoodSubtype.FOUR_CHEESE),
                new FoodItem("Pepperoni", 14.50, Category.PIZZA, FoodSubtype.PEPPERONI),

                // ================= SUSHI =================
                new FoodItem("Maki Salmon", 14.90, Category.SUSHI, FoodSubtype.MAKI),
                new FoodItem("Nigiri Set", 15.90, Category.SUSHI, FoodSubtype.NIGIRI),

                // ================= FAST FOOD =================
                new FoodItem("Chicken Burger", 9.50, Category.FAST_FOOD, FoodSubtype.CHICKEN_BURGER),
                new FoodItem("Fries XL", 4.50, Category.FAST_FOOD, FoodSubtype.CHICKEN_BURGER),

                // ================= DESSERTS =================
                new FoodItem("Tiramisu", 6.50, Category.DESSERT, FoodSubtype.TIRAMISU),
                new FoodItem("Cheesecake", 6.90, Category.DESSERT, FoodSubtype.TIRAMISU),

                // ================= DRINKS =================
                new FoodItem("Coca Cola", 2.50, Category.DRINK, FoodSubtype.COLA),
                new FoodItem("Water", 1.50, Category.DRINK, FoodSubtype.WATER),
                new FoodItem("Orange Juice", 3.00, Category.DRINK, FoodSubtype.JUICE),
                new FoodItem("Jules's Tears", 3.55, Category.DRINK, FoodSubtype.JUICE)
        );

        FoodService.init(allFoods);

        // ================= UI =================
        ScrollPane foodScrollPane = UIComponents.createFoodList();
        TextField searchField = new TextField();

        // ================= FILTER STATE =================
        final Category[] selectedCategory = {null};
        final FoodSubtype[] selectedSubtype = {null};

        // ================= FILTER FUNCTION =================
        Runnable refresh = () -> {

            var filtered = allFoods.stream()
                    .filter(f -> searchField.getText() == null
                            || searchField.getText().isBlank()
                            || f.getName().toLowerCase()
                            .contains(searchField.getText().toLowerCase()))

                    .filter(f -> selectedCategory[0] == null
                            || f.getCategory() == selectedCategory[0])

                    .filter(f -> selectedSubtype[0] == null
                            || f.getSubtype() == selectedSubtype[0])

                    .toList();

            CartController cartController = new CartController(CartService.getInstance());

            UIComponents.updateFoodList(
                    foodScrollPane,
                    filtered,
                    cartController::addItem
            );
        };

        // ================= SEARCH =================
        searchField.textProperty().addListener((obs, oldVal, newVal) -> refresh.run());

        // ================= FILTER BAR =================
        var filterBar = UIComponents.createFilterBar(
                cat -> {
                    selectedCategory[0] = cat;
                    selectedSubtype[0] = null;
                    refresh.run();
                },
                sub -> {
                    selectedSubtype[0] = sub;
                    refresh.run();
                }
        );

        // ================= SIDEBAR =================
        var sidebar = UIComponents.createSidebar(cat -> {
            selectedCategory[0] = cat.equals("ALL") ? null : Category.valueOf(cat);
            selectedSubtype[0] = null;
            refresh.run();
        });

        // ================= LAYOUT =================
        BorderPane leftPanel = new BorderPane();
        leftPanel.setTop(sidebar);
        leftPanel.setCenter(filterBar);

        root.setTop(UIComponents.createTopBar(searchField));
        root.setLeft(leftPanel);
        root.setCenter(foodScrollPane);
        CartService cartService = CartService.getInstance();
        CartController cartController = new CartController(cartService);

        root.setRight(UIComponents.createCart(
                cartController.getItems(),
                cartController::clear,
                cartController::increment,
                cartController::decrement,
                cartController::removeItem
        ));

        // ================= SCENE =================
        Scene scene = new Scene(root, 1200, 700);
        var css = getClass().getResource("/styles/style.css");
        if (css != null) {
            scene.getStylesheets().add(css.toExternalForm());
        } else {
            System.out.println("CSS NOT FOUND");
        }

        stage.setTitle("Food Delivery App");
        stage.setScene(scene);
        stage.show();

        // ================= INIT =================
        refresh.run();
    }

    public static void main(String[] args) {
        launch();
    }
}