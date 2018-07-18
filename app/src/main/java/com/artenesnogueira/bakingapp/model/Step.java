package com.artenesnogueira.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * A step to do in a recipe.
 */
@SuppressWarnings({"unused", "CanBeFinal"})
public class Step implements Parcelable {

    private int id;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailURL;

    public int getId() {
        return id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public String getVideoURL() {
        String url = videoURL;
        if (url.isEmpty()) {
            url = thumbnailURL;
        }
        if (url.endsWith(".mp4")) {
            return url;
        }
        return videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public boolean hasVideo() {
        return !getVideoURL().isEmpty();
    }

    public boolean hasThumbnail() {
        String url = getThumbnailURL();
        return !url.isEmpty() &&
                (url.endsWith(".jpg") || url.endsWith(".jpeg") || url.endsWith(".png"));
    }

    private Step(Parcel in) {
        id = in.readInt();
        shortDescription = in.readString();
        description = in.readString();
        videoURL = in.readString();
        thumbnailURL = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(shortDescription);
        dest.writeString(description);
        dest.writeString(videoURL);
        dest.writeString(thumbnailURL);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Step> CREATOR = new Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel in) {
            return new Step(in);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };

}