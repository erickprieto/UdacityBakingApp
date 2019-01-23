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
import com.udacity.baking.adapters.viewholders.ListStepViewHolder;
import com.udacity.baking.events.onStepDetailsRequestEvent;
import com.udacity.baking.models.Step;
import com.udacity.baking.utils.VideoThumbnailDownloader;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 *
 * @author Erick Prieto
 * @since 2018
 */
public class StepListAdapter extends RecyclerView.Adapter<ListStepViewHolder> {

    /**
     * Name of reference to log all records of events in this class.
     */
    private static final String TAG = StepListAdapter.class.getSimpleName();

    /**
     * Data that contained a List <code>Step</code> to fill this Adapter.
     */
    private List<Step> stepList;

    private Context context;

    private String recipeName;

    /**
     * Default Constructor with Data to fill this adapter.
     *
     * @param stepList steps
     * @param context context
     */
    public StepListAdapter(List<Step> stepList, Context context)  {
        this.stepList = stepList;
        this.context = context;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    @NonNull
    @Override
    public ListStepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View stepItemView = inflater.inflate(R.layout.step_itemcardview, parent, false);

        ListStepViewHolder viewHolder = new ListStepViewHolder(stepItemView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListStepViewHolder holder, int position) {
        final String TAG_M = "onBindViewHolder() ";
        final Step step = stepList.get(position);

        if(StringUtils.isNotEmpty(step.getVideoURL())) {
            VideoThumbnailDownloader.downloaderStepVideoThumbnail(step.getVideoURL()
                    , context
                    , holder.getStepPictureImageView(), 30);
        } else {
            holder.getStepPictureImageView().setImageResource(R.drawable.ic_baseline_broken_image_24px);
        }


        holder.getStepNumberTextView().setText(stepFormat(step.getId()));
        holder.getStepDescriptionTextView().setText(step.getShortDescription());

        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BakingApplication.getEventBus().post(new onStepDetailsRequestEvent(recipeName, step));
                Log.v(TAG, "Click " + step.getShortDescription());
            }
        });

        holder.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                Log.v(TAG, "Long Click " + step.getShortDescription());
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        int count = 0;

        if(this.stepList != null && !this.stepList.isEmpty()) {
            count = this.stepList.size();
        }
        return count;
    }

    private String stepFormat(Integer stepNumber) {
        return "Step " + stepNumber + ".";
    }

    /**
     * Update {@link StepListAdapter#stepList} without rewrite the variable.
     * After of to update, launch event to notify {@link RecyclerView.Adapter#notifyDataSetChanged()}
     * of changes.
     * @param steps New list<code>Step</code> to update.
     */
    public void putSteps(List<Step> steps) {
        if (steps == null) { return; }
        this.stepList.clear();
        this.stepList.addAll(steps);
        notifyDataSetChanged();
    }

}
