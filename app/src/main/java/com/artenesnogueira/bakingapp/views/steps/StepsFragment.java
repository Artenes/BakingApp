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

/**
 * Fragment to display a list of steps
 */
public class StepsFragment extends Fragment implements IngredientsAndStepsAdapter.OnStepClicked {

    @NonNull
    private IngredientsAndStepsAdapter.OnStepClicked mOnStepClickedListener = this;
    private RecyclerView mRecyclerView;
    private GridLayoutManager mLayoutManager;
    private IngredientsAndStepsAdapter mAdapter;
    private int mColumnsCount;
    private RecipeViewModel mViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_steps, container, false);

        mRecyclerView = view.findViewById(R.id.rv_steps);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mColumnsCount = getActivity().getResources().getInteger(R.integer.ingredients_columns);
        mLayoutManager = new GridLayoutManager(getActivity(), mColumnsCount);
        mLayoutManager.setSpanSizeLookup(mSpanSizeLookup);
        mAdapter = new IngredientsAndStepsAdapter(getActivity(), mOnStepClickedListener);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        //we expect that the activity that is using this frgaments creates an instance
        //if RecipeViewModel so this fragment can have access to the data to display
        try {
            mViewModel = ViewModelProviders.of(getActivity()).get(RecipeViewModel.class);
        } catch (RuntimeException exception) {
            throw new RuntimeException("Create an instance of RecipeViewModel to use this fragment", exception);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mViewModel.getState().observe(getActivity(), this::render);
    }

    /**
     * Sets the callback to be used when a step is clicked. If none is set,
     * this fragment will throw an exception when an item is clicked.
     *
     * @param onStepClicked the OnStepClicked implementation
     */
    public void setOnStepClicked(@NonNull IngredientsAndStepsAdapter.OnStepClicked onStepClicked) {
        mOnStepClickedListener = onStepClicked;
    }

    /**
     * Renders the state of the view
     *
     * @param state the new state of the view
     */
    public void render(RecipeState state) {
        Recipe recipe = state.getRecipe();

        mAdapter.setData(recipe);

        getActivity().setTitle(state.getRecipe().getName());
    }

    @Override
    public void onStepClicked(Step step) {
        //since is not possible to passa an implementation of IngredientsAndStepsAdapter.OnStepClicked
        //right when this fragment is created, we make sure that an exception is thrown
        //if we forget to set the adapter
        throw new RuntimeException("onStepClicked not set in fragment");
    }

    /**
     * Lookup to define when an item in the list should spam to multiple columns or not
     */
    private final GridLayoutManager.SpanSizeLookup mSpanSizeLookup = new GridLayoutManager.SpanSizeLookup() {
        @Override
        public int getSpanSize(int position) {

            int viewTytpe = mAdapter.getItemViewType(position);

            if (viewTytpe == IngredientsAndStepsAdapter.TITLE_VIEW_TYTPE || viewTytpe == IngredientsAndStepsAdapter.STEP_VIEW_TYTPE) {
                return mColumnsCount;
            }

            return 1;

        }
    };

}
