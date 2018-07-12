package com.artenesnogueira.bakingapp.views.recipes;

import android.app.Application;
import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.ComponentName;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.artenesnogueira.bakingapp.model.Recipe;
import com.artenesnogueira.bakingapp.model.RecipesState;
import com.artenesnogueira.bakingapp.provider.IngredientsRepository;
import com.artenesnogueira.bakingapp.provider.RecipesRepository;
import com.artenesnogueira.bakingapp.utilities.HttpClient;
import com.artenesnogueira.bakingapp.utilities.JsonParser;
import com.artenesnogueira.bakingapp.utilities.SharedPreferencesIndex;
import com.artenesnogueira.bakingapp.widget.IngredientsWidgetProvider;

import java.io.IOException;
import java.util.List;

/**
 * View model for the recipes view.
 */
public class RecipesViewModel extends AndroidViewModel {

    private final MutableLiveData<RecipesState> mState;
    private final IngredientsRepository mRepository;

    public RecipesViewModel(@NonNull Application application) {
        super(application);
        mRepository = new IngredientsRepository(SharedPreferencesIndex.getForIngredients(application), new JsonParser());
        mState = new MutableLiveData<>();
        mState.setValue(RecipesState.makeLoadingState());
        RecipesRepository repository = new RecipesRepository(new HttpClient(), new JsonParser());
        new LoadRecipesTask(repository, mState).execute();
    }

    public LiveData<RecipesState> getState() {
        return mState;
    }

    public void setRecipeToWidget(Recipe recipe){
        try {
            mRepository.set(recipe.createResumedRecipe());
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getApplication());
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(getApplication(), IngredientsWidgetProvider.class));
        for (int id : appWidgetIds) {
            IngredientsWidgetProvider.updateAppWidget(getApplication(), appWidgetManager, id, mRepository);
        }
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