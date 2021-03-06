package com.artenesnogueira.bakingapp.views;

import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.artenesnogueira.bakingapp.R;
import com.artenesnogueira.bakingapp.model.Step;
import com.artenesnogueira.bakingapp.views.step_details.StepDetailsActivity;
import com.artenesnogueira.bakingapp.views.steps.IngredientsAndStepsAdapter;
import com.artenesnogueira.bakingapp.views.steps.StepsFragment;

/**
 * Layout for one pane view for step details view.
 */
public class StepDetailsOnePaneView implements IngredientsAndStepsAdapter.OnStepClicked {

    private final RecipeViewModel viewModel;
    private final Context context;

    public StepDetailsOnePaneView(Context context, RecipeViewModel viewModel, FragmentManager fragmentManager) {
        this.context = context;
        this.viewModel = viewModel;

        StepsFragment fragment = new StepsFragment();
        fragment.setOnStepClicked(this);
        fragmentManager.beginTransaction().replace(R.id.fl_details_container, fragment).commit();
    }

    @Override
    public void onStepClicked(Step step) {
        //define that when a step is clicked, a new activity will be open
        int index = viewModel.getSteps().indexOf(step);
        StepDetailsActivity.start(context, viewModel.getRecipe(), index);
    }

}