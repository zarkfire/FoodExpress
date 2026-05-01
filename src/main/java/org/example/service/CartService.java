package org.example.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.model.CartItem;
import org.example.model.FoodItem;
import org.example.storage.CartStorage;

public class CartService {

    public CartService() {
        items.addAll(CartStorage.load());
    }

    private static final CartService instance = new CartService();

    private final ObservableList<CartItem> items = FXCollections.observableArrayList();

    public static CartService getInstance() {
        return instance;
    }

    public void addItem(FoodItem food) {
        for (CartItem ci : items) {
            if (ci.getItem().name().equals(food.name())) {
                ci.increment();
                items.set(items.indexOf(ci), ci);
                save();
                return;
            }
        }

        items.add(new CartItem(food));
        save();
    }

    public void increment(CartItem item) {
        item.increment();
        items.set(items.indexOf(item), item);
        save();
    }

    public void decrement(CartItem item) {
        item.decrement();

        if (item.getQuantity() <= 0) {
            items.remove(item);
        }

        save();
    }

    public void removeItem(CartItem item) {
        items.remove(item);
        CartStorage.save(items);
    }

    public void clear() {
        items.clear();
        CartStorage.save(items);
    }

    public ObservableList<CartItem> getItems() {
        return items;
    }

    private void save() {
        CartStorage.save(items);
    }

    public double getTotal() {
        return items.stream().mapToDouble(CartItem::getTotal).sum();
    }

    public void command(){

    }
}