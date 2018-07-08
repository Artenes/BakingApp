package com.artenesnogueira.bakingapp.provider;

import com.artenesnogueira.bakingapp.model.Recipe;
import com.artenesnogueira.bakingapp.utilities.HttpClient;
import com.artenesnogueira.bakingapp.utilities.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

/**
 * Repository that holds the recipes.
 */
public class RecipesRepository {

    private static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    private final HttpClient client;
    private final JsonParser parser;

    public RecipesRepository(HttpClient client, JsonParser parser) {
        this.client = client;
        this.parser = parser;
    }

    /**
     * Get all available recipes.
     *
     * @return all recipes
     * @throws IOException if it was not possible to retrieve the recipes
     */
    public List<Recipe> getRecipes() throws IOException {

        //the base element in the response from the server is an array
        Type recipesCollectionType = new TypeToken<Collection<Recipe>>(){}.getType();

        String jsonResponse = client.get(BASE_URL);
        try {
            return parser.fromJson(jsonResponse, recipesCollectionType);
        } catch (Exception exception) {
            throw new IOException(exception);
        }

    }

}