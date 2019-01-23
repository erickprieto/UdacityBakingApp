package com.udacity.baking.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.baking.BakingApplication;
import com.udacity.baking.R;
import com.udacity.baking.adapters.viewholders.ListRecipeViewHolder;
import com.udacity.baking.events.onRecipeDetailsRequestEvent;
import com.udacity.baking.models.Recipe;
import com.udacity.baking.utils.VideoThumbnailDownloader;

import java.util.List;

/**
 *
 * @author Erick Prieto
 * @since 2018
 */
public class RecipeListAdapter extends RecyclerView.Adapter<ListRecipeViewHolder> {

    /**
     * Name of reference to log all records of events in this class.
     */
    private static final String TAG = RecipeListAdapter.class.getSimpleName();

    /**
     * Data that contained a List <code>Recipe</code> to fill this Adapter.
     */
    private List<Recipe> recipeList;

    private Context context;

    /**
     * Default Constructor with Data to fill this adapter.
     *
     * @param recipeList recipes
     * @param context context
     */
    public RecipeListAdapter(List<Recipe> recipeList, Context context) {
        this.recipeList = recipeList;
        this.context = context;
    }

    @NonNull
    @Override
    public ListRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View recipeItemView = inflater.inflate(R.layout.recipe_itemcardview, parent, false);

        ListRecipeViewHolder viewHolder = new ListRecipeViewHolder(recipeItemView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListRecipeViewHolder holder, int position) {
        final String TAG_M = "onBindViewHolder() ";
        final Recipe recipe = recipeList.get(position);
        VideoThumbnailDownloader.downloaderRecipeVideoThumbnail(recipe.getSteps().get(2).getVideoURL()
                , context
                , holder.getThumbnailRecipeImageView(), 30);
        holder.getNameRecipeTextView().setText(recipe.getName());
        holder.getServingsRecipeTextView().setText(formatServings(recipe.getServings()));

        holder.getThumbnailRecipeImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BakingApplication.getEventBus().post(new onRecipeDetailsRequestEvent(recipe));
                Log.v(TAG, "Click " + recipe.getName());
            }
        });
        holder.getThumbnailRecipeImageView().setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                Log.v(TAG, "Long Click " + recipe.getName());
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        int count= 0;

        if(this.recipeList != null && !this.recipeList.isEmpty()) {
            count = this.recipeList.size();
        }
        return count;
    }

    private String formatServings(Integer servings) {
        if (servings == null) { return context.getResources().getString(R.string.servings); }
        return String.format(context.getResources().getString(R.string.servings), servings);
    }

    /**
     * Update {@link RecipeListAdapter#recipeList} without rewrite the variable.
     * After of to update, launch event to notify {@link RecyclerView.Adapter#notifyDataSetChanged()}
     * of changes.
     * @param recipes New list<code>Recipe</code> to update.
     */
    public void putRecipes(List<Recipe> recipes) {
        if (recipes == null) { return; }
        this.recipeList.clear();
        this.recipeList.addAll(recipes);
        notifyDataSetChanged();
    }
}
