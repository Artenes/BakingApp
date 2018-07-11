package com.artenesnogueira.bakingapp.views;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.artenesnogueira.bakingapp.model.Recipe;

/**
 * Factory to create a new instace of RecipeViewModel.
 */
@SuppressWarnings("ALL")
public class RecipeViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final Recipe mRecipe;
    private final int mCurrentStepIndex;

    public RecipeViewModelFactory(Recipe recipe, int currentStepIndex) {
        mRecipe = recipe;
        mCurrentStepIndex = currentStepIndex;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new RecipeViewModel(mRecipe, mCurrentStepIndex);
    }

}