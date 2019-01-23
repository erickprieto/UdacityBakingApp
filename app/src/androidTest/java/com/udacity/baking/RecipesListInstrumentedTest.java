package com.udacity.udacitybaking;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.udacity.baking.R;
import com.udacity.baking.activities.RecipesListActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class RecipesListInstrumentedTest {

    @Rule
    public ActivityTestRule<RecipesListActivity> recipesListActivityTestRule
            = new ActivityTestRule<>(RecipesListActivity.class);


    @Test
    public void clickRecipe_OpenRecipeInfoActivity() {
        // Context of the app under test.
        onView(withId(R.id.listRecipesFragment_listRecipeRecyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }
}
