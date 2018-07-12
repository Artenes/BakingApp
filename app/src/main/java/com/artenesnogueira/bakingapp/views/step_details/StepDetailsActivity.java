package com.artenesnogueira.bakingapp.views.step_details;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.artenesnogueira.bakingapp.R;
import com.artenesnogueira.bakingapp.model.Recipe;
import com.artenesnogueira.bakingapp.views.PlayerViewModel;
import com.artenesnogueira.bakingapp.views.RecipeViewModel;
import com.artenesnogueira.bakingapp.views.RecipeViewModelFactory;

/**
 * Activity to display the details of a step
 */
public class StepDetailsActivity extends AppCompatActivity {

    private static final String EXTRA_RECIPE = "EXTRA_RECIPE";
    private static final String EXTRA_RECIPE_INDEX = "EXTRA_RECIPE_INDEX";

    /**
     * Starts the activity
     *
     * @param context the current context
     * @param recipe  the recipe to display
     * @param index   the index of the step to show
     */
    public static void start(Context context, Recipe recipe, int index) {
        Intent intent = new Intent(context, StepDetailsActivity.class);
        intent.putExtra(EXTRA_RECIPE, recipe);
        intent.putExtra(EXTRA_RECIPE_INDEX, index);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //these methods can throw exceptions
        //if some of these date is not available
        Bundle bundle = getBundleFromIntent();
        Recipe recipe = getRecipeFromBundle(bundle);
        int index = getIndexFromBundle(bundle);

        setContentView(R.layout.activity_step_details);

        RecipeViewModelFactory factory = new RecipeViewModelFactory(recipe, index);
        ViewModelProviders.of(this, factory).get(RecipeViewModel.class);
        ViewModelProviders.of(this).get(PlayerViewModel.class);

        StepDetailsFragment fragment = StepDetailsFragment.create(true, false);
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fm_step_details, fragment).commit();
    }

    /**
     * Helper method to get the bundle from the intent.
     * Since the data in the bundle is required, we throw
     * some exceptions if it is not available
     *
     * @return the available bundle in the intent
     */
    private Bundle getBundleFromIntent() {
        Intent intent = getIntent();
        if (intent == null) {
            throw new IllegalArgumentException("No parameters provided to activity");
        }

        Bundle bundle = intent.getExtras();
        if (bundle == null) {
            throw new IllegalArgumentException("No parameters provided to activity");
        }

        return bundle;
    }

    /**
     * Gets the recipe from the bundle
     *
     * @param bundle the bundle where the recipe is
     * @return the retrieved recipe from the bundle
     */
    private Recipe getRecipeFromBundle(@NonNull Bundle bundle) {
        if (!bundle.containsKey(EXTRA_RECIPE)) {
            throw new IllegalArgumentException("No recipe provided");
        }

        Recipe recipe = (Recipe) bundle.get(EXTRA_RECIPE);

        if (recipe == null) {
            throw new IllegalArgumentException("No recipe provided");
        }

        return recipe;
    }

    /**
     * Gets the index for the step to display from the bundle
     *
     * @param bundle the bundle where the index is
     * @return the retrieved index from the bundle
     */
    private int getIndexFromBundle(@NonNull Bundle bundle) {
        if (!bundle.containsKey(EXTRA_RECIPE_INDEX)) {
            throw new IllegalArgumentException("No step index provided");
        }

        return bundle.getInt(EXTRA_RECIPE_INDEX);
    }

}
