package com.artenesnogueira.bakingapp.views.recipes;

import android.app.Application;
import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.ComponentName;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.artenesnogueira.bakingapp.R;
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
    private final IngredientsRepository mIngredientsRepository;
    private final RecipesRepository mRecipesRespository;

    public RecipesViewModel(@NonNull Application application) {
        super(application);
        mIngredientsRepository = new IngredientsRepository(SharedPreferencesIndex.getForIngredients(application), new JsonParser());
        mRecipesRespository = new RecipesRepository(new HttpClient(), new JsonParser(), mIngredientsRepository);
        mState = new MutableLiveData<>();
        reload();
    }

    public LiveData<RecipesState> getState() {
        return mState;
    }

    public void reload() {
        mState.setValue(RecipesState.makeLoadingState());
        new LoadRecipesTask(mRecipesRespository, mState).execute();
    }

    public void setRecipeToWidget(Recipe recipeToWidget){
        List<Recipe> currentRecipes = mState.getValue().getRecipes();

        //save the recipe in the repository
        try {
            mIngredientsRepository.set(recipeToWidget.createResumedRecipe());
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        //update the widgets
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getApplication());
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(getApplication(), IngredientsWidgetProvider.class));
        for (int id : appWidgetIds) {
            IngredientsWidgetProvider.updateAppWidget(getApplication(), appWidgetManager, id, mIngredientsRepository);
        }

        //update previous recipe on widget to not be on the widget anymore
        for (Recipe recipe : currentRecipes) {
            if (recipe.isOnWidget()) {
                recipe.setOnWidget(false);
                break;
            }
        }

        //update the recipe to be on the widget
        recipeToWidget.setOnWidget(true);

        //update the ui
        String message = getApplication().getResources().getString(R.string.recipe_added_to_widget);
        mState.setValue(RecipesState.makeAddRecipeToWidget(currentRecipes, message));
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