package com.udacity.baking.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.udacity.baking.models.Recipe;

import java.util.List;

/**
 *
 * @author Erick Prieto
 * @since 2018
 */
public class MainViewModel extends ViewModel {
    /**
     * Tag that identify all messages sent to loggger by this class.
     */
    private static final String TAG = MainViewModel.class.getSimpleName();

    private List<Recipe> recipes;

    public MainViewModel() {

    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    @Override
    protected void onCleared() {
        final String TAG_M = "onCleared ";
        Log.v(TAG, TAG_M);
        super.onCleared();
    }
}
