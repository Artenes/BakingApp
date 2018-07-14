package com.artenesnogueira.bakingapp.views.recipes;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.artenesnogueira.bakingapp.R;
import com.artenesnogueira.bakingapp.model.Recipe;
import com.artenesnogueira.bakingapp.model.RecipesState;
import com.artenesnogueira.bakingapp.views.steps.StepsActivity;

/**
 * The view that displays the list of recipes.
 */
@SuppressWarnings({"FieldCanBeLocal", "WeakerAccess"})
public class RecipesActivity extends AppCompatActivity implements RecipesAdapter.OnRecipeClicked, View.OnClickListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecipesAdapter mAdapter;
    private RecipesViewModel mViewModel;
    private TextView mErrorTextView;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mErrorTextView = findViewById(R.id.tv_error);
        mProgressBar = findViewById(R.id.pb_loading);

        int numberOfColumns = getResources().getInteger(R.integer.recipes_columns);
        mRecyclerView = findViewById(R.id.recipes_list);
        mLayoutManager = new GridLayoutManager(this, numberOfColumns);
        mAdapter = new RecipesAdapter(this);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mErrorTextView.setOnClickListener(this);

        mViewModel = ViewModelProviders.of(this).get(RecipesViewModel.class);
        mViewModel.getState().observe(this, this::render);
    }

    public void render(@NonNull RecipesState state) {
        if (state.isLoading()) {
            mProgressBar.setVisibility(View.VISIBLE);
            mErrorTextView.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.GONE);
            return;
        }

        if (state.hasError()) {
            mErrorTextView.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.GONE);
            return;
        }

        mRecyclerView.setVisibility(View.VISIBLE);
        mErrorTextView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);

        mAdapter.setData(state.getRecipes());

        if (state.hasMessage()) {
            Toast.makeText(this, state.popMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onRecipeClicked(Recipe recipe) {
        StepsActivity.start(this, recipe);
    }

    @Override
    public void onAddToWidgetClicked(Recipe recipe) {
        mViewModel.setRecipeToWidget(recipe);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        mViewModel.reload();
    }

}
