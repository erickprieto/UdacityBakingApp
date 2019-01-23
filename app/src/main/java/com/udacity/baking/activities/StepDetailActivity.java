package com.udacity.baking.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.udacity.baking.R;
import com.udacity.baking.fragments.StepDetailFragment;
import com.udacity.baking.models.Step;

import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Erick Prieto
 * @since 2018
 */
public class StepDetailActivity extends AppCompatActivity {

    public static final String ID_ARGS_STEP = "step";
    public static final String ID_RECIPE_NAME_STEP = "recipe";
    private static final String DETAIL_FRAGMENT_KEY = "detail_fragment";

    private StepDetailFragment stepDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipedetail_activity);

        Step step = new Step();
        String recipeName = StringUtils.EMPTY;

        if (getIntent() != null) {
            step = getIntent().getParcelableExtra(ID_ARGS_STEP);
            recipeName = getIntent().getStringExtra(ID_RECIPE_NAME_STEP);
        }

        // persistence: Save the state of the detail fragment so video is preserved after device rotation
        if (savedInstanceState == null) {
            stepDetailFragment = StepDetailFragment.newInstance(recipeName, step);
        } else {
            stepDetailFragment = (StepDetailFragment) getSupportFragmentManager()
                    .getFragment(savedInstanceState, DETAIL_FRAGMENT_KEY);
        }


        ActionBar ab = getSupportActionBar();
        if (step.getId()>0)
            ab.setTitle(getString(R.string.stepDetailActivity_Title) + (step.getId()));
        else
            ab.setTitle(R.string.stepDetailActivity_Introduction);
        ab.setSubtitle(recipeName);

        loadStepDetailFragment();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, DETAIL_FRAGMENT_KEY, stepDetailFragment);
    }

    private void loadStepDetailFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.recipedetail_frame, stepDetailFragment)
                .commit();
    }
}
