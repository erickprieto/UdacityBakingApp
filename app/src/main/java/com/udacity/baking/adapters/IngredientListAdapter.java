package com.udacity.baking.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.baking.R;
import com.udacity.baking.adapters.viewholders.ListIngredientViewHolder;
import com.udacity.baking.models.Ingredient;

import java.util.List;

/**
 *
 * @author Erick Prieto
 * @since 2018
 */
public class IngredientListAdapter extends RecyclerView.Adapter<ListIngredientViewHolder> {

    /**
     * Name of reference to log all records of events in this class.
     */
    private static final String TAG = IngredientListAdapter.class.getSimpleName();

    /**
     * Data that contained a List <code>Ingredient</code> to fill this Adapter.
     */
    private List<Ingredient> ingredientList;

    private Context context;

    /**
     * Default Constructor with Data to fill this adapter.
     *
     * @param ingredientList ingredients
     * @param context context
     */
    public IngredientListAdapter(List<Ingredient> ingredientList, Context context) {
        this.ingredientList = ingredientList;
        this.context = context;
    }

    @NonNull
    @Override
    public ListIngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View ingredientItemView = inflater.inflate(R.layout.ingredient_item, parent, false);

        ListIngredientViewHolder viewHolder = new ListIngredientViewHolder(ingredientItemView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListIngredientViewHolder holder, int position) {
        final String TAG_M = "onBindViewHolder() ";
        final Ingredient ingredient = ingredientList.get(position);

        holder.getIngredientQtyTextView().setText(formatIngredientQuantity(ingredient));
        holder.getIngredientNameTextView().setText(ingredient.getIngredient());
    }

    @Override
    public int getItemCount() {
        int count = 0;

        if(this.ingredientList != null && !this.ingredientList.isEmpty()) {
            count = this.ingredientList.size();
        }
        return count;
    }

    /**
     * Update {@link IngredientListAdapter#ingredientList} without rewrite the variable.
     * After of to update, launch event to notify {@link RecyclerView.Adapter#notifyDataSetChanged()}
     * of changes.
     * @param ingredients New list<code>Ingredient</code> to update.
     */
    public void putIngredients(List<Ingredient> ingredients) {
        if (ingredients == null) { return; }
        this.ingredientList.clear();
        this.ingredientList.addAll(ingredients);
        notifyDataSetChanged();
    }

    private String formatIngredientQuantity(Ingredient ingredient) {
        return "(" + ingredient.getQuantity() + " " + ingredient.getMeasure() + ")";
    }

}
