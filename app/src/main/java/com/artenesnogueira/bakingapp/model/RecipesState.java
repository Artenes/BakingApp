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

    public static RecipesState makeLoadingState() {
        return new RecipesState(new ArrayList<>(0), true, false);
    }

    public static RecipesState makeErrorState() {
        return new RecipesState(new ArrayList<>(0), false, true);
    }

    public static RecipesState makeDisplayState(@NonNull List<Recipe> recipes) {
        return new RecipesState(recipes, false, false);
    }

    private RecipesState(@NonNull List<Recipe> recipes, boolean isLoading, boolean hasError) {
        this.isLoading = isLoading;
        this.hasError = hasError;
        this.recipes = recipes;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public boolean hasError() {
        return hasError;
    }

    @NonNull
    public List<Recipe> getRecipes() {
        return recipes;
    }

}