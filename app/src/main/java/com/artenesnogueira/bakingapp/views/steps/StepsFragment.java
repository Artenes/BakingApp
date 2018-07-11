package com.artenesnogueira.bakingapp.views.steps;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.artenesnogueira.bakingapp.R;
import com.artenesnogueira.bakingapp.model.Ingredient;
import com.artenesnogueira.bakingapp.model.Recipe;
import com.artenesnogueira.bakingapp.model.RecipeState;
import com.artenesnogueira.bakingapp.model.Step;
import com.artenesnogueira.bakingapp.views.RecipeViewModel;
import com.artenesnogueira.bakingapp.views.steps.StepsAdapter;

/**
 * Fragment to display a list of steps
 */
public class StepsFragment extends Fragment implements StepsAdapter.OnStepClicked {

    @NonNull
    private StepsAdapter.OnStepClicked mOnStepClickedListener = this;
    private TextView mIngredientsTextView;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private StepsAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_details, container, false);

        mIngredientsTextView = view.findViewById(R.id.txv_ingredients);
        mRecyclerView = view.findViewById(R.id.rv_steps);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mLayoutManager = new GridLayoutManager(getActivity(), 1);
        mAdapter = new StepsAdapter(mOnStepClickedListener);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        //we expect that the activity that is using this frgaments creates an instance
        //if RecipeViewModel so this fragment can have access to the data to display
        try {
            RecipeViewModel viewModel = ViewModelProviders.of(getActivity()).get(RecipeViewModel.class);
            viewModel.getState().observe(getActivity(), this::render);
        } catch (RuntimeException exception) {
            throw new RuntimeException("Create an instance of RecipeViewModel to use this fragment", exception);
        }
    }

    /**
     * Sets the callback to be used when a step is clicked. If none is set,
     * this fragment will throw an exception when an item is clicked.
     *
     * @param onStepClicked the OnStepClicked implementation
     */
    public void setOnStepClicked(@NonNull StepsAdapter.OnStepClicked onStepClicked) {
        mOnStepClickedListener = onStepClicked;
    }

    /**
     * Renders the state of the view
     *
     * @param state the new state of the view
     */
    public void render(RecipeState state) {
        Recipe recipe = state.getRecipe();

        mAdapter.setData(recipe.getSteps());

        for (Ingredient ingredient : recipe.getIngredients()) {
            mIngredientsTextView.setText("");
            mIngredientsTextView.append(ingredient.getIngredient());
            mIngredientsTextView.append(",");
        }
    }

    @Override
    public void onStepClicked(Step step, int index) {
        //since is not possible to passa an implementation of StepsAdapter.OnStepClicked
        //right when this fragment is created, we make sure that an exception is thrown
        //if we forget to set the adapter
        throw new RuntimeException("onStepClicked not set in fragment");
    }

}
