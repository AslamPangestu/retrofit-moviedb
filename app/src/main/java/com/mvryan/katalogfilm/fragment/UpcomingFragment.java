package com.mvryan.katalogfilm.fragment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mvryan.katalogfilm.BuildConfig;
import com.mvryan.katalogfilm.FilmDetailActivity;
import com.mvryan.katalogfilm.R;
import com.mvryan.katalogfilm.model.Film;
import com.mvryan.katalogfilm.model.Result;
import com.mvryan.katalogfilm.network.Networks;
import com.mvryan.katalogfilm.network.Routes;
import com.mvryan.katalogfilm.utils.adapter.FilmAdapter;
import com.mvryan.katalogfilm.utils.listener.FilmListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mvryan on 17/08/18.
 */

public class UpcomingFragment extends Fragment implements FilmListener{

    private RecyclerView recyclerView;
    private FilmAdapter filmAdapter;
    private List<Film> films = new ArrayList<>();

    private Routes routes;

    private String lang;


    public UpcomingFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_upcoming, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.upcoming_list);

        if (Locale.getDefault().getLanguage().equals("en")){
            lang = BuildConfig.LANG_DBMOVIE_US;
        }else if (Locale.getDefault().getLanguage().equals("in")){
            lang = BuildConfig.LANG_DBMOVIE_ID;
        }

        if(savedInstanceState != null){
            films = savedInstanceState.getParcelableArrayList("films");
            generateFilm(films);
        }else {
            routes = Networks.filmRequest().create(Routes.class);
            getUpcoming();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("films", new ArrayList<>(filmAdapter.getFilmList()));
    }


    private void getUpcoming(){
        Call<Result> resultCall = routes.getUpcoming(BuildConfig.API_DBMOVIE, lang);
        resultCall.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(@NonNull Call<Result> call, @NonNull Response<Result> response) {
                if (response.isSuccessful()) {
                    films = response.body().getResults();
                    generateFilm(films);
                } else {
                    Toast.makeText(getActivity(), getString(R.string.error), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Toast.makeText(getActivity(), getString(R.string.not_found), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void generateFilm(List<Film> films) {
        filmAdapter = new FilmAdapter(films, this, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),new LinearLayoutManager(getActivity()).getOrientation()));
        recyclerView.setAdapter(filmAdapter);

    }

    @Override
    public void onClick(Film film) {
        Intent intent = new Intent(getActivity(), FilmDetailActivity.class);
        intent.putExtra("movie", new Gson().toJson(film));
        startActivity(intent);
    }
}
