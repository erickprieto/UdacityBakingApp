package com.udacity.baking.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.udacity.baking.R;

/**
 *
 * @author Erick Prieto
 * @since 2018
 */
public class ListRecipeViewHolder extends RecyclerView.ViewHolder {


    public ListRecipeViewHolder(View itemView) {
        super(itemView);

    }

    public ImageView getThumbnailRecipeImageView() {
        return (ImageView) itemView.findViewById(R.id.recipePicture_ImageView);
    }

    public TextView getNameRecipeTextView() {
        return (TextView) itemView.findViewById(R.id.recipeName_TextView);
    }

    public TextView getServingsRecipeTextView() {
        return (TextView) itemView.findViewById(R.id.recipeServings_TextView);
    }
}
