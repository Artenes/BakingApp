package com.artenesnogueira.bakingapp;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.support.test.espresso.core.internal.deps.guava.collect.Iterables;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;

import com.artenesnogueira.bakingapp.views.recipes.RecipesActivity;

import org.junit.Assume;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

/**
 * Usually there is test for each screen, but since this app is very simples
 * is not worth it creating different tests for each one, just one class
 * is enough to hold all basic test cases
 */
@RunWith(AndroidJUnit4.class)
public class ApplicationFlowTest {

    private static final int FIRST_ITEM = 0;
    private static final int SECOND_ITEM = 1;

    @Rule
    public ActivityTestRule<RecipesActivity> mActivityRule = new ActivityTestRule<>(RecipesActivity.class);

    @Test
    public void itShowsRecipeSteps() {
        onView(withId(R.id.recipes_list)).perform(actionOnItemAtPosition(FIRST_ITEM, click()));

        onView(withId(R.id.txv_ingredients)).check(matches(withText("cream cheese(softened),")));
        onView(withText("Recipe Introduction")).check(matches(isDisplayed()));
        onView(withText("Starting prep")).check(matches(isDisplayed()));
        onView(withText("Prep the cookie crust.")).check(matches(isDisplayed()));
        onView(withText("Press the crust into baking form.")).check(matches(isDisplayed()));
        onView(withText("Start filling prep")).check(matches(isDisplayed()));
        onView(withText("Finish filling prep")).check(matches(isDisplayed()));
        onView(withText("Finishing Steps")).check(matches(isDisplayed()));
    }

    @Test
    public void itDisplaysTheStepDetailsAndVideo() {
        onView(withId(R.id.recipes_list)).perform(actionOnItemAtPosition(FIRST_ITEM, click()));
        onView(withId(R.id.rv_steps)).perform(actionOnItemAtPosition(SECOND_ITEM, click()));

        onView(withId(R.id.tv_description)).check(matches(withText("1. Preheat the oven to 350°F. Butter a 9\" deep dish pie pan.")));
        onView(withId(R.id.exo_overlay)).check(matches(isDisplayed()));
    }

    @Test
    public void itAllowsNavigationBetweenSteps() {
        onView(withId(R.id.recipes_list)).perform(actionOnItemAtPosition(FIRST_ITEM, click()));
        onView(withId(R.id.rv_steps)).perform(actionOnItemAtPosition(SECOND_ITEM, click()));

        onView(withId(R.id.btn_next)).perform(click());
        onView(withId(R.id.tv_description)).check(matches(withText("2. Whisk the graham cracker crumbs, 50 grams (1/4 cup) of sugar, and 1/2 teaspoon of salt together in a medium bowl. Pour the melted butter and 1 teaspoon of vanilla into the dry ingredients and stir together until evenly mixed.")));

        onView(withId(R.id.btn_previous)).perform(click());
        onView(withId(R.id.tv_description)).check(matches(withText("1. Preheat the oven to 350°F. Butter a 9\" deep dish pie pan.")));
    }

    @Test
    public void itBlocksNavigationWhenItReachsTheStartOrEndOfTheStepsList() {
        onView(withId(R.id.recipes_list)).perform(actionOnItemAtPosition(FIRST_ITEM, click()));
        onView(withId(R.id.rv_steps)).perform(actionOnItemAtPosition(SECOND_ITEM, click()));

        onView(withId(R.id.btn_previous)).perform(click());
        onView(withId(R.id.btn_previous)).check(matches(not(isEnabled())));

        onView(withId(R.id.btn_next)).perform(click());
        onView(withId(R.id.btn_next)).perform(click());
        onView(withId(R.id.btn_next)).perform(click());
        onView(withId(R.id.btn_next)).perform(click());
        onView(withId(R.id.btn_next)).perform(click());
        onView(withId(R.id.btn_next)).perform(click());
        onView(withId(R.id.btn_next)).check(matches(not(isEnabled())));
    }

    @Test
    public void itDisplayesStepsDetailsInTabletLandscape() throws InterruptedException {
        onView(withId(R.id.recipes_list)).perform(actionOnItemAtPosition(FIRST_ITEM, click()));

        mActivityRule.getActivity().runOnUiThread(() -> {
            getCurrentActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        });

        //assumeThatItIsInDoublePane();

        onView(withId(R.id.rv_steps)).perform(actionOnItemAtPosition(SECOND_ITEM, click()));
        onView(withId(R.id.tv_description)).check(matches(withText("1. Preheat the oven to 350°F. Butter a 9\" deep dish pie pan.")));
        onView(withId(R.id.exo_overlay)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_next)).check(matches(not(isDisplayed())));
        onView(withId(R.id.btn_previous)).check(matches(not(isDisplayed())));
    }

    public void assumeThatItIsInDoublePane() {

        mActivityRule.getActivity().runOnUiThread(() -> {
            boolean isInDoublePane = getCurrentActivity().getResources().getBoolean(R.bool.is_double_pane);
            Assume.assumeTrue(isInDoublePane);
        });

    }

    public Activity getCurrentActivity() {
        final Activity[] activity = new Activity[1];
        activity[0] = Iterables.getOnlyElement(ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED));
        return activity[0];
    }

}