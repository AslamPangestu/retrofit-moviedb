package com.mvryan.listfavourite.utils;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mvryan.listfavourite.BuildConfig;
import com.mvryan.listfavourite.R;

import static com.mvryan.listfavourite.db.DBContract.FavouriteColumn.POSTER_PATH;
import static com.mvryan.listfavourite.db.DBContract.FavouriteColumn.RELEASE_DATE;
import static com.mvryan.listfavourite.db.DBContract.FavouriteColumn.TITLE;
import static com.mvryan.listfavourite.db.DBContract.FavouriteColumn.VOTE;
import static com.mvryan.listfavourite.db.DBContract.getColumnString;


/**
 * Created by mvryan on 23/09/18.
 */

public class ListFavouriteAdapter extends CursorAdapter {

    public ListFavouriteAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.film_item, parent, false);
        return view;
    }

    @Override
    public Cursor getCursor() {
        return super.getCursor();
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        if (cursor != null){

            TextView title = view.findViewById(R.id.title_view);
            TextView releaseDate = view.findViewById(R.id.release_date_view);
            TextView vote = view.findViewById(R.id.vote_view);
            ImageView poster = view.findViewById(R.id.poster_view);

            title.setText(getColumnString(cursor,TITLE));
            releaseDate.setText(getColumnString(cursor,RELEASE_DATE));
            vote.setText(getColumnString(cursor,VOTE));
            Glide.with(context)
                    .load(BuildConfig.IMG_DBMOVIE+getColumnString(cursor,POSTER_PATH))
                    .into(poster);
        }
    }
}
