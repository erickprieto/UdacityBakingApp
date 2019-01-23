package com.udacity.baking.activities;

import android.support.v4.app.FragmentTransaction;
import android.app.Service;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.squareup.otto.Subscribe;
import com.udacity.baking.BakingApplication;
import com.udacity.baking.R;

import com.udacity.baking.events.onRecipeDetailsRequestEvent;
import com.udacity.baking.fragments.RecipesListFragment;
import com.udacity.baking.models.Recipe;
import com.udacity.baking.services.BakingService;
import com.udacity.baking.viewmodels.MainViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Erick Prieto
 * @since 2018
 */
public class RecipesListActivity extends AppCompatActivity {

    /**
     * Tag that identify all messages sent to loggger by this class.
     */
    private static final String TAG = RecipesListActivity.class.getSimpleName();

    private static final String ID_SERIAL_LIST_RECIPES = "listRecipes";
    private static final String LIST_RECIPE_FRAGMENT_TAG = "fragment_list_recipe";
    private static final String DETAIL_RECIPE_FRAGMENT_TAG = "fragment_detail_recipe";

    private MainViewModel mainViewModel;

    private BakingService bakingService;
    private BakingServiceConnection bakingServiceConnection = new BakingServiceConnection();
    private boolean connectedService;
    private Intent intentBakingService;
    private boolean fragmentInitial = true;


    public List<Recipe> getListRecipes() {
        return mainViewModel.getRecipes();
    }

    public void setListRecipes(List<Recipe> recipes) {
        mainViewModel.setRecipes(recipes);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String TAG_M = "onCreate ";
        setContentView(R.layout.recipeslist_activity);
        if(this.mainViewModel == null) {
            this.mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        }
        intentBakingService = new Intent(this, BakingService.class);
        bindService(intentBakingService, bakingServiceConnection, Service.BIND_AUTO_CREATE);

        if (fragmentInitial) {
            loadInitialFragment();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        final String TAG_M = "onRestart ";
        Log.v(TAG, TAG_M);
    }

    @Override
    protected void onStart() {
        super.onStart();
        final String TAG_M = "onStart ";
        Log.v(TAG, TAG_M);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        final String TAG_M = "onRestoreInstanceState ";
        Log.v(TAG, TAG_M);
        if (this.mainViewModel.getRecipes() == null) {
            this.mainViewModel.setRecipes(savedInstanceState.getParcelableArrayList(ID_SERIAL_LIST_RECIPES));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        final String TAG_M = "onResume ";
        BakingApplication.getEventBus().register(this);
        Log.v(TAG, TAG_M);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        final String TAG_M = "onSaveInstanceState ";
        Log.v(TAG, TAG_M);
        outState.putParcelableArrayList(ID_SERIAL_LIST_RECIPES
                , new ArrayList<>(this.mainViewModel.getRecipes()));

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        final String TAG_M = "onPause ";
        Log.v(TAG, TAG_M);
        BakingApplication.getEventBus().unregister(this);
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        final String TAG_M = "onStop ";
        Log.v(TAG, TAG_M);
        if(connectedService) {
//            unbindService(bakingServiceConnection);
        }
    }

    private void setUpActionBar() {

        ActionBar supportActionBar = getSupportActionBar();

        if (supportActionBar != null) {
            supportActionBar.setElevation(4.0f);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_cake_24px);
        }
    }

    private void loadInitialFragment() {
        RecipesListFragment fr = RecipesListFragment.newInstance(null);
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.mainActivity_firstFragmentContainer, fr, LIST_RECIPE_FRAGMENT_TAG)
                .commit();
    }

    @Subscribe
    public void loadRecipeDetailActivity(onRecipeDetailsRequestEvent event) {
        final String TAG_M = "loadRecipeDetailActivity ";
        Log.v(TAG, TAG_M + event.getRecipe().toString());
        Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtra(RecipeDetailActivity.ID_RECIPE, event.getRecipe());
        startActivityForResult(intent, 1);
    }



    class BakingServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            final String TAG_M = "onServiceConnected ";
            Log.v(TAG, TAG_M + "++++++++++++++++++++++++++++++++++++++++++++++++");
            bakingService = ((BakingService.BakingServiceBinder) service).getService();
            if (bakingService.getRecipes() == null) {
                bakingService.fetchRecipes();
            } else {
                mainViewModel.setRecipes(bakingService.getRecipes());
            }
            connectedService = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            final String TAG_M = "onServiceDisconnected ";
            Log.v(TAG, TAG_M + "++++++++++++++++++++++++++++++++++++++++++++++");
            connectedService = false;
        }

        @Override
        public void onBindingDied(ComponentName name) {
            final String TAG_M = "onBindingDied ";
            Log.v(TAG, TAG_M + "++++++++++++++++++++++++++++++++++++++++++++++");
            connectedService = false;
        }
    }

}
