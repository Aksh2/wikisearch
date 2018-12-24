package com.moneytap.models;

import com.google.gson.annotations.SerializedName;

public class Thumbnail {

    @SerializedName("source")
    private String url;

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "Thumbnail{" +
                "url='" + url + '\'' +
                '}';
    }
}
