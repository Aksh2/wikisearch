package com.moneytap.models;

import com.google.gson.annotations.SerializedName;

public class Page {

    private int pageid;
    private String title;
    private int index;
    private Thumbnail thumbnail;
    private Terms terms;
    @SerializedName("fullurl")
    private String url;

    @Override
    public String toString() {
        return "Page{" +
                "pageid=" + pageid +
                ", title='" + title + '\'' +
                ", index=" + index +
                ", thumbnail=" + thumbnail +
                ", terms=" + terms +
                ", url='" + url + '\'' +
                '}';
    }

    public String getUrl() {
        return url;
    }

    public int getPageid() {
        return pageid;
    }

    public String getTitle() {
        return title;
    }

    public int getIndex() {
        return index;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public Terms getTerms() {
        return terms;
    }


}
