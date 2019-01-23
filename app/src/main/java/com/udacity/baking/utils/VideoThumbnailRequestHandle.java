package com.udacity.baking.utils;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.util.Log;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;
import com.squareup.picasso.RequestHandler;

import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author Erick Prieto
 * @since 2018
 */
public class VideoThumbnailRequestHandle extends RequestHandler {

    /**
     * Name of reference to log all records of events in this class.
     */
    private static final String TAG = VideoThumbnailRequestHandle.class.getSimpleName();

    public static final String SCHEME_VIDEO = "video";

    private Integer timeFrameAt;

    public VideoThumbnailRequestHandle(Integer timeFrameAt) {
        this.timeFrameAt = timeFrameAt;
    }

    public Integer getTimeFrameAt() {
        return timeFrameAt;
    }

    public void setTimeFrameAt(Integer timeFrameAt) {
        this.timeFrameAt = timeFrameAt;
    }

    @Override
    public boolean canHandleRequest(Request data)
    {
        String url = data.uri.toString();
        return (url.contains(".mp4"));
    }

    @Override
    public Result load(Request data, int arg1) throws IOException
    {
        try {
            Log.v(TAG,"load data: " + data.uri.toString());
            Bitmap bmp = retriveVideoFrameFromVideo(data.uri.toString(), timeFrameAt);
            Log.v(TAG,"load bmp: " + bmp);
            return new Result(bmp, Picasso.LoadedFrom.DISK);
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
            throw new IOException(ex.getMessage());
        }
    }

    private Bitmap retriveVideoFrameFromVideo(String videoPath, Integer timeUs) throws Exception
    {
        final String TAG_M = "retriveVideoFrameFromVideo: ";
        Bitmap bitmap = null;
        MediaMetadataRetriever mediaMetadataRetriever = null;
        try
        {
            mediaMetadataRetriever = new MediaMetadataRetriever();
            if (Build.VERSION.SDK_INT >= 14) {
                mediaMetadataRetriever.setDataSource(videoPath, new HashMap<String, String>());
            } else {
                mediaMetadataRetriever.setDataSource(videoPath);
            }
            bitmap = mediaMetadataRetriever.getFrameAtTime(timeUs, MediaMetadataRetriever.OPTION_NEXT_SYNC);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Exception in retriveVideoFrameFromVideo(String videoPath)" + e.getMessage());

        } finally {
            if (mediaMetadataRetriever != null) {
                mediaMetadataRetriever.release();
            }
        }
        Log.v(TAG, TAG_M + bitmap);
        return bitmap;
    }
}
