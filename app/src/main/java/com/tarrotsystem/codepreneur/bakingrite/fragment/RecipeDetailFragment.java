package com.tarrotsystem.codepreneur.bakingrite.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tarrotsystem.codepreneur.bakingrite.DetailActivity;
import com.tarrotsystem.codepreneur.bakingrite.R;
import com.tarrotsystem.codepreneur.bakingrite.adapter.RecipeStepAdapter;
import com.tarrotsystem.codepreneur.bakingrite.model.Recipe;
import com.tarrotsystem.codepreneur.bakingrite.model.Step;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.tarrotsystem.codepreneur.bakingrite.DetailActivity.SELECTED_RECIPES;

/**
 * Created by codepreneur on 6/28/17.
 */

public class RecipeDetailFragment extends Fragment {
    ArrayList<Recipe> recipes;
    RecipeStepAdapter recipeStepAdapter;
    @BindView(R.id.steps_recycler)
    RecyclerView stepsRecycler;

    public RecipeDetailFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState!=null){
            recipes = savedInstanceState.getParcelableArrayList(SELECTED_RECIPES);
        }else{
            recipes = getArguments().getParcelableArrayList(SELECTED_RECIPES);
        }

        View rootView = inflater.inflate(R.layout.recipe_steps_fragment, container, false);
        ButterKnife.bind(this,rootView);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        stepsRecycler.setLayoutManager(linearLayoutManager);
        recipeStepAdapter = new RecipeStepAdapter((DetailActivity)getActivity());
        stepsRecycler.setAdapter(recipeStepAdapter);
        recipeStepAdapter.setStepsRecipeData(recipes.get(0).getStepList(),getContext());
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle currentState) {
        super.onSaveInstanceState(currentState);
        currentState.putParcelableArrayList(SELECTED_RECIPES, recipes);
    }
}
