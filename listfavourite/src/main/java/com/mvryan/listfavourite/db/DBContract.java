package com.mvryan.listfavourite.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by mvryan on 23/09/18.
 */

public class DBContract {

    public static String TABLE_FAVOURITE = "tbl_favourite";

    public static final class FavouriteColumn implements BaseColumns {
        public static String _ID="_id";
        public static String TITLE = "title";
        public static String POSTER_PATH = "poster_path";
        public static String RELEASE_DATE = "release_date";
        public static String VOTE = "vote";
        public static String POPULARITY = "popularity";
        public static String OVERVIEW = "overview";
    }

    public static final String AUTHORITY = "com.mvryan.katalogfilm";
    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_FAVOURITE)
            .build();
    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString( cursor.getColumnIndex(columnName) );
    }
    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt( cursor.getColumnIndex(columnName) );
    }
    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong( cursor.getColumnIndex(columnName) );
    }

}
