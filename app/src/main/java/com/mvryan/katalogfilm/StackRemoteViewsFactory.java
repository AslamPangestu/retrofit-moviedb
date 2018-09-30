package com.mvryan.katalogfilm;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.mvryan.katalogfilm.db.DBContract;
import com.mvryan.katalogfilm.model.Film;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static android.provider.BaseColumns._ID;
import static com.mvryan.katalogfilm.db.DBContract.FavouriteColumn.POSTER_PATH;
import static com.mvryan.katalogfilm.db.DBContract.FavouriteColumn.TITLE;

/**
 * Created by mvryan on 30/09/18.
 */

class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private Cursor cursor;

    public StackRemoteViewsFactory(Context applicationContext) {
        mContext = applicationContext;
    }

    @Override
    public void onCreate() {
        initDatabase();
    }

    @Override
    public void onDataSetChanged() {
        if (cursor != null) cursor.close();
        long token = Binder.clearCallingIdentity();
        initDatabase();
        Binder.restoreCallingIdentity(token);
    }

    @Override
    public void onDestroy() {
        if (cursor == null) return;
        cursor.close();
    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Film film = getFilm(position);
        RemoteViews widgetView = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);

        Bitmap filmImage;
        try {
            filmImage = Glide.with(mContext)
                    .asBitmap()
                    .load(BuildConfig.IMG_DBMOVIE+film.getPoster_path())
                    .submit()
                    .get();
            Log.d("Path",film.getPoster_path());

            widgetView.setImageViewBitmap(R.id.film_img, filmImage);
            widgetView.setTextViewText(R.id.film_title, film.getTitle());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Bundle extras = new Bundle();
        extras.putInt(FavouriteListImage.INTENT_ITEM, position);

        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        widgetView.setOnClickFillInIntent(R.id.film_img, fillInIntent);

        return widgetView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    private void initDatabase() {
        cursor = mContext.getContentResolver().query(
                DBContract.CONTENT_URI,
                null,null,null,_ID + " DESC");
    }

    private Film getFilm(int position) {
        if (!cursor.moveToPosition(position)) throw new IllegalStateException("oops!");
        return new Film(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(TITLE)),
                cursor.getString(cursor.getColumnIndexOrThrow(POSTER_PATH)));
    }
}
