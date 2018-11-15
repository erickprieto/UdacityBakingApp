package com.udacity.baking.activities;

import android.app.FragmentTransaction;
import android.app.Service;
import android.arch.lifecycle.ViewModelProviders;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.squareup.otto.Subscribe;
import com.udacity.baking.BakingApplication;
import com.udacity.baking.R;

import com.udacity.baking.events.ListRecipesFetchedEvent;
import com.udacity.baking.fragments.ListRecipesFragment;
import com.udacity.baking.models.Recipe;
import com.udacity.baking.services.BakingService;
import com.udacity.baking.viewmodels.MainViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;


public class MainActivity extends AppCompatActivity {

    /**
     * Tag that identify all messages sent to loggger by this class.
     */
    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String ID_SERIAL_LIST_RECIPES = "listRecipes";

    private MainViewModel mainViewModel;

    private BakingService bakingService;
    private BakingServiceConnection bakingServiceConnection = new BakingServiceConnection();
    private boolean connectedService;
    private Intent intentBakingService;

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
        setContentView(R.layout.main_activity);
        if(this.mainViewModel == null) {
            this.mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        }
        intentBakingService = new Intent(this, BakingService.class);
        bindService(intentBakingService, bakingServiceConnection, Service.BIND_AUTO_CREATE);
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
        loadFragment();
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
        unbindService(bakingServiceConnection);
    }

    private void loadFragment() {
        ListRecipesFragment fr = new ListRecipesFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainActivity_rootContainer, fr)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
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
                loadFragment();
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
