package org.example.service;

import org.example.model.Category;
import org.example.model.FoodItem;
import org.example.model.FoodSubtype;

import java.util.ArrayList;
import java.util.List;

/**
 * Service central de gestion des aliments.
 * Contient toute la logique de filtrage.
 */
public class FoodService {

    private static final List<FoodItem> foods = new ArrayList<>();

    public static void init(List<FoodItem> list) {
        foods.clear();
        foods.addAll(list);
    }

    // ===================== ALL =====================

    public static List<FoodItem> getAll() {
        return new ArrayList<>(foods);
    }

    // ===================== SEARCH ONLY =====================

    public static List<FoodItem> searchByName(String query) {

        return filter(query, null, null);
    }

    // ===================== FILTER COMPLET =====================

    public static List<FoodItem> filter(
            String query,
            Category category,
            FoodSubtype subtype
    ) {

        return foods.stream()
                .filter(f -> query == null
                        || query.isBlank()
                        || f.getName().toLowerCase().contains(query.toLowerCase()))

                .filter(f -> category == null
                        || f.getCategory() == category)

                .filter(f -> subtype == null
                        || f.getSubtype() == subtype)

                .toList();
    }
}