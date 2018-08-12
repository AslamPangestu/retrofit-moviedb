package com.mvryan.katalogfilm;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mvryan.katalogfilm.adapter.FilmAdapter;
import com.mvryan.katalogfilm.model.Film;
import com.mvryan.katalogfilm.model.Result;
import com.mvryan.katalogfilm.network.Networks;
import com.mvryan.katalogfilm.network.Routes;
import com.mvryan.katalogfilm.utils.FilmListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements FilmListener{

    private RecyclerView recyclerView;
    private FilmAdapter filmAdapter;

    private static String API_KEY = "a3bcf179bcd3a4cfef5b41daf788b14d";
    private static String LANG = "en-US";
    private Routes routes;

    private EditText titleEdt;
    private Button findBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleEdt = findViewById(R.id.title_edt);
        findBtn = findViewById(R.id.find_btn);

        routes = Networks.filmRequest().create(Routes.class);

        findBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleEdt.getText().toString();
                getFilm(title);
            }
        });

    }

    private void getFilm(String title) {
        Call<Result> resultCall = routes.getResult(BuildConfig.API_DBMOVIE, BuildConfig.LANG_DBMOVIE, title);
        resultCall.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(@NonNull Call<Result> call, @NonNull Response<Result> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Film ditemukan", Toast.LENGTH_LONG).show();

                    List<Film> films = response.body().getResults();
                    generateFilm(films);
                } else {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Film tidak ada", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void generateFilm(List<Film> films) {
        recyclerView = findViewById(R.id.film_list);
        filmAdapter = new FilmAdapter(films,this);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(filmAdapter);

    }

    @Override
    public void onClick(Film film) {
        Intent intent = new Intent(getApplicationContext(), FilmDetailActivity.class);
        intent.putExtra("Title",film.getOriginal_title());
        intent.putExtra("Poster",film.getPoster_path());
        intent.putExtra("Vote",film.getVote_average());
        intent.putExtra("Popularity",film.getPopularity());
        intent.putExtra("Release_Date",film.getRelease_date());
        intent.putExtra("Overview",film.getOverview());
        startActivity(intent);
    }
}
