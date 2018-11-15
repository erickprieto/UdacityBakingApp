package com.udacity.baking.utils;

import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.udacity.baking.R;

public class VideoThumbnailDownloader {

    /**
     * Name of reference to log all records of events in this class.
     */
    private static final String TAG = VideoThumbnailDownloader.class.getSimpleName();

    private static VideoThumbnailRequestHandle videoRequestHandler;

    private static Picasso picassoInstance;

    private static Picasso getPicassoInstanceSingleton(Context context) {
        if (picassoInstance == null) {
            videoRequestHandler = new VideoThumbnailRequestHandle(30);
            picassoInstance = new Picasso.Builder(context.getApplicationContext())
                    .addRequestHandler(videoRequestHandler)
                    .indicatorsEnabled(true)
                    .build();
        }

        return picassoInstance;
    }

    public static void downloader(String filepath, Context context, ImageView view) {
        final String TAG_M = "downloader: ";
        Log.v(TAG, TAG_M + filepath);
        VideoThumbnailDownloader
                .getPicassoInstanceSingleton(context)
                .load(filepath)
                .error(R.drawable.ic_baseline_broken_image_24px)
                .resize(384,216)
                .into(view);
    }

    private VideoThumbnailDownloader() {

    }
}
