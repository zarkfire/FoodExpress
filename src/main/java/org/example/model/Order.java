package org.example.model;

import java.time.LocalDateTime;
import java.util.List;

public class Order {

    private List<CartItem> items;
    private double total;
    private String date;
    public Order() {}

    public Order(List<CartItem> items, double total) {
        this.items = items;
        this.total = total;
        this.date = String.valueOf(LocalDateTime.now());
    }

    public List<CartItem> getItems() {
        return items;
    }

    public double getTotal() {
        return total;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Commande du " + date + " - " + total + "€";
    }
}