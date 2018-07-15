package com.artenesnogueira.bakingapp.views.step_details;

import android.arch.lifecycle.ViewModelProviders;
import android.net.Uri;
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
import com.artenesnogueira.bakingapp.views.PlayerViewModel;
import com.artenesnogueira.bakingapp.views.RecipeViewModel;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

/**
 * Fragment to display the details of a step of a recipe
 */
public class StepDetailsFragment extends Fragment implements View.OnClickListener {

    private static final String KEY_NAVIGATION_VISIBILITY = "NAVIGATION_VISIBILITY";
    private static final String KEY_TWO_PANE_MODE = "TWO_PANE_MODE";

    private SimpleExoPlayerView mPlayerView;
    private TextView mVideoNotAvailableTextView;
    private TextView mTitleTextView;
    private TextView mDescriptionTextView;
    private Button mNextButton;
    private Button mPreviousButton;
    private RecipeViewModel mViewModel;
    private PlayerViewModel mPlayerViewModel;

    /**
     * Create a new instance of this fragment
     *
     * @param displayNavigation wheter the navigation buttons will be displayed or not
     * @return the new instance of this fragment
     */
    public static StepDetailsFragment create(boolean displayNavigation, boolean inTwoPaneMode) {
        int visibility = displayNavigation ? View.VISIBLE : View.GONE;
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_NAVIGATION_VISIBILITY, visibility);
        bundle.putBoolean(KEY_TWO_PANE_MODE, inTwoPaneMode);
        StepDetailsFragment fragment = new StepDetailsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //must be sure about how this mLayout will behave
        int layout = isInTwoPaneMode() ? R.layout.fragment_step_details_protrait : R.layout.fragment_step_details;
        View view = inflater.inflate(layout, container, false);

        mPlayerView = view.findViewById(R.id.player);
        mVideoNotAvailableTextView = view.findViewById(R.id.tv_video_not_available);
        mDescriptionTextView = view.findViewById(R.id.tv_description);
        mTitleTextView = view.findViewById(R.id.tv_title);
        mNextButton = view.findViewById(R.id.btn_next);
        mPreviousButton = view.findViewById(R.id.btn_previous);

        if (!isDisplayingOnlyVideoPlayer()) {
            mNextButton.setOnClickListener(this);
            mPreviousButton.setOnClickListener(this);

            //this can throw an exception if not available
            int visibility = getNavigationVisibility();
            mNextButton.setVisibility(visibility);
            mPreviousButton.setVisibility(visibility);
        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //we expect that the activity that is using this frgaments creates an instance
        //if RecipeViewModel so this fragment can have access to the data to display
        try {
            mViewModel = ViewModelProviders.of(getActivity()).get(RecipeViewModel.class);
            mPlayerViewModel = ViewModelProviders.of(getActivity()).get(PlayerViewModel.class);
        } catch (RuntimeException exception) {
            throw new RuntimeException("Create an instance of RecipeViewModel and PlayerViewModel to use this fragment", exception);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mViewModel.getState().observe(this, this::render);
        mPlayerView.setPlayer(mPlayerViewModel.getPlayer());
    }

    @Override
    public void onPause() {
        super.onPause();
        mPlayerViewModel.stop();
    }

    /**
     * Renders the state of the view
     *
     * @param state the new state of the view
     */
    public void render(RecipeState state) {
        Step step = state.getRecipe().getSteps().get(state.getCurrentStepIndex());
        if (!isDisplayingOnlyVideoPlayer()) {
            mDescriptionTextView.setText(step.getDescription());
            mNextButton.setEnabled(state.hasNext());
            mPreviousButton.setEnabled(state.hasPrevious());
            mTitleTextView.setText(step.getShortDescription());
        }

        if (step.hasVideo()) {
            mPlayerView.setVisibility(View.VISIBLE);
            mVideoNotAvailableTextView.setVisibility(View.GONE);
        } else {
            mPlayerView.setVisibility(View.GONE);
            mVideoNotAvailableTextView.setVisibility(View.VISIBLE);
        }

        Uri uri = Uri.parse(step.getVideoURL());
        mPlayerViewModel.setVideo(uri);

        getActivity().setTitle(state.getRecipe().getName());
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

    /**
     * Gets if this fragment is in two pane mode from the bundle passed to this fragment
     *
     * @return if the two pane mode is being used
     */
    public boolean isInTwoPaneMode() {
        Bundle bundle = getArguments();

        if (bundle == null || !bundle.containsKey(KEY_TWO_PANE_MODE)) {
            throw new IllegalArgumentException("Two pane mode configuration not provided");
        }

        return bundle.getBoolean(KEY_TWO_PANE_MODE);
    }

    /**
     * Checks if only the video player is being displayed
     * this happens when the device is in landscape
     *
     * @return either if only the video is being displayed
     */
    public boolean isDisplayingOnlyVideoPlayer() {

        return mDescriptionTextView == null &&
                mTitleTextView == null &&
                mNextButton == null &&
                mPreviousButton == null;

    }

}
