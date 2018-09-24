package com.mvryan.listfavourite;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.mvryan.listfavourite.utils.ListFavouriteAdapter;

import static com.mvryan.listfavourite.db.DBContract.CONTENT_URI;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private ListView listView;
    private ListFavouriteAdapter listFavouriteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.lst_favorites);
        listFavouriteAdapter = new ListFavouriteAdapter(this, null, true);
        listView.setAdapter(listFavouriteAdapter);
        getSupportLoaderManager().initLoader(100, null, this);
    }

    @Override protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(100, null, this);
    }

    @NonNull @Override public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(this, CONTENT_URI, null, null, null, null);
    }

    @Override public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        listFavouriteAdapter.swapCursor(data);
    }

    @Override public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        listFavouriteAdapter.swapCursor(null);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        getSupportLoaderManager().destroyLoader(100);
    }

}
