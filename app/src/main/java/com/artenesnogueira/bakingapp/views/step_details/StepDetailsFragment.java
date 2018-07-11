package com.artenesnogueira.bakingapp.views.step_details;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.artenesnogueira.bakingapp.R;
import com.artenesnogueira.bakingapp.model.RecipeState;
import com.artenesnogueira.bakingapp.model.Step;
import com.artenesnogueira.bakingapp.views.RecipeViewModel;

/**
 * Fragment to display the details of a step of a recipe
 */
public class StepDetailsFragment extends Fragment implements View.OnClickListener {

    private static final String KEY_NAVIGATION_VISIBILITY = "NAVIGATION_VISIBILITY";

    private TextView mDescriptionTextView;
    private Button mNextButton;
    private Button mPreviousButton;
    private RecipeViewModel mViewModel;

    /**
     * Create a new instance of this fragment
     *
     * @param displayNavigation wheter the navigation buttons will be displayed or not
     * @return the new instance of this fragment
     */
    public static StepDetailsFragment create(boolean displayNavigation) {
        int visibility = displayNavigation ? View.VISIBLE : View.GONE;
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_NAVIGATION_VISIBILITY, visibility);
        StepDetailsFragment fragment = new StepDetailsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_details, container, false);

        mDescriptionTextView = view.findViewById(R.id.tv_description);
        mNextButton = view.findViewById(R.id.btn_next);
        mPreviousButton = view.findViewById(R.id.btn_previous);

        mNextButton.setOnClickListener(this);
        mPreviousButton.setOnClickListener(this);

        //this can throw an exception if not available
        int visibility = getNavigationVisibility();
        mNextButton.setVisibility(visibility);
        mPreviousButton.setVisibility(visibility);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //we expect that the activity that is using this frgaments creates an instance
        //if RecipeViewModel so this fragment can have access to the data to display
        try {
            mViewModel = ViewModelProviders.of(getActivity()).get(RecipeViewModel.class);
            mViewModel.getState().observe(this, this::render);
        } catch (RuntimeException exception) {
            throw new RuntimeException("Create an instance of RecipeViewModel to use this fragment", exception);
        }

    }

    /**
     * Renders the state of the view
     *
     * @param state the new state of the view
     */
    public void render(RecipeState state) {
        Step step = state.getRecipe().getSteps().get(state.getCurrentStepIndex());
        mDescriptionTextView.setText(step.getDescription());
        mNextButton.setEnabled(state.hasNext());
        mPreviousButton.setEnabled(state.hasPrevious());
    }

    /**
     * Goes to the next step
     */
    public void next() {
        mViewModel.next();
    }

    /**
     * Goes to the previous step
     */
    public void previous() {
        mViewModel.previous();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_next:
                next();
                break;
            case R.id.btn_previous:
                previous();
                break;
        }
    }

    /**
     * Gets the navigation visibility from the bundle passed to this fragment
     *
     * @return the visibility of the navigation buttons
     */
    public int getNavigationVisibility() {
        Bundle bundle = getArguments();

        if (bundle == null || !bundle.containsKey(KEY_NAVIGATION_VISIBILITY)) {
            throw new IllegalArgumentException("Navigation visibility not provided");
        }

        return bundle.getInt(KEY_NAVIGATION_VISIBILITY);
    }

}
