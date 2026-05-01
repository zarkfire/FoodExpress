package org.example.model;

public class CartItem {
    private final FoodItem item;
    private int quantity;

    public CartItem(FoodItem item) {
        this.item = item;
        this.quantity = 1;
    }

    public void increment() {
        quantity++;
    }

    public void decrement() {
       quantity--;

    }

    public FoodItem getItem() { return item; }
    public int getQuantity() { return quantity; }

    public double getTotal() {
        return item.price() * quantity;
    }

    @Override
    public String toString() {
        return item.name() + " x" + quantity;
    }
}