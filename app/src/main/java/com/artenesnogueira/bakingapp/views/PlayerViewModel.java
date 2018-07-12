package com.artenesnogueira.bakingapp.views;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.artenesnogueira.bakingapp.R;
import com.artenesnogueira.bakingapp.model.VideoInformation;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.HashMap;
import java.util.Map;

/**
 * View model to manage the playback of a video
 */
public class PlayerViewModel extends AndroidViewModel {

    private SimpleExoPlayer mExoPlayer;
    private final Map<Uri, VideoInformation> mVideos;
    private Uri mCurrentVideoUri;

    public PlayerViewModel(@NonNull Application application) {
        super(application);
        TrackSelector trackSelector = new DefaultTrackSelector();
        LoadControl loadControl = new DefaultLoadControl();
        mExoPlayer = ExoPlayerFactory.newSimpleInstance(application, trackSelector, loadControl);
        mVideos = new HashMap<>();
    }

    /**
     * Get the player instance
     *
     * @return the player instance
     */
    public SimpleExoPlayer getPlayer() {
        return mExoPlayer;
    }

    /**
     * Set the video to play.
     * This will save the state of the previous video playing (if any)
     * before setting up the new video.
     *
     * @param uri the uri of the video
     */
    public void setVideo(@NonNull Uri uri) {
        savePreviousVideoState(uri.equals(mCurrentVideoUri));

        mCurrentVideoUri = uri;

        VideoInformation videoInformation = mVideos.get(mCurrentVideoUri);
        if (videoInformation == null) {
            videoInformation = new VideoInformation(mCurrentVideoUri, 0, false);
            mVideos.put(uri, videoInformation);
        }

        String userAgent = Util.getUserAgent(getApplication(), getApplication().getString(R.string.app_name));
        MediaSource mediaSource = new ExtractorMediaSource(uri, new DefaultDataSourceFactory(getApplication(), userAgent), new DefaultExtractorsFactory(), null, null);
        mExoPlayer.prepare(mediaSource);
        mExoPlayer.setPlayWhenReady(videoInformation.isAutoPlay());
        mExoPlayer.seekTo(videoInformation.getCurrentPosition());
    }

    /**
     * Stop the player
     */
    public void stop() {
        mExoPlayer.stop();
    }

    /**
     * Save the state of the previous video playing
     *
     * @param isSameAsNewOne true if the new video that is about to be played is the same as the previous one, false otherwise
     *                       this flag is used to save or not if the video will auto play when it finishis buffering. When
     *                       we rotate the screen we want it to keep playing, but when we swap the videos, it must be stopped.
     */
    private void savePreviousVideoState(boolean isSameAsNewOne) {
        //if the new one is the same as the previous one, save if the video should play automatically
        //otherwise just don`t play automatically new videos added to the map
        boolean startAutoPlay = isSameAsNewOne && mExoPlayer.getPlayWhenReady();
        long startPosition = Math.max(0, mExoPlayer.getCurrentPosition());
        mVideos.put(mCurrentVideoUri, new VideoInformation(mCurrentVideoUri, startPosition, startAutoPlay));
    }

    @Override
    protected void onCleared() {
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }

}
