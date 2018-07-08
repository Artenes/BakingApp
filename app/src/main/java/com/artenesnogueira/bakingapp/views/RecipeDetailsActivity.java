package com.artenesnogueira.bakingapp.views;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.artenesnogueira.bakingapp.R;
import com.artenesnogueira.bakingapp.model.Ingredient;
import com.artenesnogueira.bakingapp.model.Recipe;
import com.artenesnogueira.bakingapp.model.RecipeDetailsState;
import com.artenesnogueira.bakingapp.model.Step;

/**
 * Activity to display the details of a recipe.
 */
@SuppressWarnings({"FieldCanBeLocal", "WeakerAccess"})
public class RecipeDetailsActivity extends AppCompatActivity implements StepsAdapter.OnStepClicked {

    private static final String EXTRA_RECIPE = "EXTRA_RECIPE";

    /**
     * Starts the RecipeDetailsActivity activity
     *
     * @param context the context to start the activity
     * @param recipe the recipe to display in the activity
     */
    public static void start(Context context, Recipe recipe) {
        Intent intent = new Intent(context, RecipeDetailsActivity.class);
        intent.putExtra(EXTRA_RECIPE, recipe);
        context.startActivity(intent);
    }

    private TextView mIngredientsTextView;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private StepsAdapter mAdapter;
    private RecipeDetailsViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //if there is no recipe, this method will throw an exception
        Recipe recipe = getRecipeFromIntent();

        setContentView(R.layout.activity_recipe_details);

        mIngredientsTextView = findViewById(R.id.txv_ingredients);
        mRecyclerView = findViewById(R.id.rv_steps);
        mLayoutManager = new GridLayoutManager(this, 1);
        mAdapter = new StepsAdapter(this);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        RecipeDetailsViewModelFactory factory = new RecipeDetailsViewModelFactory(recipe);
        mViewModel = ViewModelProviders.of(this, factory).get(RecipeDetailsViewModel.class);
        mViewModel.getState().observe(this, this::render);
    }

    public void render(RecipeDetailsState state) {

        Recipe recipe = state.getRecipe();

        mAdapter.setData(recipe.getSteps());

        for (Ingredient ingredient : recipe.getIngredients()) {
            mIngredientsTextView.append(ingredient.getIngredient());
            mIngredientsTextView.append(",");
        }

    }

    @Override
    public void onStepClicked(Step step) {
        Toast.makeText(this, step.getShortDescription(), Toast.LENGTH_SHORT).show();
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
