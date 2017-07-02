package com.tarrotsystem.codepreneur.bakingrite;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


import com.tarrotsystem.codepreneur.bakingrite.adapter.RecipeStepAdapter;
import com.tarrotsystem.codepreneur.bakingrite.fragment.RecipeDetailFragment;
import com.tarrotsystem.codepreneur.bakingrite.fragment.RecipeStepDetailFragment;
import com.tarrotsystem.codepreneur.bakingrite.model.Recipe;
import com.tarrotsystem.codepreneur.bakingrite.model.Step;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by codepreneur on 6/27/17.
 */

public class DetailActivity extends AppCompatActivity implements RecipeStepAdapter.RecipeClickListener, RecipeStepDetailFragment.RecipeClickListener {

    public static String SELECTED_RECIPES = "SelectedRecipes";
    public static String SELECTED_STEPS = "SelectedSteps";
    public static String SELECTED_INDEX = "SelectedIndex";


    private ArrayList<Recipe> recipe = new ArrayList<>();
    public String recipeName;

    @Nullable
    @BindView(R.id.my_toolbar)
    Toolbar toolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        if (savedInstanceState == null) {
            if (getIntent().hasExtra(SELECTED_RECIPES) && getIntent() != null) {
                Bundle selectedRecipe = getIntent().getExtras();
                recipe = selectedRecipe.getParcelableArrayList(SELECTED_RECIPES);

                final RecipeDetailFragment firstfragment = new RecipeDetailFragment();
                firstfragment.setArguments(selectedRecipe);
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, firstfragment)
                        .commit();

                if (findViewById(R.id.recipe_linear_layout).getTag() != null && findViewById(R.id.recipe_linear_layout).getTag().equals("tablet-land")) {

                    final RecipeStepDetailFragment secondfragment = new RecipeStepDetailFragment();
                    secondfragment.setArguments(selectedRecipe);
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container2, secondfragment)
                            .commit();
                }
            }

        } else {
            recipe = savedInstanceState.getParcelableArrayList(SELECTED_RECIPES);
            FragmentManager fragmentManager = getSupportFragmentManager();

            final RecipeDetailFragment firstfragment = new RecipeDetailFragment();
            firstfragment.setArguments(savedInstanceState);
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, firstfragment)
                    .commit();

            if (findViewById(R.id.recipe_linear_layout).getTag() != null && findViewById(R.id.recipe_linear_layout).getTag().equals("tablet-land")) {
                final RecipeStepDetailFragment secondfragment = new RecipeStepDetailFragment();
                secondfragment.setArguments(savedInstanceState);
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container2, secondfragment)
                        .commit();
            }

        }
        recipeName = recipe.get(0).getName();

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("How To Make " + recipeName + " Recipe");
        }

    }


    @Override
    public void onClick(ArrayList<Step> steps, int selectedItemIndex) {
        RecipeStepDetailFragment fragment = new RecipeStepDetailFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();

        Bundle stepBundle = new Bundle();
        stepBundle.putParcelableArrayList(SELECTED_STEPS, steps);
        stepBundle.putParcelableArrayList(SELECTED_RECIPES, recipe);
        stepBundle.putInt(SELECTED_INDEX, selectedItemIndex);
        stepBundle.putString("Title", recipeName);
        fragment.setArguments(stepBundle);

        if (findViewById(R.id.recipe_linear_layout).getTag() != null && findViewById(R.id.recipe_linear_layout).getTag().equals("tablet-land")) {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container2, fragment)
                    .commit();
        } else {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment).addToBackStack(null)
                    .commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(SELECTED_RECIPES, recipe);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        }
    }
}
