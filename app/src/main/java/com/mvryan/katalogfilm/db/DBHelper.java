package com.mvryan.katalogfilm.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.mvryan.katalogfilm.db.DBContract.FavouriteColumn.ID;
import static com.mvryan.katalogfilm.db.DBContract.FavouriteColumn.OVERVIEW;
import static com.mvryan.katalogfilm.db.DBContract.FavouriteColumn.POPULARITY;
import static com.mvryan.katalogfilm.db.DBContract.FavouriteColumn.POSTER_PATH;
import static com.mvryan.katalogfilm.db.DBContract.FavouriteColumn.RELEASE_DATE;
import static com.mvryan.katalogfilm.db.DBContract.FavouriteColumn.TITLE;
import static com.mvryan.katalogfilm.db.DBContract.FavouriteColumn.VOTE;
import static com.mvryan.katalogfilm.db.DBContract.TABLE_FAVOURITE;

/**
 * Created by mvryan on 08/09/18.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "dbfilm";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_TABLE_FAVOURITE = "create table " + TABLE_FAVOURITE +
            " ( " + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            TITLE + " TEXT NOT NULL," +
            POPULARITY + " TEXT NOT NULL," +
            VOTE + " TEXT NOT NULL," +
            OVERVIEW + " TEXT NOT NULL," +
            RELEASE_DATE + " TEXT NOT NULL," +
            POSTER_PATH + " TEXT NOT NULL);";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_FAVOURITE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVOURITE);
        onCreate(db);
    }
}
