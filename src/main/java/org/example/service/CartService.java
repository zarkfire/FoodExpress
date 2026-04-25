package org.example.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.model.CartItem;
import org.example.model.FoodItem;

public class CartService {

    private static final CartService instance = new CartService();

    private ObservableList<CartItem> items = FXCollections.observableArrayList();

    public static CartService getInstance() {
        return instance;
    }

    public void addItem(FoodItem food) {
        for (CartItem ci : items) {
            if (ci.getItem().getName().equals(food.getName())) {
                ci.increment();
                items.set(items.indexOf(ci), ci); // force refresh UI
                return;
            }
        }
        items.add(new CartItem(food));
    }

    public void removeItem(CartItem item) {
        items.remove(item);
    }

    public void clear() {
        items.clear();
    }

    public ObservableList<CartItem> getItems() {
        return items;
    }

    public double getTotal() {
        return items.stream().mapToDouble(CartItem::getTotal).sum();
    }
}