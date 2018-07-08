package com.artenesnogueira.bakingapp.views;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.artenesnogueira.bakingapp.model.Recipe;
import com.artenesnogueira.bakingapp.model.RecipeDetailsState;

/**
 * View model for the recipe details view.
 */
class RecipeDetailsViewModel extends ViewModel {

    private final MutableLiveData<RecipeDetailsState> mState;

    RecipeDetailsViewModel(Recipe recipe) {
        mState = new MutableLiveData<>();
        mState.setValue(RecipeDetailsState.makeDisplayState(recipe));
    }

    LiveData<RecipeDetailsState> getState() {
        return mState;
    }

}