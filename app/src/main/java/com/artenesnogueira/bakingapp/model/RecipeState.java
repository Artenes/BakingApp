package com.artenesnogueira.bakingapp.model;

import android.support.annotation.NonNull;

/**
 * The state for the recipe details.
 */
public class RecipeState {

    @NonNull
    private final Recipe recipe;
    private final int currentStepIndex;

    public static RecipeState makeDisplayState(@NonNull Recipe recipe, int stepIndex) {
        int size = recipe.getSteps().size();
        if (stepIndex < 0 || stepIndex > size) {
            throw new IndexOutOfBoundsException("Invalid index for steps list, index: " + stepIndex + ", size: " + size);
        }
        return new RecipeState(recipe, stepIndex);
    }

    private RecipeState(@NonNull Recipe recipe, int currentStepIndex) {
        this.recipe = recipe;
        this.currentStepIndex = currentStepIndex;
    }

    @NonNull
    public Recipe getRecipe() {
        return recipe;
    }

    public int getCurrentStepIndex() {
        return currentStepIndex;
    }

    public int getNextStepIndex() {
        return currentStepIndex + 1;
    }

    public int getPreviousStepIndex() {
        return currentStepIndex - 1;
    }

    public boolean hasNext() {
        return getNextStepIndex() < recipe.getSteps().size();
    }

    public boolean hasPrevious() {
        return getPreviousStepIndex() >= 0;
    }

}