package org.example.ui;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.example.model.CartItem;
import org.example.model.Order;
import org.example.service.CartService;
import org.example.service.OrderService;

import java.util.List;
import java.util.function.Consumer;

public class CartView {
    public static VBox createCart(
            ObservableList<CartItem> items,
            Runnable onClear,
            Consumer<CartItem> onIncrement,
            Consumer<CartItem> onDecrement,
            Consumer<CartItem> onRemove
    ) {

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
                        onIncrement.accept(item);
                        list.refresh();
                        updateTotal.run();
                    });

                    minus.setOnAction(e -> {
                        item.decrement();

                        if (item.getQuantity() <= 0) {
                            onRemove.accept(item);
                        }

                        list.refresh();
                        updateTotal.run();
                    });

                    delete.setOnAction(e -> {
                        onRemove.accept(item);
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
        Button order = getButton(updateTotal);

        // ===================== ASSEMBLY =====================
        card.getChildren().addAll(title, list, total, order);

        cart.getChildren().add(card);

        return cart;
    }

    private static Button getButton(Runnable updateTotal) {
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
            CartService cartInstance = CartService.getInstance();
            OrderService orderService = OrderService.getInstance();


            Order orderObj = new Order(
                    List.copyOf(cartInstance.getItems()),
                    cartInstance.getTotal()
            );

            orderService.addOrder(orderObj);

            cartInstance.clear();
            updateTotal.run();
        });
        return order;
    }
}
