package com.example.bakingapp;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
public  class Steps implements Parcelable {
    private String descroption;
    private String videoURL;
    private String shortDescription;


    protected Steps(Parcel in) {
        descroption = in.readString();
        videoURL = in.readString();
        shortDescription = in.readString();
    }

    public static final Creator<Steps> CREATOR = new Creator<Steps>() {
        @Override
        public Steps createFromParcel(Parcel in) {
            return new Steps(in);
        }

        @Override
        public Steps[] newArray(int size) {
            return new Steps[size];
        }
    };

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }


    public String getDescroption() {
        return descroption;
    }

    public void setDescroption(String descroption) {
        this.descroption = descroption;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public Steps(String descroption,String shortDescription, String videoURL) {
        this.descroption = descroption;
        this.videoURL = videoURL;
        this.shortDescription = shortDescription;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(descroption);
        dest.writeString(videoURL);
        dest.writeString(shortDescription);
    }
}
