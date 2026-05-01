package org.example.storage;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.model.Order;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class OrderStorage {

    private static final String FILE = "orders.json";
    private static final Gson gson = new Gson();

    public static void save(List<Order> orders) {
        try (Writer w = new FileWriter(FILE)) {
            gson.toJson(orders, w);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Order> load() {

        try {
            File file = new File(FILE);

            if (!file.exists() || file.length() == 0) {
                return new ArrayList<>();
            }

            try (Reader r = new FileReader(file)) {
                Type type = new TypeToken<List<Order>>() {}.getType();
                List<Order> orders = gson.fromJson(r, type);
                return orders != null ? orders : new ArrayList<>();
            }

        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    public static void initFile() {
        File file = new File(FILE);
        if (!file.exists()) {
            save(new ArrayList<>());
        }
    }

}