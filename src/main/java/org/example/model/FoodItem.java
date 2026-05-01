package org.example.model;

/**
 * Représente un aliment avec catégorie + sous-type.
 */
public record FoodItem(String name, double price, Category category, FoodSubtype subtype) {

    @Override
    public String toString() {
        return subtype + " - " + price + "€";
    }
}