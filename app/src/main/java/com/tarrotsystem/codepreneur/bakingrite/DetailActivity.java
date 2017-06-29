package com.tarrotsystem.codepreneur.bakingrite;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.tarrotsystem.codepreneur.bakingrite.adapter.RecipeStepAdapter;
import com.tarrotsystem.codepreneur.bakingrite.fragment.RecipeDetailFragment;
import com.tarrotsystem.codepreneur.bakingrite.model.Ingredient;
import com.tarrotsystem.codepreneur.bakingrite.model.Recipe;
import com.tarrotsystem.codepreneur.bakingrite.model.Step;
import com.tarrotsystem.codepreneur.bakingrite.utils.RecipeUtils;

import org.w3c.dom.Text;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by codepreneur on 6/27/17.
 */

public class DetailActivity extends AppCompatActivity implements RecipeStepAdapter.RecipeClickListener{
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.backdrop)
    ImageView recipeImage;

    @BindView(R.id.collapsingtbl)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.btnIngredients)
    FloatingActionButton floatingActionButton;


    public static String SELECTED_RECIPES = "SelectedRecipes";
    private ArrayList<Recipe> recipe = new ArrayList<>();
    private String recipeName;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        if (savedInstanceState == null){
            Bundle selectedRecipe = getIntent().getExtras();
            recipe = selectedRecipe.getParcelableArrayList(SELECTED_RECIPES);
            recipeName = recipe.get(0).getName();

            final RecipeDetailFragment fragment = new RecipeDetailFragment();
            fragment.setArguments(selectedRecipe);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();

        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(recipeName);
        collapsingToolbarLayout.setTitle(recipeName);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.white));

        recipeImage.setImageDrawable(getResources().getDrawable(RecipeUtils.getImageById(recipe.get(0).getId())));
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupDialog();
            }
        });
    }

    private void setupDialog(){
       /* dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_LEFT_ICON);
        dialog.setFeatureDrawableResource(Window.FEATURE_LEFT_ICON, R.drawable.ic_ingredients);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setTitle("You Need");
        dialog.*/
        LayoutInflater inflater = LayoutInflater.from(this);;
         View dialogueLayout = inflater.inflate(R.layout.custom_dialog, null);
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setView(dialogueLayout);
        builder.setTitle("You Need");
        builder.setIcon(R.drawable.ic_ingredients);
        builder.setPositiveButton("OK",null).create()
                .show();

        final TextView ingredient = (TextView)dialogueLayout.findViewById(R.id.ingredient);

        ArrayList<Ingredient> ingredients = recipe.get(0).getIngredientList();

        for(Ingredient a: ingredients){
            ingredient.append("\u2022  "+ a.getQuantity().toString());
            ingredient.append(a.getMeasure());
            ingredient.append(" of "+a.getIngredient()+"\n\n");
        }

    }

    @Override
    public void onClick(Step clickedItemIndex) {

    }
}
