package com.moneytap.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Query {

    @SerializedName("pages")
    private List<Page> pagesList;

    public List<Page> getPagesList() {
        return pagesList;
    }

    @Override
    public String toString() {
        return "Query{" +
                "pagesList=" + pagesList +
                '}';
    }
}
