package com.udacity.baking.fragments;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.udacity.baking.BakingApplication;
import com.udacity.baking.R;
import com.udacity.baking.activities.RecipeDetailActivity;
import com.udacity.baking.adapters.IngredientListAdapter;
import com.udacity.baking.adapters.StepListAdapter;
import com.udacity.baking.models.Ingredient;
import com.udacity.baking.models.Recipe;
import com.udacity.baking.models.Step;

import java.util.ArrayList;

/**
 *
 * @author Erick Prieto
 * @since 2018
 */
public class RecipeDetailFragment extends Fragment {

    /**
     * Tag that identify all messages sent to loggger by this class.
     */
    private static final String TAG = RecipeDetailFragment.class.getSimpleName();
    private static final String ID_ARGS_RECIPE_ID = "recipeId";

    public static final String ID_ARGS_STEP = "step";
    public static final String ID_RECIPE_NAME_STEP = "recipe";

    private StepListAdapter stepAdapter;
    private IngredientListAdapter ingredientAdapter;

    private RecipeDetailActivity activity;

    private Integer recipeId;

    public static RecipeDetailFragment newInstance(Recipe recipe) {
        RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ID_ARGS_RECIPE_ID, recipe);
        recipeDetailFragment.setArguments(args);
        return recipeDetailFragment;
    }

    public RecyclerView getIngredientsRecyclerView() {
        return this.getView().findViewById(R.id.detailRecipesFragment_listIngredientRecyclerView);
    }
    public RecyclerView getStepsRecyclerView() {
        return this.getView().findViewById(R.id.detailRecipesFragment_listStepRecyclerView);
    }
    public ProgressBar getProgressBar() {
        return this.getView().findViewById(R.id.detailRecipesFragment_loadProgressBar);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            recipeId = getArguments().getInt(ID_ARGS_RECIPE_ID);
        } else {
            Log.wtf(TAG, "No args received");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {
        activity =  (RecipeDetailActivity) getActivity();
        if (savedInstanceState != null) {

        }

        return inflater.inflate(R.layout.detailrecipe_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        BakingApplication.getEventBus().register(this);
        activity.getSupportActionBar().setTitle(activity.getRecipe().getName());
        getStepsRecyclerView().setLayoutManager(new LinearLayoutManager(activity));
        getIngredientsRecyclerView().setLayoutManager(new LinearLayoutManager(activity));
        assignAdapterViews();
        stepAdapter.putSteps(activity.getRecipe().getSteps());
        ingredientAdapter.putIngredients(activity.getRecipe().getIngredients());
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
        BakingApplication.getEventBus().unregister(this);
    }

    /**
     * Assign to {@link RecipeDetailFragment#getIngredientsRecyclerView()} an Adapter
     * for {@link Ingredient} and {@link RecipeDetailFragment#getStepsRecyclerView()} an Adapter
     * for {@link Step}.
     */
    private void assignAdapterViews() {
        final String TAG_M = "assignAdapterViews() ";
        Log.v(TAG, TAG_M);
        stepAdapter = new StepListAdapter(new ArrayList<Step>(), getActivity());
        stepAdapter.setRecipeName(activity.getRecipe().getName());
        ingredientAdapter = new IngredientListAdapter(new ArrayList<Ingredient>(), getActivity());
        getStepsRecyclerView().setAdapter(stepAdapter);
        getIngredientsRecyclerView().setAdapter(ingredientAdapter);
    }

}
