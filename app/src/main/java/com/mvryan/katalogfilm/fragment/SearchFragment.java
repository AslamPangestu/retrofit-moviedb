package com.mvryan.katalogfilm.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mvryan.katalogfilm.BuildConfig;
import com.mvryan.katalogfilm.FilmDetailActivity;
import com.mvryan.katalogfilm.R;
import com.mvryan.katalogfilm.utils.adapter.FilmAdapter;
import com.mvryan.katalogfilm.model.Film;
import com.mvryan.katalogfilm.model.Result;
import com.mvryan.katalogfilm.network.Networks;
import com.mvryan.katalogfilm.network.Routes;
import com.mvryan.katalogfilm.utils.listener.FilmListener;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mvryan on 24/08/18.
 */

public class SearchFragment extends Fragment implements FilmListener {

    private RecyclerView recyclerView;
    private FilmAdapter filmAdapter;

    private Routes routes;

    private EditText titleEdt;
    private Button findBtn;

    private String lang;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        titleEdt = view.findViewById(R.id.title_edt);
        titleEdt.setHint(R.string.hint_title);
        findBtn = view.findViewById(R.id.find_btn);
        findBtn.setText(R.string.search_btn);
        recyclerView = view.findViewById(R.id.film_list);

        routes = Networks.filmRequest().create(Routes.class);

        if (Locale.getDefault().getLanguage().equals("en")){
            lang = BuildConfig.LANG_DBMOVIE_US;
        }else if (Locale.getDefault().getLanguage().equals("in")){
            lang = BuildConfig.LANG_DBMOVIE_ID;
        }

        findBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleEdt.getText().toString();
                if (TextUtils.isEmpty(title))
                {
                    Toast.makeText(getActivity(), getString(R.string.txt_empty), Toast.LENGTH_SHORT).show();
                }
                getFilm(title);
            }
        });

    }

    private void getFilm(String title) {
        Call<Result> resultCall = routes.getSearch(BuildConfig.API_DBMOVIE, lang, title);
        resultCall.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(@NonNull Call<Result> call, @NonNull Response<Result> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getActivity(), getString(R.string.found), Toast.LENGTH_LONG).show();

                    List<Film> films = response.body().getResults();
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
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(filmAdapter);
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


}
