package com.mvryan.katalogfilm.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.mvryan.katalogfilm.db.DBContract;

import java.util.List;

import static com.mvryan.katalogfilm.db.DBContract.getColumnInt;
import static com.mvryan.katalogfilm.db.DBContract.getColumnString;

/**
 * Created by mvryan on 29/07/18.
 */

public class Film implements Parcelable {

    private String vote_average;

    private String backdrop_path;

    private String adult;

    private int id;

    private String title;

    private String overview;

    private String original_language;

    private String[] genre_ids;

    private String release_date;

    private String original_title;

    private String vote_count;

    private String poster_path;

    private String video;

    private String popularity;

    public Film() {
    }

    public Film(Cursor cursor){
        this.id = getColumnInt(cursor, DBContract.FavouriteColumn.ID);
        this.title = getColumnString(cursor, DBContract.FavouriteColumn.TITLE);
        this.poster_path = getColumnString(cursor, DBContract.FavouriteColumn.POSTER_PATH);
        this.popularity = getColumnString(cursor, DBContract.FavouriteColumn.POPULARITY);
        this.overview = getColumnString(cursor, DBContract.FavouriteColumn.OVERVIEW);
        this.release_date = getColumnString(cursor, DBContract.FavouriteColumn.RELEASE_DATE);
        this.vote_average = getColumnString(cursor, DBContract.FavouriteColumn.VOTE);
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public void setAdult(String adult) {
        this.adult = adult;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public void setGenre_ids(String[] genre_ids) {
        this.genre_ids = genre_ids;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public void setVote_count(String vote_count) {
        this.vote_count = vote_count;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getVote_average() {
        return vote_average;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public String getAdult() {
        return adult;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public String[] getGenre_ids() {
        return genre_ids;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getVote_count() {
        return vote_count;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getVideo() {
        return video;
    }

    public String getPopularity() {
        return popularity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.poster_path);
        dest.writeString(this.popularity);
        dest.writeString(this.vote_average);
        dest.writeString(this.release_date);
        dest.writeString(this.overview);
    }

    protected Film(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.poster_path = in.readString();
        this.popularity = in.readString();
        this.vote_average = in.readString();
        this.release_date = in.readString();
        this.overview = in.readString();
    }

    public static final Parcelable.Creator<Film> CREATOR = new Parcelable.Creator<Film>() {

        @Override
        public Film createFromParcel(Parcel source) {
            return new Film(source);
        }

        @Override
        public Film[] newArray(int size) {
            return new Film[size];
        }
    };
}
