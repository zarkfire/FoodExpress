package org.example.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.model.Order;
import org.example.storage.OrderStorage;

import java.util.ArrayList;

public class OrderService {

    private static final OrderService instance = new OrderService();

    private final ObservableList<Order> orders =
            FXCollections.observableArrayList();

    private OrderService() {
        OrderStorage.initFile();
        orders.addAll(OrderStorage.load());
    }

    public static OrderService getInstance() {
        return instance;
    }

    public void addOrder(Order order) {
        orders.add(order);
        save();
    }

    public ObservableList<Order> getOrders() {
        return orders;
    }

    private void save() {
        OrderStorage.save(new ArrayList<>(orders));
    }
}