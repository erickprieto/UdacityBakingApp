package com.udacity.baking.fragments;


import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.squareup.otto.Subscribe;
import com.udacity.baking.BakingApplication;
import com.udacity.baking.R;
import com.udacity.baking.activities.RecipesListActivity;
import com.udacity.baking.adapters.RecipeListAdapter;
import com.udacity.baking.events.onListRecipesFetchedEvent;
import com.udacity.baking.models.Recipe;

import java.util.ArrayList;

/**
 *
 * @author Erick Prieto
 * @since 2018
 */
public class RecipesListFragment extends Fragment {

    /**
     * Tag that identify all messages sent to loggger by this class.
     */
    private static final String TAG = RecipesListFragment.class.getSimpleName();

    public static final String ID_SERIAL_LIST_RECIPES = "listRecipes";

    private RecipeListAdapter adapter;

    private RecipesListActivity activity;

    public static RecipesListFragment newInstance(Bundle bundle) {
        RecipesListFragment result = new RecipesListFragment();
        result.setArguments(bundle);
        return result;
    }

    public RecyclerView getRecyclerView() {
        return this.getView().findViewById(R.id.listRecipesFragment_listRecipeRecyclerView);
    }
    public ProgressBar getProgressBar() {
        return this.getView().findViewById(R.id.listRecipesFragment_loadProgressBar);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final String TAG_M = "onCreateView ";
        Log.v(TAG, TAG_M);
        activity =  (RecipesListActivity) getActivity();
        return inflater.inflate(R.layout.listrecipes_fragment, container, false);

    }

    @Override
    public void onStart() {
        super.onStart();
        final String TAG_M = "onStart ";
        getProgressBar().setVisibility(View.VISIBLE);
        Log.v(TAG, TAG_M);

    }

    @Override
    public void onResume() {
        super.onResume();
        final String TAG_M = "onResume ";
        BakingApplication.getEventBus().register(this);
        Log.v(TAG, TAG_M);
        activity.getSupportActionBar().setTitle(getResources().getString(R.string.recipeListFragment_title));
        getRecyclerView().setLayoutManager(getGridLayoutManager());
        assignAdapterViews();

        if (activity.getListRecipes() != null) {
            adapter.putRecipes(activity.getListRecipes());
            getProgressBar().setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        final String TAG_M = "onSaveInstanceState ";
        Log.v(TAG, TAG_M);
        outState.putParcelableArrayList(ID_SERIAL_LIST_RECIPES
                , new ArrayList<>(this.activity.getListRecipes()));
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
        final String TAG_M = "onPause ";
        BakingApplication.getEventBus().unregister(this);
        Log.v(TAG, TAG_M);
    }

    /**
     * Define the columns in the {@link android.support.v7.widget.GridLayout}.
     * 2 Columns if {@link Configuration#ORIENTATION_PORTRAIT} or 3 columns if
     * {@link Configuration#ORIENTATION_PORTRAIT}.
     * @return <c>GridLayoutManager</c> configurated for vertical or horizontal position.
     */
    @NonNull
    private GridLayoutManager getGridLayoutManager() {

        GridLayoutManager grid;
        switch (getResources().getConfiguration().orientation) {
            case Configuration.ORIENTATION_LANDSCAPE:
                grid = new GridLayoutManager(getActivity(), 3);
                break;
            case Configuration.ORIENTATION_PORTRAIT:
                grid = new GridLayoutManager(getActivity(), 1);
                break;
            default:
                grid = new GridLayoutManager(getActivity(), 1);
        }
        return grid;
    }

    /**
     * Assign to {@link RecipesListFragment#getRecyclerView()}
     * an Adapter for {@link com.udacity.baking.models.Recipe}.
     */
    private void assignAdapterViews() {
        final String TAG_M = "assignAdapterViews() ";
        Log.v(TAG, TAG_M);
        adapter = new RecipeListAdapter(new ArrayList<Recipe>(), getActivity());
        getRecyclerView().setAdapter(adapter);
    }

    @Subscribe
    public void onListRecipesFetched(onListRecipesFetchedEvent event) {
        final String TAG_M = "onListRecipesFetched ";
        Log.v(TAG, TAG_M + event.getRecipes().size());

        activity.setListRecipes(event.getRecipes());
        adapter.putRecipes(activity.getListRecipes());
        getProgressBar().setVisibility(View.INVISIBLE);
    }
}
