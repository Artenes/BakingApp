package com.artenesnogueira.bakingapp.model;

/**
 * An ingredient for a recipe.
 */
@SuppressWarnings("unused")
public class Ingredient {

    private float quantity;
    private String measure;
    private String ingredient;

    public float getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }

}