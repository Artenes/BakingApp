package com.artenesnogueira.bakingapp.model;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * The state for the recipes list.
 */
public class RecipesState {

    @NonNull
    private final List<Recipe> recipes;
    private final boolean isLoading;
    private final boolean hasError;
    private String message;

    public static RecipesState makeLoadingState() {
        return new RecipesState(new ArrayList<>(0), true, false, "");
    }

    public static RecipesState makeErrorState() {
        return new RecipesState(new ArrayList<>(0), false, true, "");
    }

    public static RecipesState makeDisplayState(@NonNull List<Recipe> recipes) {
        return new RecipesState(recipes, false, false, "");
    }

    public static RecipesState makeAddRecipeToWidget(@NonNull List<Recipe> recipes, String message) {
        return new RecipesState(recipes, false, false, message);
    }

    private RecipesState(@NonNull List<Recipe> recipes, boolean isLoading, boolean hasError, @NonNull String message) {
        this.isLoading = isLoading;
        this.hasError = hasError;
        this.recipes = recipes;
        this.message = message;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public boolean hasError() {
        return hasError;
    }

    public boolean hasMessage() {
        return !message.isEmpty();
    }

    /**
     * Pop the message and empty it out
     *
     * @return the available message
     */
    public String popMessage() {
        String flashMessage = message;
        message = "";
        return flashMessage;
    }

    @NonNull
    public List<Recipe> getRecipes() {
        return recipes;
    }

}