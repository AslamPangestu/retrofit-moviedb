package com.mvryan.katalogfilm.network;

import com.mvryan.katalogfilm.model.Result;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by mvryan on 19/07/18.
 */

public interface Routes {

    @GET("search/movie")
    Call<Result> getSearch (@Query("api_key") String apiKey,
                            @Query("language") String lang,
                            @Query("query") String filmName);

    @GET("movie/now_playing")
    Call<Result> getNowPlaying (@Query("api_key") String apiKey,
                            @Query("language") String lang);

    @GET("movie/upcoming")
    Call<Result> getUpcoming (@Query("api_key") String apiKey,
                            @Query("language") String lang);
}
