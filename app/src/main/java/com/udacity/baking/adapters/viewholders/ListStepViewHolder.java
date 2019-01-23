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
public class ListStepViewHolder extends RecyclerView.ViewHolder {

    public ListStepViewHolder(View itemView) {
        super(itemView);
    }

    public void setOnClickListener(View.OnClickListener listener) {
        itemView.setOnClickListener(listener);
    }

    public void setOnLongClickListener(View.OnLongClickListener listener) {
        itemView.setOnLongClickListener(listener);
    }

    public ImageView getStepPictureImageView() {
        return (ImageView) itemView.findViewById(R.id.stepPicture_ImageView);
    }

    public TextView getStepNumberTextView() {
        return (TextView) itemView.findViewById(R.id.stepNumber_TextView);
    }

    public TextView getStepDescriptionTextView() {
        return (TextView) itemView.findViewById(R.id.stepDescription_TextView);
    }
}
