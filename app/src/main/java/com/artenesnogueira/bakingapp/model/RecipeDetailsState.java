package com.artenesnogueira.bakingapp.model;

import android.support.annotation.NonNull;

/**
 * The state for the recipe details.
 */
public class RecipeDetailsState {

    @NonNull
    private final Recipe recipe;

    public static RecipeDetailsState makeDisplayState(@NonNull Recipe recipe) {
        return new RecipeDetailsState(recipe);
    }

    private RecipeDetailsState(@NonNull Recipe recipe) {
        this.recipe = recipe;
    }

    @NonNull
    public Recipe getRecipe() {
        return recipe;
    }

}