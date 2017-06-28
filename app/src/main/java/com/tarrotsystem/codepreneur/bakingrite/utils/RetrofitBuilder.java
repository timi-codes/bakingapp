package com.tarrotsystem.codepreneur.bakingrite.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by codepreneur on 6/27/17.
 */

public class RetrofitBuilder {
    public static RecipesEndpoint recipes;
    private static final String RECIPES_URL= "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";


    public static RecipesEndpoint retrieve() {

        Gson gson = new GsonBuilder().create();

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();


        recipes = new Retrofit.Builder()
                .baseUrl(RECIPES_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .callFactory(httpClientBuilder.build())
                .build().create(RecipesEndpoint.class);
        return recipes;
    }
}
