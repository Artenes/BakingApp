package com.artenesnogueira.bakingapp.model;

import android.net.Uri;

/**
 * Information about a video being played
 */
public class VideoInformation {

    private final Uri uri;
    private final long currentPosition;
    private boolean autoPlay;

    public VideoInformation(Uri uri, long currentPosition, boolean autoPlay) {
        this.uri = uri;
        this.currentPosition = currentPosition;
        this.autoPlay = autoPlay;
    }

    public Uri getUri() {
        return uri;
    }

    public long getCurrentPosition() {
        return currentPosition;
    }

    public boolean isAutoPlay() {
        return autoPlay;
    }

}