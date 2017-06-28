package com.tarrotsystem.codepreneur.bakingrite.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tarrotsystem.codepreneur.bakingrite.MainActivity;
import com.tarrotsystem.codepreneur.bakingrite.R;
import com.tarrotsystem.codepreneur.bakingrite.RecipeAdapter;
import com.tarrotsystem.codepreneur.bakingrite.model.Recipe;
import com.tarrotsystem.codepreneur.bakingrite.utils.RecipesEndpoint;
import com.tarrotsystem.codepreneur.bakingrite.utils.RetrofitBuilder;

import java.net.HttpURLConnection;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by codepreneur on 6/26/17.
 */

public class RecipeFragment extends Fragment {
    @BindView(R.id.recipe_recycler)
    RecyclerView recyclerView;

    @BindView(R.id.empty_view)
    TextView empty_view;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private static final int ANIM_DURATION_RECYCLERVIEW = 500;
    private RecipeAdapter recipeAdapter;

    public RecipeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipe_recyclerview,container,false);

        ButterKnife.bind(this,rootView);

        recipeAdapter = new RecipeAdapter((MainActivity)getActivity());
        recyclerView.setAdapter(recipeAdapter);
        startIntroAnimation();


        if (rootView.getTag()!=null && rootView.getTag().equals("phone-land")){
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),3);
            recyclerView.setLayoutManager(gridLayoutManager);
        }else{
            LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(linearLayoutManager);
        }

        if (savedInstanceState == null){

        }

        RecipesEndpoint recipesEndpoint = RetrofitBuilder.retrieve();
        Call<ArrayList<Recipe>> recipe = recipesEndpoint.getRecipe();

        recipe.enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                if (response.code()== HttpURLConnection.HTTP_OK){
                    hideEmptyView();
                    ArrayList<Recipe> recipes = response.body();
                    recipeAdapter.setRecipeData(recipes,getContext());
                    startIntroAnimation();
                }else {
                    showEmptyView();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                showEmptyView();
                Log.e("request failed : ",t.getLocalizedMessage());
            }
        });
        return rootView;
    }

    private void startIntroAnimation() {
        recyclerView.setAlpha(0f);
        recyclerView.animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_RECYCLERVIEW)
                .alpha(1f)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .start();
    }

    private void showEmptyView(){
        empty_view.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    private void hideEmptyView(){
        empty_view.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);

    }
}
