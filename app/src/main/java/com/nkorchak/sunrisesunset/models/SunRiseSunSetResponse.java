package com.nkorchak.sunrisesunset.models;

/**
 * Created by nazarkorchak on 23.01.18.
 */

public class SunRiseSunSetResponse {

    private Results results;

    private String status;

    public Results getResults() {
        return results;
    }

    public void setResults(Results results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}