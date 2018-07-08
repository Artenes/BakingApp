package com.artenesnogueira.bakingapp.views;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.artenesnogueira.bakingapp.R;
import com.artenesnogueira.bakingapp.model.Recipe;
import com.artenesnogueira.bakingapp.model.RecipesState;

/**
 * The view that displays the list of recipes.
 */
@SuppressWarnings({"FieldCanBeLocal", "WeakerAccess"})
public class RecipesActivity extends AppCompatActivity implements RecipesAdapter.OnRecipeClicked {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecipesAdapter mAdapter;
    private RecipesViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recipes_list);
        mLayoutManager = new GridLayoutManager(this, 1);
        mAdapter = new RecipesAdapter(this);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mViewModel = ViewModelProviders.of(this).get(RecipesViewModel.class);
        mViewModel.getState().observe(this, this::render);
    }

    public void render(@NonNull RecipesState state) {

        if (state.isLoading()) {
            Toast.makeText(this, "Is loading", Toast.LENGTH_SHORT).show();
            return;
        }

        if (state.hasError()) {
            Toast.makeText(this, "Has error", Toast.LENGTH_SHORT).show();
            return;
        }

        mAdapter.setData(state.getRecipes());

    }

    @Override
    public void onRecipeClicked(Recipe recipe) {
        RecipeDetailsActivity.start(this, recipe);
    }

}
