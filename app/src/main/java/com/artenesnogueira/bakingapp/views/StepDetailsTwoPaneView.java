package com.artenesnogueira.bakingapp.views;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.artenesnogueira.bakingapp.R;
import com.artenesnogueira.bakingapp.model.Step;
import com.artenesnogueira.bakingapp.views.step_details.StepDetailsFragment;
import com.artenesnogueira.bakingapp.views.steps.IngredientsAndStepsAdapter;
import com.artenesnogueira.bakingapp.views.steps.StepsFragment;

/**
 * Layout for one pane view for step details view.
 */
public class StepDetailsTwoPaneView implements IngredientsAndStepsAdapter.OnStepClicked {

    private final RecipeViewModel viewModel;

    public StepDetailsTwoPaneView(AppCompatActivity activity, RecipeViewModel viewModel, FragmentManager fragmentManager) {

        this.viewModel = viewModel;

        //creates the player view model to be used by the step details fragment
        ViewModelProviders.of(activity).get(PlayerViewModel.class);

        //in the two pane layout we use two fragments
        StepsFragment stepsFragment = new StepsFragment();
        StepDetailsFragment detailsFragment = StepDetailsFragment.create(false, true);

        //the steps fragment requires to set what happens when a step is clicked
        //since this logic changes from one to two pane layout
        stepsFragment.setOnStepClicked(this);

        fragmentManager.beginTransaction()
                .replace(R.id.fl_steps, stepsFragment)
                .replace(R.id.fl_step_details, detailsFragment)
                .commit();

    }

    @Override
    public void onStepClicked(Step step) {
        int index = viewModel.getSteps().indexOf(step);
        //define that when a step is clicked, a message is sent
        //to the view model so it can trigger updates in the fragments
        viewModel.goToStep(index);
    }

}