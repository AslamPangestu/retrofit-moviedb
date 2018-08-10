package com.mvryan.katalogfilm.network;

import com.mvryan.katalogfilm.model.Result;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by mvryan on 19/07/18.
 */

public interface Routes {

    @GET("3/search/movie")
    Call<Result> getResult (@Query("api_key") String apiKey,
                            @Query("language") String lang,
                            @Query("query") String filmName);
}
