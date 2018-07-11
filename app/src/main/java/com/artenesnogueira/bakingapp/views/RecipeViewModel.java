package com.artenesnogueira.bakingapp.views;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.artenesnogueira.bakingapp.model.Recipe;
import com.artenesnogueira.bakingapp.model.RecipeState;
import com.artenesnogueira.bakingapp.model.Step;

import java.util.List;

/**
 * View model for a recipe.
 */
public class RecipeViewModel extends ViewModel {

    private final MutableLiveData<RecipeState> mState;

    public RecipeViewModel(Recipe recipe, int currentStepIndex) {
        mState = new MutableLiveData<>();
        mState.setValue(RecipeState.makeDisplayState(recipe, currentStepIndex));
    }

    /**
     * Get the current state
     *
     * @return the observable current state
     */
    public LiveData<RecipeState> getState() {
        return mState;
    }

    /**
     * Get the steps of the recipe
     *
     * @return a list of steps
     */
    public List<Step> getSteps() {
        return mState.getValue().getRecipe().getSteps();
    }

    /**
     * Get the recipe
     *
     * @return a recipe
     */
    public Recipe getRecipe() {
        return mState.getValue().getRecipe();
    }

    /**
     * Go to a given step in the steps list
     *
     * @param index the index of the step to go to
     */
    public void goToStep(int index) {
        RecipeState state = mState.getValue();
        mState.setValue(RecipeState.makeDisplayState(state.getRecipe(), index));
    }

    /**
     * Go to the next step if one is availbale
     */
    public void next() {
        RecipeState state = mState.getValue();
        if (!state.hasNext()) {
            return;
        }
        goToStep(state.getNextStepIndex());
    }

    /**
     * Go to the previous step if one is available
     */
    public void previous() {
        RecipeState state = mState.getValue();
        if (!state.hasPrevious()) {
            return;
        }
        goToStep(state.getPreviousStepIndex());
    }

}