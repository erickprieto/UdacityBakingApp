package com.udacity.baking.activities;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.squareup.otto.Subscribe;
import com.udacity.baking.BakingApplication;
import com.udacity.baking.R;
import com.udacity.baking.events.onStepDetailsRequestEvent;
import com.udacity.baking.fragments.RecipeDetailFragment;
import com.udacity.baking.fragments.StepDetailFragment;
import com.udacity.baking.models.Ingredient;
import com.udacity.baking.models.Recipe;
import com.udacity.baking.models.Step;
import com.udacity.baking.widgets.BakingWidgetProvider;

/**
 *
 * @author Erick Prieto
 * @since 2018
 */
public class RecipeDetailActivity extends AppCompatActivity {

    /**
     * Tag that identify all messages sent to loggger by this class.
     */
    private static final String TAG = RecipeDetailActivity.class.getSimpleName();

    private static final String DETAIL_RECIPE_FRAGMENT_TAG = "fragment_detail_recipe";
    private static final String STEP_DETAIL_FRAGMENT_TAG = "fragment_step_detail";

    public static final String ID_RECIPE = "recipe";

    private boolean twoPanels;

    private Recipe recipe;


    public Recipe getRecipe() {
        return recipe;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.containsKey(ID_RECIPE)) {
                recipe = getIntent().getParcelableExtra(ID_RECIPE);
            }
        } else if (savedInstanceState != null) {
            recipe = savedInstanceState.getParcelable(ID_RECIPE);
        } else {
            Log.wtf(TAG, "Recipe has NOT been received.");
            finish();
        }
        setTitle(recipe.getName());
        setContentView(R.layout.recipedetail_activity);
        twoPanels = getResources().getBoolean(R.bool.landscape);
        Log.d(TAG, "Two panels: " + twoPanels);

        loadDetailRecipeFragment();

        if (twoPanels) {
            loadStepDetailFragment(recipe.getName(), recipe.getSteps().get(0));
        }
    }

    private void loadDetailRecipeFragment() {
        final String TAG_M = "loadDetailRecipeFragment ";
        Log.v(TAG, TAG_M + recipe.toString());
        RecipeDetailFragment fr = RecipeDetailFragment.newInstance(recipe);
        Log.v(TAG, TAG_M + fr.getArguments().toString());
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.recipedetail_frame, fr, DETAIL_RECIPE_FRAGMENT_TAG)
                .commit();
    }

    private void loadStepDetailFragment(String recipeName, Step step) {
        final String TAG_M = "loadStepDetailFragment";
        StepDetailFragment fr = StepDetailFragment.newInstance(recipeName, step);
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.stepdetail_frame, fr, STEP_DETAIL_FRAGMENT_TAG)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.recipe_menu, menu);

        if ((BakingApplication.getSharedPreferences().getInt(BakingApplication.PREFERENCES_WIDGETPROVIDER_ID, -1) == recipe.getId())){
            menu.findItem(R.id.item_establishRecipeWidget).setIcon(R.drawable.ic_baseline_favorite_24px);
        } else {
            menu.findItem(R.id.item_establishRecipeWidget).setIcon(R.drawable.ic_baseline_widgets_24px);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.item_establishRecipeWidget) {
            boolean isRecipeInWidget = (BakingApplication.getSharedPreferences().getInt(BakingApplication.PREFERENCES_WIDGETPROVIDER_ID, -1) == recipe.getId());

            if (isRecipeInWidget) {
                BakingApplication.getSharedPreferences()
                        .edit()
                        .remove(BakingApplication.PREFERENCES_WIDGETPROVIDER_ID)
                        .remove(BakingApplication.PREFERENCES_WIDGETPROVIDER_TITLE)
                        .remove(BakingApplication.PREFERENCES_WIDGETPROVIDER_CONTENT)
                        .apply();

                item.setIcon(R.drawable.ic_baseline_widgets_24px);
                Toast.makeText(this, R.string.recipeActivity_widgetRemovedToast,Toast.LENGTH_SHORT).show();
            } else {
                BakingApplication.getSharedPreferences()
                        .edit()
                        .putInt(BakingApplication.PREFERENCES_WIDGETPROVIDER_ID, recipe.getId())
                        .putString(BakingApplication.PREFERENCES_WIDGETPROVIDER_TITLE, recipe.getName())
                        .putString(BakingApplication.PREFERENCES_WIDGETPROVIDER_CONTENT, ingredientsToString())
                        .apply();

                item.setIcon(R.drawable.ic_baseline_favorite_24px);
                Toast.makeText(this, R.string.recipeActivity_widgetAddedToast,Toast.LENGTH_SHORT).show();
            }

            // Put changes on the Widget
            ComponentName provider = new ComponentName(this, BakingWidgetProvider.class);
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
            int[] ids = appWidgetManager.getAppWidgetIds(provider);
            BakingWidgetProvider bakingWidgetProvider = new BakingWidgetProvider();
            bakingWidgetProvider.onUpdate(this, appWidgetManager, ids);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BakingApplication.getEventBus().register(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(ID_RECIPE, recipe);
    }

    @Override
    protected void onPause() {
        super.onPause();
        BakingApplication.getEventBus().unregister(this);
    }

    private String ingredientsToString(){
        StringBuilder result = new StringBuilder();
        for (Ingredient ingredient :  this.recipe.getIngredients()) {
            result
                    .append(ingredient.getQuantity())
                    .append(" ")
                    .append(ingredient.getMeasure())
                    .append(" ")
                    .append(ingredient.getIngredient())
                    .append("\n");
        }
        return result.toString();
    }

    @Subscribe
    public void loadStepDetailActivity(onStepDetailsRequestEvent event) {
        if(twoPanels) {
            loadStepDetailFragment(event.getRecipeName(), event.getStep());
        } else {
            Intent intent = new Intent(this, StepDetailActivity.class);
            intent.putExtra(StepDetailActivity.ID_ARGS_STEP, event.getStep());
            intent.putExtra(StepDetailActivity.ID_RECIPE_NAME_STEP, event.getRecipeName());
            startActivityForResult(intent,1 );
        }
    }
}
