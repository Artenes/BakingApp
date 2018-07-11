package com.artenesnogueira.bakingapp.model;

import android.support.annotation.NonNull;

import java.util.ArrayList;

/**
 * The state for the step details.
 */
public class StepDetailsState {

    @NonNull
    private final ArrayList<Step> steps;
    private final int currentIndex;

    public static StepDetailsState makeDisplayState(@NonNull ArrayList<Step> steps, int currentIndex) {
        return new StepDetailsState(steps, currentIndex);
    }

    public StepDetailsState(@NonNull ArrayList<Step> steps, int currentIndex) {
        this.steps = steps;
        this.currentIndex = currentIndex;
    }

    @NonNull
    public ArrayList<Step> getSteps() {
        return steps;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public int getNextStepIndex() {
        return currentIndex + 1;
    }

    public int getPreviousStepIndex() {
        return currentIndex - 1;
    }

    public boolean hasNext() {
        return getNextStepIndex() < steps.size();
    }

    public boolean hasPrevious() {
        return getPreviousStepIndex() >= 0;
    }

}