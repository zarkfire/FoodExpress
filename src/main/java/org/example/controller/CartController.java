package org.example.controller;

import javafx.collections.ObservableList;
import org.example.model.CartItem;
import org.example.model.FoodItem;
import org.example.service.CartService;

public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    public ObservableList<CartItem> getItems() {
        return cartService.getItems();
    }

    public void addItem(FoodItem item) {
        cartService.addItem(item);
    }

    public void removeItem(CartItem item) {
        cartService.removeItem(item);
    }

    public void increment(CartItem item) {
        cartService.increment(item);
    }

    public void decrement(CartItem item) {
        cartService.decrement(item);
        if (item.getQuantity() <= 0) {
            cartService.removeItem(item);
        }
    }

    public void clear() {
        cartService.clear();
    }

    public double getTotal() {
        return cartService.getTotal();
    }
}