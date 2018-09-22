package com.mvryan.katalogfilm.db;

import android.provider.BaseColumns;

/**
 * Created by mvryan on 08/09/18.
 */

public class DBContract {

    static String TABLE_FAVOURITE = "tbl_favourite";

    static final class FavouriteColumn implements BaseColumns {
        static String TITLE = "title";
        static String POSTER_PATH = "poster_path";
        static String RELEASE_DATE = "release_date";
        static String VOTE = "vote";
        static String POPULARITY = "popularity";
        static String OVERVIEW = "overview";
        static String ID="id";
    }
}
