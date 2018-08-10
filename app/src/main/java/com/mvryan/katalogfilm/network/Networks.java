package com.mvryan.katalogfilm.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mvryan on 19/07/18.
 */

public class Networks {

    private static Retrofit retrofit;
    private static final String BASE_URL = "https://api.themoviedb.org/";

    public static OkHttpClient client;

    private static void interceptor(){

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client = new  OkHttpClient.Builder().addInterceptor(interceptor).build();
    }

    public static Retrofit filmRequest(){
        interceptor();
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }

}
