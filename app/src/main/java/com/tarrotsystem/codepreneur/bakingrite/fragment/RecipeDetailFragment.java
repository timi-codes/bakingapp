package com.tarrotsystem.codepreneur.bakingrite.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tarrotsystem.codepreneur.bakingrite.DetailActivity;
import com.tarrotsystem.codepreneur.bakingrite.R;
import com.tarrotsystem.codepreneur.bakingrite.adapter.RecipeStepAdapter;
import com.tarrotsystem.codepreneur.bakingrite.model.Ingredient;
import com.tarrotsystem.codepreneur.bakingrite.model.Recipe;
import com.tarrotsystem.codepreneur.bakingrite.utils.RecipeUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Optional;

import static com.tarrotsystem.codepreneur.bakingrite.DetailActivity.SELECTED_RECIPES;

/**
 * Created by codepreneur on 6/28/17.
 */

public class RecipeDetailFragment extends Fragment {
    ArrayList<Recipe> recipes;
    RecipeStepAdapter recipeStepAdapter;
    @BindView(R.id.steps_recycler)
    RecyclerView stepsRecycler;

    @Nullable
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Nullable
    @BindView(R.id.backdrop)
    ImageView recipeImage;

    @Nullable
    @BindView(R.id.collapsingtbl)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @Nullable
    @BindView(R.id.btnIngredients)
    FloatingActionButton floatingActionButton;

    private String recipeName;
    private LinearLayoutManager linearLayoutManager;
    private Parcelable mListState;
    private static String LIST_STATE_KEY = "recycleposition";



    public RecipeDetailFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState!=null){
            recipes = savedInstanceState.getParcelableArrayList(SELECTED_RECIPES);
            mListState = savedInstanceState.getParcelable(LIST_STATE_KEY);

        }else{
            recipes = getArguments().getParcelableArrayList(SELECTED_RECIPES);
        }
        recipeName = recipes.get(0).getName();

        View rootView = inflater.inflate(R.layout.recipe_detail_fragment, container, false);
        ButterKnife.bind(this,rootView);

        if (toolbar!=null){
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(recipeName);
            toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
            collapsingToolbarLayout.setTitle(recipeName);
            collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.white));

            if (recipes.get(0).getImage().isEmpty()){
                Picasso.with(getContext()).load(RecipeUtils.getImageById(recipes.get(0).getId())).into(recipeImage);
            }else{
                Picasso.with(getContext()).load(recipes.get(0).getImage()).into(recipeImage);
            }

            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setupDialog();
                }
            });
        }


        linearLayoutManager = new LinearLayoutManager(getContext());
        stepsRecycler.setLayoutManager(linearLayoutManager);
        recipeStepAdapter = new RecipeStepAdapter((DetailActivity)getActivity());
        stepsRecycler.setAdapter(recipeStepAdapter);
        recipeStepAdapter.setStepsRecipeData(recipes.get(0).getStepList(),getContext());
        return rootView;
    }

    private void setupDialog(){
        LayoutInflater inflater = LayoutInflater.from(getContext());;
        View dialogueLayout = inflater.inflate(R.layout.custom_dialog, null);
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setView(dialogueLayout);
        builder.setTitle(Html.fromHtml("<font color='#399f8c'>You Need</font>"));
        builder.setIcon(R.drawable.ic_ingredients);
        builder.setPositiveButton("OK",null).create()
                .show();

        final TextView ingredient = (TextView)dialogueLayout.findViewById(R.id.ingredient);

        ArrayList<Ingredient> ingredients = recipes.get(0).getIngredientList();

        for(Ingredient a: ingredients){
            ingredient.append("\u2022  "+ a.getQuantity().toString());
            ingredient.append(a.getMeasure());
            ingredient.append(" of "+a.getIngredient()+"\n\n");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle currentState) {
        super.onSaveInstanceState(currentState);
        currentState.putParcelableArrayList(SELECTED_RECIPES, recipes);
        // Save list state
        mListState = linearLayoutManager.onSaveInstanceState();
        currentState.putParcelable(LIST_STATE_KEY, mListState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Retrieve list state and list/item positions
        if(savedInstanceState != null)
            mListState = savedInstanceState.getParcelable(LIST_STATE_KEY);
    }


    @Override
    public void onResume() {
        super.onResume();

        if (mListState != null) {
            linearLayoutManager.onRestoreInstanceState(mListState);
        }
    }
}
