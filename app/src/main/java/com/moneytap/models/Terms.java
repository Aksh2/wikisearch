package com.moneytap.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Terms {
    @SerializedName("description")
    private List<String> description;

    public List<String> getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Terms{" +
                "description=" + description +
                '}';
    }
}
