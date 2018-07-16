package com.artenesnogueira.bakingapp.model;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Resumed version of the recipe, used to display in the widget
 */
public class ResumedRecipe {

    private int id;
    private String name;
    private List<Ingredient> ingredients;

    public ResumedRecipe() {
        this.name = "";
        this.ingredients = new ArrayList<>(0);
    }

    public ResumedRecipe(int id, @NonNull String name, @NonNull List<Ingredient> ingredients) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public boolean hasName() {
        return !name.isEmpty();
    }

}