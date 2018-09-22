package com.mvryan.katalogfilm;

import android.os.Bundle;
import android.os.Debug;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mvryan.katalogfilm.db.FilmHelper;
import com.mvryan.katalogfilm.model.Film;

/**
 * Created by mvryan on 12/08/18.
 */

public class FilmDetailActivity extends AppCompatActivity {

    String title, poster, vote, popularity, release_date, overview,film_id;
    TextView title_txt, vote_txt, popularity_txt, release_date_txt, overview_txt;
    ImageView poster_img;
    Button favourite;

    public static int RESULT_ADD = 101;
    public static int RESULT_DELETE = 301;

    FilmHelper filmHelper;
    Film film;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.film_detail);

        title = getIntent().getStringExtra("Title");
        poster = getIntent().getStringExtra("Poster");
        vote = getIntent().getStringExtra("Vote");
        popularity = getIntent().getStringExtra("Popularity");
        release_date = getIntent().getStringExtra("Release_Date");
        overview = getIntent().getStringExtra("Overview");

        title_txt = findViewById(R.id.title_detail);
        vote_txt = findViewById(R.id.vote_detail);
        popularity_txt = findViewById(R.id.popularity_detail);
        release_date_txt = findViewById(R.id.release_date_detail);
        overview_txt = findViewById(R.id.overview_detail);
        poster_img = findViewById(R.id.poster_detail);
        favourite = findViewById(R.id.favourite);

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

        favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Film newFilm = new Film();
                newFilm.setTitle(title);
                newFilm.setVote_average(vote);
                newFilm.setPopularity(popularity);
                newFilm.setRelease_date(release_date);
                newFilm.setOverview(overview);
                newFilm.setPoster_path(poster);
                filmHelper.insert(newFilm);

                setResult(RESULT_ADD);
                finish();
                Log.d("status","success");
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
