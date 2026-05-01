package org.example.model;

/**
 * Représente un aliment avec catégorie + sous-type.
 */
public class FoodItem {

    private final String name;
    private final double price;
    private final Category category;
    private final FoodSubtype subtype;

    public FoodItem(String name, double price, Category category, FoodSubtype subtype) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.subtype = subtype;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public Category getCategory() {
        return category;
    }

    public FoodSubtype getSubtype() {
        return subtype;
    }

    @Override
    public String toString() {
        return subtype + " - " + price + "€";
    }
}