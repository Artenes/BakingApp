package com.artenesnogueira.bakingapp.views;

import android.support.v4.app.FragmentManager;

import com.artenesnogueira.bakingapp.R;
import com.artenesnogueira.bakingapp.model.Step;
import com.artenesnogueira.bakingapp.views.step_details.StepDetailsFragment;
import com.artenesnogueira.bakingapp.views.steps.StepsAdapter;
import com.artenesnogueira.bakingapp.views.steps.StepsFragment;

/**
 * Layout for one pane view for step details view.
 */
public class StepDetailsTwoPaneView implements StepsAdapter.OnStepClicked {

    private final RecipeViewModel viewModel;

    public StepDetailsTwoPaneView(RecipeViewModel viewModel, FragmentManager fragmentManager) {

        this.viewModel = viewModel;

        //in the two pane layout we use two fragment
        StepsFragment stepsFragment = new StepsFragment();
        StepDetailsFragment detailsFragment = StepDetailsFragment.create(false);

        //the steps fragment requires to set what happens when a step is clicked
        //since this logic changes from one to two pane layout
        stepsFragment.setOnStepClicked(this);

        fragmentManager.beginTransaction()
                .add(R.id.fl_steps, stepsFragment)
                .add(R.id.fl_step_details, detailsFragment)
                .commit();

    }

    @Override
    public void onStepClicked(Step step, int index) {
        //define that when a step is clicked, a message is sent
        //to the view model so it can trigger updates in the fragments
        viewModel.goToStep(index);
    }

}