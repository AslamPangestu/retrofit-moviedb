package com.mvryan.katalogfilm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.mvryan.katalogfilm.db.FilmHelper;
import com.mvryan.katalogfilm.model.Film;

/**
 * Created by mvryan on 12/08/18.
 */

public class FilmDetailActivity extends AppCompatActivity {

    String title, poster, vote, popularity, release_date, overview,film_id;
    TextView title_txt, vote_txt, popularity_txt, release_date_txt, overview_txt;
    ImageView poster_img;
    Button btnFavourite;

    private FilmHelper filmHelper;
    private Film film;

    private boolean isFavorite = false;

    public static String EXTRA = "movie";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.film_detail);

        film = new Gson().fromJson(getIntent().getStringExtra(EXTRA), Film.class);

        title = film.getTitle();
        poster = film.getPoster_path();
        vote = film.getVote_average();
        popularity = film.getPopularity();
        release_date = film.getRelease_date();
        overview = film.getOverview();
        film_id = String.valueOf(film.getId());

        title_txt = findViewById(R.id.title_detail);
        vote_txt = findViewById(R.id.vote_detail);
        popularity_txt = findViewById(R.id.popularity_detail);
        release_date_txt = findViewById(R.id.release_date_detail);
        overview_txt = findViewById(R.id.overview_detail);
        poster_img = findViewById(R.id.poster_detail);
        btnFavourite = findViewById(R.id.favourite);

        title_txt.setText(title);
        vote_txt.setText(getString(R.string.vote_average)+" : "+vote);
        popularity_txt.setText(getString(R.string.popularity)+" : "+popularity);
        release_date_txt.setText(getString(R.string.release_date)+" : "+release_date);
        overview_txt.setText(getString(R.string.overview)+" : \n"+overview);

        Glide.with(getApplicationContext())
                .load(BuildConfig.IMG_DBMOVIE+poster)
                .into(poster_img);

        filmHelper = new FilmHelper(this);
        filmHelper.open();

        btnFavourite.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.textColor));
        final int isFilmExist = filmHelper.queryByIdProvider(String.valueOf(film.getId())).getCount();

        isFavorite = isFilmExist != 0;

        if (isFilmExist == 0) {
            btnFavourite.setText(getString(R.string.btn_favourite));
        } else {
            btnFavourite.setText(getString(R.string.btn_not_favourite));
        }

        btnFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFavorite) {
                    filmHelper.insert(film);
                    btnFavourite.setText(getString(R.string.btn_not_favourite));
                    isFavorite = true;
                } else {
                    filmHelper.delete(film.getId());
                    btnFavourite.setText(getString(R.string.btn_favourite));
                    isFavorite = false;
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (filmHelper != null){
            filmHelper.close();
        }
    }
}
