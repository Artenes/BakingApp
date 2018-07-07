package com.artenesnogueira.bakingapp.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A recipe.
 */
@SuppressWarnings({"FieldCanBeLocal", "CanBeFinal", "unused"})
public class Recipe {

    private int id;
    private String name = ""; //to avoid NullPointers
    private final List<Ingredient> ingredients;
    private final List<Step> steps;
    private int servings;
    private String image = "";

    public Recipe() {
        ingredients = new ArrayList<>(0);
        steps = new ArrayList<>(0);
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

    public List<Step> getSteps() {
        return steps;
    }

    public int getServings() {
        return servings;
    }

    public String getImage() {
        return image;
    }

}