package com.artenesnogueira.bakingapp.views.steps;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.artenesnogueira.bakingapp.R;
import com.artenesnogueira.bakingapp.model.Recipe;
import com.artenesnogueira.bakingapp.views.RecipeViewModel;
import com.artenesnogueira.bakingapp.views.RecipeViewModelFactory;
import com.artenesnogueira.bakingapp.views.StepDetailsOnePaneView;
import com.artenesnogueira.bakingapp.views.StepDetailsTwoPaneView;

/**
 * Activity to display the steps of a recipe.
 */
@SuppressWarnings({"FieldCanBeLocal", "WeakerAccess"})
public class StepsActivity extends AppCompatActivity {

    private static final String EXTRA_RECIPE = "EXTRA_RECIPE";

    /**
     * Starts the StepsActivity activity
     *
     * @param context the context to start the activity
     * @param recipe  the recipe to display in the activity
     */
    public static void start(Context context, Recipe recipe) {
        Intent intent = new Intent(context, StepsActivity.class);
        intent.putExtra(EXTRA_RECIPE, recipe);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //if there is no recipe, this method will throw an exception
        Recipe recipe = getRecipeFromIntent();

        setContentView(R.layout.activity_recipe_details);
        //another way is to check if the inflated layout contains a certain id
        //but since layouts can change easyly, it is safer to rely on something else
        boolean isDoublePaneLayout = getResources().getBoolean(R.bool.is_double_pane);

        RecipeViewModelFactory factory = new RecipeViewModelFactory(recipe, 0);
        RecipeViewModel viewModel = ViewModelProviders.of(this, factory).get(RecipeViewModel.class);

        //we`ve create two classes to isolate the details of each pane implementation
        //by just instantiating them, we are setting up all fragments and other details
        //to start interacting with the screen
        if (isDoublePaneLayout) {
            new StepDetailsTwoPaneView(this, viewModel, getSupportFragmentManager());
        } else {
            new StepDetailsOnePaneView(this, viewModel, getSupportFragmentManager());
        }

    }

    /**
     * Gets the recipe from the intent passed to this activity.
     *
     * @return the recipe available in the intent
     * @throws IllegalArgumentException if no recipe is available
     */
    private Recipe getRecipeFromIntent() throws IllegalArgumentException {

        Intent intent = getIntent();

        if (intent == null) {
            throw new IllegalArgumentException("No recipe provided");
        }

        Bundle bundle = intent.getExtras();

        if (bundle == null || !bundle.containsKey(EXTRA_RECIPE)) {
            throw new IllegalArgumentException("No recipe provided");
        }

        Recipe recipe = (Recipe) bundle.get(EXTRA_RECIPE);

        if (recipe == null) {
            throw new IllegalArgumentException("No recipe provided");
        }

        return recipe;

    }

}
