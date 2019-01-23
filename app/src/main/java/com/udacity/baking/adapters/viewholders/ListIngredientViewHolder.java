package com.udacity.baking.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.udacity.baking.R;

/**
 *
 * @author Erick Prieto
 * @since 2018
 */
public class ListIngredientViewHolder extends RecyclerView.ViewHolder {

    public ListIngredientViewHolder(View itemView) {
        super(itemView);
    }

    public TextView getIngredientQtyTextView() {
        return itemView.findViewById(R.id.ingredientItem_qtyTextView);
    }

    public TextView getIngredientNameTextView() {
        return itemView.findViewById(R.id.ingredientItem_nameTextView);
    }
}
