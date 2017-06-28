package com.tarrotsystem.codepreneur.bakingrite.utils;

import com.tarrotsystem.codepreneur.bakingrite.model.Recipe;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by codepreneur on 6/27/17.
 */

public interface RecipesEndpoint {
    @GET("baking.json")
    Call<ArrayList<Recipe>> getRecipe();
}
