package com.mvryan.katalogfilm.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mvryan.katalogfilm.FilmDetailActivity;
import com.mvryan.katalogfilm.R;
import com.mvryan.katalogfilm.db.FilmHelper;
import com.mvryan.katalogfilm.model.Film;
import com.mvryan.katalogfilm.utils.adapter.FavouriteAdapter;
import com.mvryan.katalogfilm.utils.listener.FilmListener;

import static com.mvryan.katalogfilm.db.DBContract.CONTENT_URI;
import static com.mvryan.katalogfilm.db.DBContract.FavouriteColumn._ID;

/**
 * Created by mvryan on 08/09/18.
 */

public class FavouriteFragment extends Fragment implements FilmListener {

    private RecyclerView recyclerView;
    private FavouriteAdapter favouriteAdapter;
    private Cursor filmList;

    public FavouriteFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favourite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.favourite_list);

        favouriteAdapter = new FavouriteAdapter(this, getContext());
        favouriteAdapter.setFilmList(filmList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(favouriteAdapter);

        new LoadFilmAsync().execute();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(Film film) {
        Intent intent = new Intent(getActivity(), FilmDetailActivity.class);
        intent.putExtra("Id", film.getId());
        intent.putExtra("Title", film.getTitle());
        intent.putExtra("Poster", film.getPoster_path());
        intent.putExtra("Vote", film.getVote_average());
        intent.putExtra("Popularity", film.getPopularity());
        intent.putExtra("Release_Date", film.getRelease_date());
        intent.putExtra("Overview", film.getOverview());
        getActivity().startActivityForResult(intent,1);
//        startActivity(intent);
    }

    private class LoadFilmAsync extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
 
        @Override
        protected Cursor doInBackground(Void... voids) {
            return getContext().getContentResolver().query(CONTENT_URI,null,null,null,_ID + " DESC");
        }
 
        @Override
        protected void onPostExecute(Cursor films) {
            super.onPostExecute(films);

            filmList = films;
            favouriteAdapter.setFilmList(filmList);
            favouriteAdapter.notifyDataSetChanged();

            if (filmList.getCount() == 0){
                showSnackbarMessage("Tidak ada data saat ini");
            }
        }
    }

    private void showSnackbarMessage(String message){
        Snackbar.make(recyclerView, message, Snackbar.LENGTH_SHORT).show();
    }
}
