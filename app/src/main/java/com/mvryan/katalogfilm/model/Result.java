package com.mvryan.katalogfilm.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mvryan on 24/07/18.
 */

public class Result {

    private List<Film> results;

    private String page;

    private String total_pages;

    private String total_results;

    public List<Film> getResults() {
        return results;
    }

    public String getPage() {
        return page;
    }

    public String getTotal_pages() {
        return total_pages;
    }

    public String getTotal_results() {
        return total_results;
    }
}
