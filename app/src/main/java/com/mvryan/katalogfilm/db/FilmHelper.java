package com.mvryan.katalogfilm.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.mvryan.katalogfilm.model.Film;

import java.util.ArrayList;

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

public class FilmHelper {

    private static String DATABASE_TABLE = TABLE_FAVOURITE;
    private Context context;
    private DBHelper dbHelper;

    private SQLiteDatabase database;

    public FilmHelper(Context context) {
        this.context = context;
    }

    public FilmHelper open() throws SQLException {
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public ArrayList<Film> query() {
        ArrayList<Film> arrayList = new ArrayList<Film>();
        Film film;
        Cursor cursor = database.query(DATABASE_TABLE, null, null,null, null, null, ID + " DESC", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            do {
                film = new Film();
                film.setId(cursor.getInt(cursor.getColumnIndexOrThrow(ID)));
                film.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                film.setRelease_date(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE)));
                film.setPopularity(cursor.getString(cursor.getColumnIndexOrThrow(POPULARITY)));
                film.setPoster_path(cursor.getString(cursor.getColumnIndexOrThrow(POSTER_PATH)));
                film.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                film.setVote_average(cursor.getString(cursor.getColumnIndexOrThrow(VOTE)));

                arrayList.add(film);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(Film film) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(ID, film.getId());
        initialValues.put(TITLE, film.getTitle());
        initialValues.put(RELEASE_DATE, film.getRelease_date());
        initialValues.put(POPULARITY, film.getPopularity());
        initialValues.put(POSTER_PATH, film.getPoster_path());
        initialValues.put(OVERVIEW, film.getOverview());
        initialValues.put(VOTE, film.getVote_average());
        return database.insert(DATABASE_TABLE, null, initialValues);
    }

    public int delete(int id) {
        return database.delete(TABLE_FAVOURITE, ID + " = '" + id + "'", null);
    }

    public Cursor queryByIdProvider(String id){
        return database.query(DATABASE_TABLE,null
                ,ID + " = ?"
                ,new String[]{id}
                ,null
                ,null
                ,null
                ,null);
    }
    public Cursor queryProvider(){
        return database.query(DATABASE_TABLE
                ,null
                ,null
                ,null
                ,null
                ,null
                ,ID + " DESC");
    }
    public long insertProvider(ContentValues values){
        return database.insert(DATABASE_TABLE,null,values);
    }

    public int updateProvider(String id,ContentValues values){
        return database.update(DATABASE_TABLE,values,ID +" = ?",new String[]{id} );
    }

    public int deleteProvider(String id){
        return database.delete(DATABASE_TABLE,ID + " = ?", new String[]{id});
    }

}
