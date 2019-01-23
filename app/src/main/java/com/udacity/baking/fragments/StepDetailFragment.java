package com.udacity.baking.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;
import com.udacity.baking.R;
import com.udacity.baking.models.Step;

/**
 *
 * @author Erick Prieto
 * @since 2018
 */
public class StepDetailFragment extends Fragment {

    /**
     * Tag that identify all messages sent to loggger by this class.
     */
    private static final String TAG = StepDetailFragment.class.getSimpleName();

    private static final String ID_ARGS_RECIPE_NAME = "recipe";
    private static final String ID_ARGS_STEP = "step";
    private static final String SELECTED_POSITION = "selected_position";
    private static final String PLAY_WHEN_READY = "play_when_ready";

    private String recipeName;
    private Step step;
    private SimpleExoPlayer exoPlayer;
    private boolean playWhenReady = true;
    private long position = -1;

    public static StepDetailFragment newInstance(String recipeName, Step step) {
        StepDetailFragment stepDetailFragment = new StepDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ID_ARGS_STEP, step);
        args.putString(ID_ARGS_RECIPE_NAME, recipeName);
        stepDetailFragment.setArguments(args);
        return stepDetailFragment;
    }

    private TextView getShortDescriptionTextView() {
        return getActivity().findViewById(R.id.detailStepsFragment_descriptionTextView);
    }

    private TextView getDescriptionTextView() {
        return getActivity().findViewById(R.id.detailStepsFragment_descriptionTextView);
    }

    private ImageView getThumbnailView() {
        return getActivity().findViewById(R.id.detailStepsFragment_thumbnailView);
    }

    private SimpleExoPlayerView getPlayerView() {
        return getActivity().findViewById(R.id.detailStepsFragment_playerView);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (getArguments() != null) {
            this.step = getArguments().getParcelable(ID_ARGS_STEP);
            this.recipeName = getArguments().getString(ID_ARGS_RECIPE_NAME);
        } else {
            Log.wtf(TAG, "No args received");
        }

        if (savedInstanceState != null){
            position = savedInstanceState.getLong(SELECTED_POSITION, C.TIME_UNSET);
            playWhenReady = savedInstanceState.getBoolean(PLAY_WHEN_READY);
        }

        return inflater.inflate(R.layout.detailstep_fragment, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        getShortDescriptionTextView().setText(step.getShortDescription());
        getDescriptionTextView().setText(step.getDescription());

        // General Case
        // 1 - If Video exists: Show Video
        // 2 - If Thumbnail exists: Show image view with the thumbnail where at the place where the video would normally show up.

        // If video exists, initialize player (General case 1)
        if (!TextUtils.isEmpty(step.getVideoURL())) {
            initializerPlayer(Uri.parse(step.getVideoURL()));
        } else if (!step.getThumbnailURL().isEmpty()) {
            // if Thumbnail exists but no video exists (General case 2)
            getThumbnailView().setVisibility(View.VISIBLE);
            Picasso.with(getContext())
                    .load(step.getThumbnailURL())
                    .into(getThumbnailView());
        }


        setActivityTitle();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(SELECTED_POSITION, position);
        outState.putBoolean(PLAY_WHEN_READY , playWhenReady);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (exoPlayer != null){
            position = exoPlayer.getCurrentPosition();
            playWhenReady = exoPlayer.getPlayWhenReady();
            releasePlayer();
        }
    }

    private void setActivityTitle() {
        getActivity().setTitle(recipeName
                        + " "
                        + getString(R.string.detailStepsFragment_title)
                        + " "
                        + step.getId());
    }

    private void initializerPlayer(Uri mediaUri){
        if (exoPlayer == null) {
            getPlayerView().setVisibility(View.VISIBLE);
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            exoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            getPlayerView().setPlayer(exoPlayer);

            getPlayerView().setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH);

            String userAgent = Util.getUserAgent(getContext(), "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            exoPlayer.prepare(mediaSource);
            if (position > 0) {
                exoPlayer.seekTo(position);
            }
            exoPlayer.setPlayWhenReady(playWhenReady);
        }
    }

    private void releasePlayer() {
        if (exoPlayer != null) {
            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;
        }
    }
}
