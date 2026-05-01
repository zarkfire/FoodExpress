package org.example.storage;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.example.model.CartItem;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;

public class CartStorage {

    private static final String FILE = "cart.json";
    private static final Gson gson = new Gson();

    public static void save(List<CartItem> items) {
        try (Writer writer = new FileWriter(FILE)) {
            gson.toJson(items, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<CartItem> load() {
        try (Reader reader = new FileReader(FILE)) {

            Type listType = new TypeToken<List<CartItem>>() {}.getType();
            List<CartItem> items = gson.fromJson(reader, listType);

            return items != null ? items : List.of();

        } catch (IOException e) {
            return List.of();
        }
    }
}