package com.udacity.baking.utils;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.util.Pair;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.udacity.baking.BakingApplication;
import com.udacity.baking.R;

import java.util.Vector;

public class VideoThumbnailDownloader {

    /**
     * Name of reference to log all records of events in this class.
     */
    private static final String TAG = VideoThumbnailDownloader.class.getSimpleName();

    private static final Point resize = new Point(384,216);

    private static VideoThumbnailRequestHandle videoRequestHandler =
            new VideoThumbnailRequestHandle(0);

    private static Picasso picassoInstance;

    private static Picasso getPicassoInstanceSingleton(Context context, Integer timeFrameAt) {
        videoRequestHandler.setTimeFrameAt(timeFrameAt);
        if (picassoInstance == null) {
            picassoInstance = new Picasso.Builder(context.getApplicationContext())
                    .addRequestHandler(videoRequestHandler)
                    .indicatorsEnabled(BakingApplication.isDebugVersion())
                    .build();
        }
        return picassoInstance;
    }

    public static void downloaderRecipeVideoThumbnail(String filepath, Context context, ImageView view, Integer timeFrameAt) {
        final String TAG_M = "downloaderRecipeVideoThumbnail: ";
        Log.v(TAG, TAG_M + filepath);
        VideoThumbnailDownloader
                .getPicassoInstanceSingleton(context, timeFrameAt)
                .load(filepath)
                .error(R.drawable.ic_baseline_broken_image_24px)
                .resize(resize.x, resize.y)
                .into(view);
    }

    public static void downloaderStepVideoThumbnail(String filepath, Context context, ImageView view, Integer timeFrameAt) {
        final String TAG_M = "downloaderStepVideoThumbnail: ";
        Log.v(TAG, TAG_M + filepath);
        VideoThumbnailDownloader
                .getPicassoInstanceSingleton(context, timeFrameAt)
                .load(filepath)
                .error(R.drawable.ic_baseline_broken_image_24px)
                .resize(80, 80)
                .centerCrop()
                .into(view);
    }

    private VideoThumbnailDownloader() {

    }
}
