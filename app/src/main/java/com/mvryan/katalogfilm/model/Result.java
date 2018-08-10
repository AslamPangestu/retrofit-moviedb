package com.mvryan.katalogfilm.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mvryan on 24/07/18.
 */

public class Result {

    @SerializedName("page")
    private int page;
    @SerializedName("total_results")
    private int totalResult;
    @SerializedName("total_pages")
    private int totalPage;
    @SerializedName("results")
    private List<Film> filmList;

    public int getPage() {
        return page;
    }

    public int getTotalResult() {
        return totalResult;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public List<Film> getFilmList() {
        return filmList;
    }
}
