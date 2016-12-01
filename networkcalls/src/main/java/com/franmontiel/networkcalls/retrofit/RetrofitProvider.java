package com.franmontiel.networkcalls.retrofit;

import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Francisco J. Montiel on 1/12/16.
 */

public class RetrofitProvider {

    private static Retrofit RETROFIT;

    private RetrofitProvider() {
        // No instances.
    }

    public static void initialize(OkHttpClient client, String url) {
        RETROFIT = new Retrofit.Builder()
                .baseUrl(url)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
    }

    public static Retrofit getRetrofit() {
        return RETROFIT;
    }

}
