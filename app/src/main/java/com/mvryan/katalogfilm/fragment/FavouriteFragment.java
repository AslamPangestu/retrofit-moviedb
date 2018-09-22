package com.mvryan.katalogfilm.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mvryan.katalogfilm.FilmDetailActivity;
import com.mvryan.katalogfilm.R;
import com.mvryan.katalogfilm.db.FilmHelper;
import com.mvryan.katalogfilm.model.Film;
import com.mvryan.katalogfilm.utils.adapter.FavouriteAdapter;
import com.mvryan.katalogfilm.utils.listener.FilmListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by mvryan on 08/09/18.
 */

public class FavouriteFragment extends Fragment implements FilmListener {

    private RecyclerView recyclerView;
    private FavouriteAdapter favouriteAdapter;
    private FilmHelper filmHelper;
    private LinkedList<Film> filmList;

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

        filmHelper = new FilmHelper(getContext());
        filmHelper.open();
        filmList = new LinkedList<>();

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
        if (filmHelper != null){
            filmHelper.close();
        }
    }

    @Override
    public void onClick(Film film) {
        Intent intent = new Intent(getActivity(), FilmDetailActivity.class);
        intent.putExtra("Title", film.getOriginal_title());
        intent.putExtra("Poster", film.getPoster_path());
        intent.putExtra("Vote", film.getVote_average());
        intent.putExtra("Popularity", film.getPopularity());
        intent.putExtra("Release_Date", film.getRelease_date());
        intent.putExtra("Overview", film.getOverview());
        startActivity(intent);
    }

    private class LoadFilmAsync extends AsyncTask<Void, Void, ArrayList<Film>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
 
        @Override
        protected ArrayList<Film> doInBackground(Void... voids) {
            return filmHelper.query();
        }
 
        @Override
        protected void onPostExecute(ArrayList<Film> films) {
            super.onPostExecute(films);
 
            filmList.addAll(films);
            favouriteAdapter.setFilmList(filmList);
            favouriteAdapter.notifyDataSetChanged();
        }
    }
}
