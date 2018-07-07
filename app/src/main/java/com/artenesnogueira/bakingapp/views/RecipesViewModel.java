package com.artenesnogueira.bakingapp.views;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import com.artenesnogueira.bakingapp.model.Recipe;
import com.artenesnogueira.bakingapp.model.RecipesState;
import com.artenesnogueira.bakingapp.provider.RecipesRepository;
import com.artenesnogueira.bakingapp.utilities.HttpClient;
import com.artenesnogueira.bakingapp.utilities.JsonParser;

import java.io.IOException;
import java.util.List;

/**
 * View model for the recipes view.
 */
class RecipesViewModel extends ViewModel {

    private final MutableLiveData<RecipesState> mState;

    public RecipesViewModel() {
        mState = new MutableLiveData<>();
        mState.setValue(RecipesState.makeLoadingState());
        RecipesRepository repository = new RecipesRepository(new HttpClient(), new JsonParser());
        new LoadRecipesTask(repository, mState).execute();
    }

    public LiveData<RecipesState> getState() {
        return mState;
    }

    static class LoadRecipesTask extends AsyncTask<Void, Void, RecipesState> {

        private final MutableLiveData<RecipesState> mMutableData;
        private final RecipesRepository mRepository;

        LoadRecipesTask(RecipesRepository repository, MutableLiveData<RecipesState> mutableData) {
            mMutableData = mutableData;
            mRepository = repository;
        }

        @Override
        protected void onPreExecute() {
            mMutableData.setValue(RecipesState.makeLoadingState());
        }

        @Override
        protected RecipesState doInBackground(Void... voids) {
            try {

                List<Recipe> recipes = mRepository.getRecipes();
                return RecipesState.makeDisplayState(recipes);

            } catch (IOException exception) {

                return RecipesState.makeErrorState();

            }
        }

        @Override
        protected void onPostExecute(RecipesState recipesState) {
            mMutableData.setValue(recipesState);
        }

    }

}