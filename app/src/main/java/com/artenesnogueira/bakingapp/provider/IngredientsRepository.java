package com.artenesnogueira.bakingapp.provider;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.artenesnogueira.bakingapp.model.ResumedRecipe;
import com.artenesnogueira.bakingapp.utilities.JsonParser;

import java.io.IOException;

/**
 * Repository to fetch the ingredients to display in the widget
 */
public class IngredientsRepository {

    private static final String KEY_RESUMED_RECIPE = "RESUMED_RECIPE";

    /**
     * We use shared preferences to store this recipe because is way
     * too much too use a sqlite database to store only one entity
     */
    private final SharedPreferences sharedPreferences;

    /**
     * To make the data easy to set and get we parse it to json
     */
    private final JsonParser parser;

    public IngredientsRepository(SharedPreferences sharedPreferences, JsonParser parser) {
        this.sharedPreferences = sharedPreferences;
        this.parser = parser;
    }

    /**
     * Get the recipe to display in the widget
     *
     * @return the resumed recipe to display
     * @throws IOException in case there is an error while reading the recipe from the repository
     */
    public ResumedRecipe get() throws IOException {
        String recipeJson = sharedPreferences.getString(KEY_RESUMED_RECIPE, "");

        if (recipeJson.isEmpty()) {
            return new ResumedRecipe();
        }

        try {
            return parser.fromJson(recipeJson, ResumedRecipe.class);
        } catch (Exception exception) {
            throw new IOException(exception);
        }
    }

    /**
     * Set the recipe to display in the widget
     *
     * @param resumedRecipe the recipe to store
     * @throws IOException in case there is an error while writing the recipe in the repository
     */
    public void set(@NonNull ResumedRecipe resumedRecipe) throws IOException {
        String recipeJson;

        try {
            recipeJson = parser.toJson(resumedRecipe);
        } catch (Exception exception) {
            throw new IOException(exception);
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_RESUMED_RECIPE, recipeJson);
        editor.apply();
    }

}