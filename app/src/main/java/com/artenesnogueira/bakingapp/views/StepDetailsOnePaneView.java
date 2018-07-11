package com.artenesnogueira.bakingapp.views;

import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.artenesnogueira.bakingapp.R;
import com.artenesnogueira.bakingapp.model.Step;
import com.artenesnogueira.bakingapp.views.step_details.StepDetailsActivity;
import com.artenesnogueira.bakingapp.views.steps.StepsAdapter;
import com.artenesnogueira.bakingapp.views.steps.StepsFragment;

/**
 * Layout for one pane view for step details view.
 */
public class StepDetailsOnePaneView implements StepsAdapter.OnStepClicked {

    private RecipeViewModel viewModel;
    private Context context;

    public StepDetailsOnePaneView(Context context, RecipeViewModel viewModel, FragmentManager fragmentManager) {
        this.context = context;
        this.viewModel = viewModel;

        StepsFragment fragment = new StepsFragment();
        fragment.setOnStepClicked(this);
        fragmentManager.beginTransaction().add(R.id.fl_details_container, fragment).commit();
    }

    @Override
    public void onStepClicked(Step step, int index) {
        //define that when a step is clicked, a new activity will be open
        StepDetailsActivity.start(context, viewModel.getRecipe(), index);
    }

}