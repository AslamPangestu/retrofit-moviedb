package com.mvryan.katalogfilm.db.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mvryan.katalogfilm.db.DBContract;
import com.mvryan.katalogfilm.db.FilmHelper;

import static com.mvryan.katalogfilm.db.DBContract.AUTHORITY;
import static com.mvryan.katalogfilm.db.DBContract.CONTENT_URI;

/**
 * Created by mvryan on 23/09/18.
 */

public class FilmProvider extends ContentProvider{

    private static final int FILM = 1;
    private static final int FILM_ID = 2;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        sUriMatcher.addURI(AUTHORITY, DBContract.TABLE_FAVOURITE, FILM);

        sUriMatcher.addURI(AUTHORITY,
                DBContract.TABLE_FAVOURITE+ "/#",
                FILM_ID);
    }

    private FilmHelper filmHelper;

    
    @Override
    public boolean onCreate() {
        filmHelper = new FilmHelper(getContext());
        filmHelper.open();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        switch(sUriMatcher.match(uri)){
            case FILM:
                cursor = filmHelper.queryProvider();
                break;
            case FILM_ID:
                cursor = filmHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }

        if (cursor != null){
            cursor.setNotificationUri(getContext().getContentResolver(),uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long added ;

        switch (sUriMatcher.match(uri)){
            case FILM:
                added = filmHelper.insertProvider(values);
                break;
            default:
                added = 0;
                break;
        }

        if (added > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return Uri.parse(CONTENT_URI + "/" + added);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int deleted;
        switch (sUriMatcher.match(uri)) {
            case FILM_ID:
                deleted =  filmHelper.deleteProvider(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }

        if (deleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return deleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int updated ;
        switch (sUriMatcher.match(uri)) {
            case FILM_ID:
                updated =  filmHelper.updateProvider(uri.getLastPathSegment(),values);
                break;
            default:
                updated = 0;
                break;
        }

        if (updated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return updated;
    }
}
