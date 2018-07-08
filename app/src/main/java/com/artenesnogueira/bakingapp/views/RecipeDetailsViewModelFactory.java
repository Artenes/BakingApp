package com.artenesnogueira.bakingapp.views;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.artenesnogueira.bakingapp.model.Recipe;

/**
 * Factory to create a new instace of RecipeDetailsViewModel.
 */
@SuppressWarnings("ALL")
class RecipeDetailsViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final Recipe mRecipe;

    RecipeDetailsViewModelFactory(Recipe mRecipe) {
        this.mRecipe = mRecipe;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new RecipeDetailsViewModel(mRecipe);
    }

}