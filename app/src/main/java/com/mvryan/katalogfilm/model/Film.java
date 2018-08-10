package com.mvryan.katalogfilm.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mvryan on 29/07/18.
 */

public class Film {

    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("release_date")
    private String release_date;
    @SerializedName("overview")
    private String overview;
    @SerializedName("poster_path")
    private String poster_path;
    @SerializedName("vote_average")
    private float vote_average;
    @SerializedName("popularity")
    private float popularity;
    @SerializedName("genre_ids")
    private List<Genre> genreIds;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getOverview() {
        return overview;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public float getVote_average() {
        return vote_average;
    }

    public float getPopularity() {
        return popularity;
    }

    public List<Genre> getGenreIds() {
        return genreIds;
    }
}
