package com.moneytap.models;

import java.util.List;

public class Response {
    private Query query;

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    @Override
    public String toString() {
        return "Response{" +
                "query=" + query +
                '}';
    }
}
