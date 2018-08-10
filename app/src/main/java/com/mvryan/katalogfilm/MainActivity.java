package com.mvryan.katalogfilm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mvryan.katalogfilm.adapter.FilmAdapter;
import com.mvryan.katalogfilm.model.Film;
import com.mvryan.katalogfilm.model.Result;
import com.mvryan.katalogfilm.network.Networks;
import com.mvryan.katalogfilm.network.Routes;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private List<Film> films;
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

        films = new ArrayList<>();

        recyclerView = findViewById(R.id.film_list);
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

    private void getFilm(String filmTitle){
        Call<Result> resultCall = routes.getResult(API_KEY,LANG,filmTitle);
        resultCall.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Log.d("onResponse", "" + response.message());
                Toast.makeText(getApplicationContext(),"Film ada",Toast.LENGTH_LONG).show();

//                List<Film> films = response.body().getFilmList();
//                filmAdapter = new FilmAdapter(films);
//
//                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//                recyclerView.setHasFixedSize(true);
//                RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.HORIZONTAL);
//                recyclerView.addItemDecoration(itemDecoration);
//                recyclerView.setAdapter(filmAdapter);
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Film tidak ada",Toast.LENGTH_LONG).show();
            }
        });
    }

}
